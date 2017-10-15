<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Order Management</title>



<link rel="stylesheet" href="css/style.css">


</head>

<body>
		<h1>TECH CHALLENGE 50 (HYBRIS)</h1>
		<h2>Fulfillment Result</h2>
		<div class="tablecontent">
			<div class="tbl-header">
				<table cellpadding="0" cellspacing="0" border="0">
					<thead>
						<tr class="headingTr">
							<th>LOCATION</th>
							<th>DESTINATION</th>
							<th>SKU</th>
							<th>AMOUNT</th>
						</tr>
					</thead>
				</table>
			</div>
			<div class="tbl-content">
				<table cellpadding="0" cellspacing="0" border="0">
					<tbody>
						<c:forEach items="${result}" var="r">
							<tr>
								<td>${r.location}</td>
								<td>${r.destination}</td>
								<td>${r.sku}</td>
								<td>${r.amount}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
</body>
</html>
