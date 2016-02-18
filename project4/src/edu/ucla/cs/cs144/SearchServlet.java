package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SearchServlet extends HttpServlet implements Servlet {
       
    public SearchServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    		String q = request.getParameter("q");
    		Integer numSkip = Integer.parseInt(request.getParameter("numresultsToSkip"));
    		Integer numReturn = Integer.parseInt(request.getParameter("numresultsToReturn"));

    		// Now we will search using the AuctionSearchClient class
    		AuctionSearchClient newSearch = new AuctionSearchClient();
    		SearchResult[] results = newSearch.basicSearch(q, numSkip, numReturn);

    		request.setAttribute("q", q);
    		request.setAttribute("results", results);
    		request.getRequestDispatcher("/SearchResult.jsp").forward(request, response);
    }
}
