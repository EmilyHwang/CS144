<%@ page import="edu.ucla.cs.cs144.*" %>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="./css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="./css/bootstrap-theme.min.css">
		<link rel="stylesheet" type="text/css" href="./css/custom.css">

		<%Item item = null;%>
	</head>
	<body>
		<div class="container top-space">
			<% if ((Boolean)request.getAttribute("error")) { %>
				<div class="alert alert-dismissible alert-warning">
				  <button type="button" class="close" data-dismiss="alert">&close;</button>
				  <h4>Warning!</h4>
				  <p>You are not supposed to do that! Please visit <a href="./getItem.html" class="alert-link">Item Search Page</a>.</p>
				</div>
			<% } else { %>
				<% item = (Item)request.getAttribute("item"); %>
				<div class="well bs-component" >
					<h3>Confirmation Page</h3>
					<div class="row">
						<div class="col-md-3 item-title">Item ID</div>
						<div class="col-md-9"><%= item.getId() %></div>
					</div>
					<div class="row">
						<div class="col-md-3 item-title">Item Name</div>
						<div class="col-md-9"><%= item.getName() %></div>
					</div>
					<div class="row">
						<div class="col-md-3 item-title">Item Buy Price</div>
						<div class="col-md-9"><%= item.getBuy_price() %></div>
					</div>
					<div class="row">
						<div class="col-md-3 item-title">Credit Card Number</div>
						<div class="col-md-9"><%= request.getAttribute("creditNum") %></div>
					</div>
					<div class="row">
						<div class="col-md-3 item-title">Transaction Time</div>
						<div class="col-md-9"><%= request.getAttribute("time") %></div>
					</div>
				</div>
			<% } %>
		</div>
	</body>
</html>