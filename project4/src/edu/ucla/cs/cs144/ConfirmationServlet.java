package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ConfirmationServlet extends HttpServlet implements Servlet {
	public ConfirmationServlet() {}

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  	String itemId = request.getParameter("id");
		HttpSession session = request.getSession(true);
		Item item = (Item)session.getAttribute("Item" + itemId);
		String creditNum = request.getParameter("creditNum");

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		if (item == null) {
			request.setAttribute("error", true);
		} else {
			request.setAttribute("error", false);
			request.setAttribute("item", item);
			request.setAttribute("creditNum", creditNum);
			request.setAttribute("time", df.format(date));
		}
  	request.getRequestDispatcher("/confirm.jsp").forward(request, response);
  }
}