package com.mk.fl.bibgyani;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.google.gson.Gson;
import com.mk.fl.bibgyani.exceptions.MultipleGamesException;
import com.mk.fl.bibgyani.exceptions.NoGameException;
import com.mk.fl.bibgyani.model.Command;
import com.mk.fl.bibgyani.model.CommandStore;
import com.mk.fl.bibgyani.model.Game;
import com.mk.fl.bibgyani.model.Lifeline;
import com.mk.fl.bibgyani.model.Question;
import com.mk.fl.bibgyani.to.CommandTO;
import com.mk.fl.bibgyani.to.DefaultResponseTO;
import com.mk.fl.bibgyani.to.GameTO;
import com.mk.fl.bibgyani.util.BibgyaniUtil;

/**
 * Servlet implementation class CommandServlet
 */
public class CommandServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CommandServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Session session = BibgyaniUtil.openSession();
		try {
			Gson gson = new Gson();
			List<CommandTO> commands = new ArrayList<CommandTO>();
			Transaction tx = session.beginTransaction();
			String sql = "FROM Command C";
			String lastReceivedTime = request.getParameter("receivedTime");
			long lastReceivedAt = 0L;
			if (null != lastReceivedTime && !lastReceivedTime.isEmpty()) {
				lastReceivedAt = Long.parseLong(lastReceivedTime);
				if (Status.getLastCommandAt() <= lastReceivedAt) {
					response.setContentType("application/json");
					DefaultResponseTO resTO = new DefaultResponseTO();
					String outJson = gson.toJson(resTO);
					response.getWriter().write(outJson);
					return;
				}
				sql += " WHERE C.time > ?";
			}
			Query query = session.createQuery(sql);
			if (null != lastReceivedTime && !lastReceivedTime.isEmpty()) {
				Date lastDate = new Date(lastReceivedAt);
				query.setTimestamp(0, lastDate);
			}
			List<?> list = query.list();
			tx.commit();
			for (Object o : list) {
				if (o instanceof Command) {
					Command command = (Command) o;
					GameTO gameTO = new GameTO(command.getGame());
					CommandTO to = new CommandTO(command.getName() // name of command
							, command.getValue() // value of the command, if any
							, command.getTime().getTime() // command sent time in long
							, gameTO// question id against which this command was sent
							);
					commands.add(to);
				}
			}
			if (commands.size() > 0) {
				Collections.sort(commands);
				Status.setLastCommandAt(commands.get(commands.size() - 1).getTime());
				// } else {
				// Status.setLastCommandAt(new Date().getTime());
			}
			String json = gson.toJson(commands);
			response.setContentType("application/json");
			response.getWriter().write(json);
		} finally {
			session.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Session session = BibgyaniUtil.openSession();
		Transaction tx = null;
		try {
			response.setContentType("application/json");
			Gson gson = new Gson();
			if (request.isUserInRole("operator")) {
				String jsonString = IOUtils.toString(request.getInputStream());
				CommandTO to = gson.fromJson(jsonString, CommandTO.class);
				tx = session.beginTransaction();
				String command_name = to.getName();
				Date time = new Date();
				if (CommandStore.FINISH_GAME.getName().equalsIgnoreCase(command_name)) {
					finishGame(session, time);
				} else if (CommandStore.NEW_GAME.getName().equalsIgnoreCase(command_name)) {
					finishGame(session, time);
					newGame(session, time);
				} else if (CommandStore.NEXT_QUESTION.getName().equalsIgnoreCase(command_name)) {
					Game game = BibgyaniUtil.getRunningGame(session, true);
					if (null == game || 15 != game.getCurrentQuestionSequence()) {
						nextQuestion(session, time);
					} else {
						command_name = CommandStore.FINISH_GAME.getName();
					}
				} else if (CommandStore.USE_LIFELINE.getName().equalsIgnoreCase(command_name)
						|| CommandStore.USE_LIFELINE_DUAL.getName().equalsIgnoreCase(command_name)) {
					setLifelineUsed(session, to);
				} else if (CommandStore.SHOW_ANSWER.getName().equalsIgnoreCase(command_name)) {
					setCurrentQuestionAnswered(session);
				}

				Command command = new Command();
				command.setName(command_name);
				command.setTime(time);
				command.setValue(to.getValue());
				command.setGame(BibgyaniUtil.getRunningGame(session));
				session.save(command);
				tx.commit();

				DefaultResponseTO resTO = new DefaultResponseTO();
				String outJson = gson.toJson(resTO);
				response.getWriter().write(outJson);
				Status.setLastCommandAt(time.getTime());
			} else {
				BibgyaniUtil.sendErrorResponse(response, "Not authorized");
			}
		} catch (Exception e) {
			if (null != tx) {
				tx.rollback();
			}
			BibgyaniUtil.sendErrorResponse(response, e.getMessage());
		} finally {
			session.close();
		}
	}

	private void setCurrentQuestionAnswered(Session session) throws NoGameException, MultipleGamesException {
		Game game = BibgyaniUtil.getRunningGame(session);
		game.setCurrentQuestionAnswered(true);

		session.save(game);
		session.flush();
	}

	private void setLifelineUsed(Session session, CommandTO to) throws NoGameException, MultipleGamesException {
		Game game = BibgyaniUtil.getRunningGame(session);

		String lifeline = to.getValue();
		if (Lifeline.GRACE.getName().equalsIgnoreCase(lifeline)) {
			game.setLifelineGrace(true);
		} else if (Lifeline.DUAL.getName().equalsIgnoreCase(lifeline)
				|| CommandStore.USE_LIFELINE_DUAL.getName().equalsIgnoreCase(to.getName())) {
			game.setLifelineDual(true);
		} else if (Lifeline.CHALLENGE.getName().equalsIgnoreCase(lifeline)) {
			game.setLifelineChallenge(true);
			game.setChallengedAt(game.getCurrentQuestionSequence());
		}

		session.save(game);
		session.flush();
	}

	private void nextQuestion(Session session, Date time) throws NoGameException, MultipleGamesException {
		newGame(session, time);

		Game game = BibgyaniUtil.getRunningGame(session);

		int qId = -1;
		Question question = game.getCurrentQuestion();
		int nextLevel = Question.EASY;
		if (question != null) {
			if (game.getCurrentQuestionSequence() == 5 || game.getCurrentQuestionSequence() == 10) {
				nextLevel++;
			}
			qId = question.getId();
		}
		nextLevel = Math.min(nextLevel, Question.DIFFICULT);

		Query query;
		if (qId == -1) {
			query = session.createQuery(Question.QUERY_RANDOM_QUESTIONS_WITH_LEVEL);
		} else {
			query = session.createQuery(Question.QUERY_RANDOM_QUESTIONS_WITH_LEVEL_NOT_WITH_ID);
			query.setParameter("qId", Integer.valueOf(qId));
		}
		query.setParameter("level", Integer.valueOf(nextLevel));
		query.setMaxResults(1);
		List<?> list = query.list();

		if (list.isEmpty()) {
			throw new IllegalStateException(
					String.format("Question of level %s is not available", getQuestionLevelString(nextLevel)));
		}

		question = (Question) list.get(0);
		game.setCurrentQuestion(question);
		game.setCurrentQuestionSequence(game.getCurrentQuestionSequence() + 1);
		game.setCurrentQuestionAnswered(false);
		session.save(game);
		session.flush();

		if (qId != 0 && qId != -1) {
			query = session.createQuery(Question.QUERY_DELETE_QUESTION_WITH_ID);
			query.setParameter("qId", Integer.valueOf(qId));
			query.executeUpdate();
		}
	}

	private Object getQuestionLevelString(int level) {
		if (level == Question.EASY) {
			return "Easy";
		} else if (level == Question.MEDIUM) {
			return "Medium";
		} else if (level == Question.DIFFICULT) {
			return "Difficult";
		}
		return null;
	}

	private void newGame(Session session, Date time) {
		Query q = session.createQuery(Game.QUERY_RUNNING_GAMES);

		if (q.list().size() == 0) {
			Game game = new Game();
			game.setStartedAt(time);
			game.setLifelineGrace(false);
			game.setLifelineChallenge(false);
			game.setLifelineDual(false);
			session.save(game);
			session.flush();
		}
	}

	private void finishGame(Session session, Date time) throws IOException, MultipleGamesException {
		Query q = session.createQuery(Command.QUERY_DELETE_COMMANDS);
		q.executeUpdate();

		q = session.createQuery(Game.QUERY_RUNNING_GAMES);
		List<?> list = q.list();
		// if (list.size() == 0) { TODO
		// sendErrorResponse(gson, response, "No game is running");
		// } else
		if (list.size() > 0) {
			if (list.size() > 1) {
				throw new MultipleGamesException("More than one game is running");
			} else {
				Game game = (Game) list.get(0);
				game.setEndedAt(time);
				game.setCurrentQuestion(null);
				session.save(game);
				session.flush();
			}
		}
	}

}
