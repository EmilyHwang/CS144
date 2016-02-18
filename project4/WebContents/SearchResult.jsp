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
							<td><%= results[i].getItemId() %></td>
							<td><%= results[i].getName() %></td>
						</tr>
					<% } %>
				</tbody> 
			</table>
		</div>
	</body>
</html>
