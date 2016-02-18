<%@ page import="edu.ucla.cs.cs144.*" %>
<%@ page import="java.util.ArrayList" %>
<html>
	<head>
	    <link rel="stylesheet" type="text/css" href="./css/bootstrap.min.css">
      <link rel="stylesheet" type="text/css" href="./css/bootstrap-theme.min.css">
	</head>
	<body>
		<div class="container">
			<form class="form-horizontal" action="./item" method="GET">
				<fieldset>
					<div class="form-group">
					  <div class="input-group">
					    <input type="text" name="id" class="form-control">
					    <span class="input-group-btn">
					      <button class="btn btn-default" type="submit">Search</button>
					    </span>
					  </div>
					</div>
			  </fieldset>
			</form>

			<% Item item = (Item) request.getAttribute("item"); %>
			<h1><%= "Search result for item: " + item.getId() %></h1>

			<table class="table table-striped table-hover" >
				<tbody>
					<tr>
						<td>Item Id</td>
						<td><%= item.getId() %></td>
					</tr>
					<tr>
						<td>Name</td>
						<td><%= item.getName() %></td>
					</tr>
					<tr>
						<td>Current Price</td>
						<td><%= item.getCurrently() %></td>
					</tr>
					<tr>
						<td>First Bid Price</td>
						<td><%= item.getFirst_bid() %></td>
					</tr>
					<tr>
						<td>Buy Price</td>
						<td><%= item.getBuy_price() %></td>
					</tr>
					<tr>
						<td>Number of Bids</td>
						<td><%= item.getNum_bids() %></td>
					</tr>
					<tr>
						<td>Location</td>
						<td><%= item.getLocation().get(0) + ", " + item.getCountry() %></td>
					</tr>
					<tr>
						<td>Start Time</td>
						<td><%= item.getStarted() %></td>
					</tr>
					<tr>
						<td>End Time</td>
						<td><%= item.getEnds() %></td>
					</tr>
					<tr>
						<td>Seller Information</td>
						<td><%= item.getSeller().get(0) + " (Rating: " + item.getSeller().get(1) + ")" %></td>
					</tr>
					<tr>
						<td>Description</td>
						<td><%= item.getDescription() %></td>
					</tr>
				</tbody>
			</table>

			<h3>Bidding History</h3>
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>Bid number</th>
						<th>Bidder ID</th>
						<th>Bidder Rating</th>
						<th>Bidder Location</th>
						<th>Time</th>
						<th>Amount</th>
					</tr>
				</thead>
				<tbody>
					<% ArrayList<ArrayList<String>> bids = (ArrayList<ArrayList<String>>) item.getBids(); %>
					<% for (int i = 0; i < bids.size(); i++) { %>
						<tr>
							<td><%= i+1 %></td>
							<td><%= bids.get(i).get(0) %></td>
							<td><%= bids.get(i).get(1) %></td>
							<td><%= bids.get(i).get(2) + ", " + bids.get(i).get(3) %></td>
							<td><%= bids.get(i).get(4) %></td>
							<td><%= bids.get(i).get(5) %></td>
						</tr>	
					<% } %>
				</tbody>
			</table>
		</div>
	</body>
</html>
