<%@ page import="edu.ucla.cs.cs144.*" %>
<html>
	<head>
	    <link rel="stylesheet" type="text/css" href="./css/bootstrap.min.css">
      <link rel="stylesheet" type="text/css" href="./css/bootstrap-theme.min.css">
      <link rel="stylesheet" type="text/css" href="./css/custom.css">
	  <script type="text/javascript" src="js/AutoSuggestControl.js"></script>
		<link rel="stylesheet" type="text/css" href="css/autosuggest.css">
        <script type="text/javascript">
            window.onload = function () {
                var oTextbox = new AutoSuggestControl(document.getElementById("queryBox"));        
            }
        </script>
	</head>
	<body>
		<div class="container top-space">
			<form class="form-horizontal" action="./search" method="GET">
				<fieldset>
					<div class="form-group" id="queryDiv">
					  <div class="input-group">
						<div>
							<input type="text" name="q" class="form-control" id="queryBox"  placeholder="Search another query ...">
						</div>
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
							<a href="./search?q=<%= request.getAttribute("q") %>&numresultsToSkip=<%= (Integer)request.getAttribute("numSkip") - 10 %>&numresultsToReturn=10">&larr; Older</a>
						<% } %>
						</li>
		  			<li class="next"><a href="./search?q=<%= request.getAttribute("q") %>&numresultsToSkip=<%= (Integer)request.getAttribute("numSkip") + 10 %>&numresultsToReturn=10">Newer &rarr;</a></li>
					</ul>
				<% } %>
			<% } %>
		</div>
	</body>
</html>
