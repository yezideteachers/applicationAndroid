package com.l3I.rally;

import java.io.IOException;

import javax.servlet.http.*;



@SuppressWarnings("serial")
public class RallyQuizl3IServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Bienvenue au RallyQuiz du groupe E");
		
	}
}
