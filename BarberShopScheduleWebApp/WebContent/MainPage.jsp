<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="js/jquery-3.2.1.min.js">
	
</script>
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous"></script>
<title>BarberShopSchedule</title>
</head>
<body onload="loadData()">
	<table class="table" id="barber_shops_table">
		<thead class="thead-dark">
			<tr>
				<th scope="col">ID</th>
				<th scope="col">Name</th>
				<th scope="col">Email</th>
				<th scope="col">Telephone</th>
				<th scope="col">Address</th>
				<th scope="col">City</th>
				<th scope="col">Description</th>
				<th scope="col">Gender</th>
			</tr>
		<tbody></tbody>
		</thead>
	</table>

</body>
</html>

<script type="text/javascript">
	function loadData() {
		$.get('BarberShopServlet', function(responseJson) {

			console.log("Response = " + responseJson);
			var rws;
			var items = JSON.parse(responseJson);
			console.log(items);
			for (var i = 0; i < items.length; i++) {
				rws = "<tr>";
				rws += "<td>" + items[i].id + "</td>";
				rws += "<td>" + items[i].name + "</td>";
				rws += "<td>" + items[i].email + "</td>";
				rws += "<td>" + items[i].telephone + "</td>";
				rws += "<td>" + items[i].address + "</td>";
				rws += "<td>" + items[i].city + "</td>";
				rws += "<td>" + items[i].description + "</td>";
				
				switch (items[i].gender) {
				case 0:
					rws += "<td>" + "Male" + "</td>";
					break;
				case 1:
					rws += "<td>" + "Female" + "</td>";
					break;
				default:
					rws += "<td>" + "Unknown" + "</td>";
					break;
				}
				
				$('#barber_shops_table').find('tbody').append(rws);
			}

		});
	}
</script>