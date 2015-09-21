/**
 *
 */
package com.mk.fl.bibgyani.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;

import com.google.gson.Gson;
import com.mk.fl.bibgyani.exceptions.MultipleGamesException;
import com.mk.fl.bibgyani.exceptions.NoGameException;
import com.mk.fl.bibgyani.model.Game;
import com.mk.fl.bibgyani.to.DefaultResponseTO;

/**
 * @author Mouli Kalakota
 */
public final class BibgyaniUtil {

	public static Session openSession() {
		return HibernateUtil.getSessionFactory().openSession();
	}

	public static Game getRunningGame(Session session) throws NoGameException, MultipleGamesException {
		Query q = session.createQuery(Game.QUERY_RUNNING_GAMES);
		List<?> list = q.list();
		if (list.size() == 0) {
			throw new NoGameException("Currently no game is running");
		} else if (list.size() > 1) {
			throw new MultipleGamesException("More than one game is running");
		}
		session.flush();

		Game game = (Game) list.get(0);
		return game;
	}

	public static Game getRunningGame(Session session, boolean silent) throws NoGameException, MultipleGamesException {
		Query q = session.createQuery(Game.QUERY_RUNNING_GAMES);
		List<?> list = q.list();
		if (list.size() == 0) {
			if (silent) {
				return null;
			}
			throw new NoGameException("Currently no game is running");
		} else if (list.size() > 1) {
			throw new MultipleGamesException("More than one game is running");
		}
		session.flush();

		Game game = (Game) list.get(0);
		return game;
	}

	public static void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
		DefaultResponseTO resTO = new DefaultResponseTO();
		resTO.setError(true);
		resTO.setMessage(message);
		String outJson = new Gson().toJson(resTO);

		response.setContentType("application/json");
		response.getWriter().write(outJson);
	}

}
