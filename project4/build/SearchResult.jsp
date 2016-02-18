<%@ page import="edu.ucla.cs.cs144.*" %>
<html>
	<head>
	    <title>Results for "<%= request.getAttribute("q") %>"</title>
	</head>
	<body>
		<%
			SearchResult[] results = (SearchResult[]) request.getAttribute("results");

		 	for (int i = 0; i < results.length; i++) { %>
				<%= results[i].getName() %>
			<% } 
		%> 
	</body>
</html>
