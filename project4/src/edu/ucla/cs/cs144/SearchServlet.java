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

            AuctionSearchClient newSearch = null;
            SearchResult[] results = null;

    		// Now we will search using the AuctionSearchClient class
            if (q != "") {
                request.setAttribute("empty", false);        
        		newSearch = new AuctionSearchClient();
        		results = newSearch.basicSearch(q, numSkip, numReturn);
                if (results.length == 0) {
                    request.setAttribute("not_found", true);            
                }
                else {
                    request.setAttribute("not_found", false);            
                    request.setAttribute("numSkip", numSkip);
                    request.setAttribute("numReturn", numReturn);
            		request.setAttribute("results", results);
                }
            } else {
                request.setAttribute("empty", true);        
            }

            request.setAttribute("q", q);
    		request.getRequestDispatcher("/SearchResult.jsp").forward(request, response);
    }
}
