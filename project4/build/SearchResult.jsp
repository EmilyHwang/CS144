<%@ page import="edu.ucla.cs.cs144.*" %>
<html>
	<head>
	    <link rel="stylesheet" type="text/css" href="./css/bootstrap.min.css">
      <link rel="stylesheet" type="text/css" href="./css/bootstrap-theme.min.css">
	</head>
	<body>
		<div class="container">
			<form class="form-horizontal" action="./search" method="GET">
				<fieldset>
					<div class="form-group">
					  <div class="input-group">
					    <input type="text" name="q" class="form-control" placeholder="Search another query ...">
					    <span class="input-group-btn">
					      <button class="btn btn-default" type="submit">Search</button>
					    </span>
					  </div>
					</div>
			    <input class="hidden" name="numresultsToSkip" type="text" value="0" />
					<input class="hidden" name="numresultsToReturn" type="text" value="10" />
			  </fieldset>
			</form>

			<% if ((Boolean)request.getAttribute("empty")) { %>
				<h1>Please enter a search query</h1>
			<% } else { %>
				<% if ((Boolean)request.getAttribute("not_found")) { %>
					<h1>There is no result for "<%= request.getAttribute("q") %>"</h1>
				<% } else { %>
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
							<a href="#">&larr; Older</a>
						<% } else { %>
							<li class="previous">
							<a href="./search?q=<%= request.getAttribute("q") %>&numresultsToSkip= <%= (Integer)request.getAttribute("numSkip") - 10 %>&numresultsToReturn=10">&larr; Older</a>
						<% } %>
						</li>
		  			<li class="next"><a href="./search?q=<%= request.getAttribute("q") %>&numresultsToSkip=<%= (Integer)request.getAttribute("numSkip") + 10 %>&numresultsToReturn=10">Newer &rarr;</a></li>
					</ul>
				<% } %>
			<% } %>
		</div>
	</body>
</html>
