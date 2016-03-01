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
					<form class="form-horizontal" action="./confirmation" method="GET">
						<fieldset>
							<legend>Please provide your credit card information</legend>
							<div class="row">
								<div class="col-sm-2 item-title">Item ID</div>
								<div class="col-sm-10"><%= item.getId() %></div>
							</div>
							<div class="row">
								<div class="col-sm-2 item-title">Item Name</div>
								<div class="col-sm-10"><%= item.getName() %></div>
							</div>
							<div class="row">
								<div class="col-sm-2 item-title">Item Buy Price</div>
								<div class="col-sm-10"><%= item.getBuy_price() %></div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">Credit Card #</label>
					      <div class="col-sm-10">
					        <input type="text" name="creditCard" class="form-control" placeholder="Please Enter 16 digits number">
					      </div>
							</div>
							<div class="form-group">
								<button type="reset" class="btn btn-default">Cancel</button>
	       			 	<button type="submit" class="btn btn-primary">Submit</button>
							</div>
					  </fieldset>
					</form>
				</div>
			<% } %>
		</div>
	</body>
</html>