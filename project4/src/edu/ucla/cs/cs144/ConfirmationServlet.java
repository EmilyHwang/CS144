package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ConfirmationServlet extends HttpServlet implements Servlet {
	public ConfirmationServlet() {}

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  // 	String itemId = request.getParameter("id");
		// HttpSession session = request.getSession(true);
		// Item item = (Item)session.getAttribute("Item" + itemId);

		// if (item == null) {
		// 	request.setAttribute("error", "Invalid access path.")
		// } else {
		// 	request.setAttribute("item", "item");
		// }
  	request.getRequestDispatcher("/confirm.jsp").forward(request, response);
  }
}