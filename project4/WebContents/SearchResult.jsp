<%@ page import="edu.ucla.cs.cs144.*" %>
<html>
	<head>
	    <title>Results for "<%= request.getAttribute("q") %>"</title>
	    <link rel="stylesheet" type="text/css" href="./css/bootstrap.min.css">
      <link rel="stylesheet" type="text/css" href="./css/bootstrap-theme.min.css">
	</head>
	<body>
		<div class="container">
			<h1>Results for "<%= request.getAttribute("q") %>"</h1>
			<% SearchResult[] results = (SearchResult[]) request.getAttribute("results"); %>

			<table class="table table-striped table-hover">
				<thread>
					<tr>
						<th>Item ID</th>
						<th>Item Name</th>
					</tr>
				</thread>
				<tbody>
					<% for (int i = 0; i < results.length; i++) { %>
						<tr>
							<td><a href="./item?id=<%= results[i].getItemId()%>" ><%= results[i].getItemId() %></a></td>
							<td><%= results[i].getName() %></td>
						</tr>
					<% } %>
				</tbody> 
			</table>

			<ul class="pager">
				<% if ((Integer) request.getAttribute("numSkip") == 0) { %>
					<li class="previous disabled">
				<% } else { %>
					<li class="previous">
				<% } %>
					<a href="./search?q=<%= request.getAttribute("q") %>&numresultsToSkip= <%= (Integer)request.getAttribute("numSkip") - 10 %>&numresultsToReturn=10">&larr; Older</a>
				</li>
  			<li class="next"><a href="./search?q=<%= request.getAttribute("q") %>&numresultsToSkip=<%= (Integer)request.getAttribute("numSkip") + 10 %>&numresultsToReturn=10">Newer &rarr;</a></li>
			</ul>
		</div>
	</body>
</html>
