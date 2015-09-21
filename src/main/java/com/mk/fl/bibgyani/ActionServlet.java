package com.mk.fl.bibgyani;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mk.fl.bibgyani.to.ActionTO;

/**
 * Servlet implementation class ActionServlet
 */
public class ActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ActionServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ActionTO to = new ActionTO();
		if (request.isUserInRole("operator")) {
			to.setShowActions(true);
			to.setShowAnswer(true);
		} else if (request.isUserInRole("host")) {
			to.setShowAnswer(true);
		}
		response.setContentType("application/json");
		Gson gson = new Gson();
		String jsonString = gson.toJson(to);
		response.getWriter().write(jsonString);
	}

}
