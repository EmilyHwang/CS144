package edu.ucla.cs.cs144;

import java.io.IOException;
import java.net.HttpURLConnection;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ProxyServlet extends HttpServlet implements Servlet {
       
    public ProxyServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //Extract query
		String query = request.getParameter("q");
		
		//issue request to Google suggest service
		URL url = new URL("http://google.com/complete/search?output=toolbar&q="+ URLEncoder.encode(query, "UTF-8"));
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		
		//Read Google suggest response
		BufferedReader connResponse = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
		
		String connResponseStr = "";
		String line;
		while ((line = connResponse.readLine()) != null){
			connResponseStr += line;
		}
		
		httpConn.disconnect();
		connResponse.close();
		
		response.setContentType("text/xml");
		response.getWriter().write(connResponseStr);
		
    }
}
