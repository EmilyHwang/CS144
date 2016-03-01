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
			<form class="form-horizontal" action="./confirmation" method="GET">
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
		</div>
	</body>
</html>