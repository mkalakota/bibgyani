package com.mk.fl.bibgyani;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.google.gson.Gson;
import com.mk.fl.bibgyani.model.Game;
import com.mk.fl.bibgyani.model.Question;
import com.mk.fl.bibgyani.to.QuestionTO;
import com.mk.fl.bibgyani.util.BibgyaniUtil;
import com.mk.fl.bibgyani.util.ExcelUtil;

/**
 * Servlet implementation class QuestionServlet
 */
public class QuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QuestionServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		Session session = BibgyaniUtil.openSession();
		String id = request.getPathInfo();
		Transaction tx = null;
		Gson gson = new Gson();
		try {
			if (null != id && !id.isEmpty()) {
				id = id.substring(1); // to eliminate '/'
				if ("delete".equalsIgnoreCase(id)) {
					doDelete(request, response);
					return;
				}
				tx = session.beginTransaction();
				Question question = (Question) session.load(Question.class, new Integer(id));
				Game game = BibgyaniUtil.getRunningGame(session);
				tx.commit();
				QuestionTO to = new QuestionTO(question);
				to.setSequence(game.getCurrentQuestionSequence());
				String json = gson.toJson(to);
				response.getWriter().write(json);
			} else {
				tx = session.beginTransaction();
				Query q = session.createQuery(Question.QUERY_SELECT_ALL_QUESTIONS);
				List<?> list = q.list();
				tx.commit();
				List<QuestionTO> questions = new ArrayList<QuestionTO>(list.size());
				for (Object o : list) {
					if (o instanceof Question) {
						questions.add(new QuestionTO((Question) o));
					}
				}
				String json = gson.toJson(questions);
				response.getWriter().write(json);
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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
		if (!isMultipartContent) {
			BibgyaniUtil.sendErrorResponse(response, "Not a file");
			return;
		}

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

		Session session = BibgyaniUtil.openSession();
		InputStream stream = null;
		Transaction tx = null;
		try {
			List<FileItem> fields = upload.parseRequest(request);
			Iterator<FileItem> iterator = fields.iterator();
			if (!iterator.hasNext()) {
				BibgyaniUtil.sendErrorResponse(response, "No fields found");
				return;
			}
			while (iterator.hasNext()) {
				FileItem item = iterator.next();
				if (!item.isFormField()) {
					stream = item.getInputStream();
					tx = session.beginTransaction();
					List<Question> questions = ExcelUtil.readExcel(stream);
					for (Question question : questions) {
						session.save(question);
					}
					tx.commit();
					stream.close();
				}
			}
		} catch (Exception e) {
			if (null != stream) {
				stream.close();
			}
			if (null != tx) {
				tx.rollback();
			}
			BibgyaniUtil.sendErrorResponse(response, e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Session session = BibgyaniUtil.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query q = session.createQuery(Question.QUERY_DELETE_ALL_QUESTIONS);
			q.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			BibgyaniUtil.sendErrorResponse(response, e.getMessage());
		} finally {
			session.close();
		}
	}

}
