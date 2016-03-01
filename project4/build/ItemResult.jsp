<%@ page import="edu.ucla.cs.cs144.*" %>
<%@ page import="java.util.ArrayList" %>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="./css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="./css/bootstrap-theme.min.css">
		<link rel="stylesheet" type="text/css" href="./css/custom.css">
		
		<%Item item = null;%>
	</head>
	<body>
		<div class="container top-space">
			<form class="form-horizontal" action="./item" method="GET">
				<fieldset>
					<div class="form-group">
					  <div class="input-group">
					    <input type="text" name="id" class="form-control" placeholder="Enter item ID">
					    <span class="input-group-btn">
					      <button class="btn btn-default" type="submit">Search</button>
					    </span>
					  </div>
					</div>
			  </fieldset>
			</form>

			<% if ((Boolean)request.getAttribute("empty")) { %>
				<h1>Please enter an item ID </h1>
			<% } else if ((Boolean)request.getAttribute("not_found")) { %>
				<h1>I'm sorry but there is no item with that ID </h1>
			<% } else { %>
				<%item = (Item) request.getAttribute("item"); %>
				
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
						<tr style="line-height: 25pt;">
							<td>Buy Price</td>
							<td>
								<%= item.getBuy_price() %>
								<% if (item.getBuy_price() != "") %>
									<a href="./credit?id=<%= item.getId() %>" class="btn btn-info" style="float: right;">Pay Now</a>						 
							</td>
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
			<% } %>
			<h3>Map</h3>
			<script type="text/javascript" 
				src="http://maps.google.com/maps/api/js?sensor=false"> 
			</script> 
			<script type="text/javascript"> 
				function initialize() { 
					var latlng = new google.maps.LatLng(-34.397, 150.644);
					var location = null;
					var latitude = null;
					var longitude = null;
					
					<% if (item != null) {%>
						location = "<%=item.getLocation().get(0) + ", " + item.getCountry()%>";
						latitude = "<%=item.getLocation().get(1)%>";
						longitude = "<%=item.getLocation().get(2)%>";
					<% }%>
					
					if(latitude && longitude) {
						latlng = new google.maps.LatLng(latitude, longitude);
					} 
					
					var myOptions = { 
						zoom: 14, // default is 8  
						center: latlng, 
						mapTypeId: google.maps.MapTypeId.ROADMAP 
					}
				
					var map = new google.maps.Map(document.getElementById("map_canvas"), 
						myOptions); 
					
					if (!(latitude && longitude)){
						var geocoder = new google.maps.Geocoder();
						
						geocoder.geocode({'address': location}, function(results, status){
							if (status === google.maps.GeocoderStatus.OK) {
								map.setCenter(results[0].geometry.location);
								var marker = new google.maps.Marker({
									map: map,
									position: results[0].geometry.location
								});
							} else {
								console.log("Geocoding failed");
							  map.setZoom(1);
							}
						});
					} else {
						var marker = new google.maps.Marker({
							map: map,
							position: latlng
						});
					}
				} 
			</script> 
			<div id="map_canvas" style="width:100%; height:400px"></div>
			<script type="text/javascript"> 
				initialize();
			</script>
		</div>

	</body>
</html>
