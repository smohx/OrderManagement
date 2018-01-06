<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html >
<head>
  <meta charset="UTF-8">
  <title>Order Management</title>
  <link rel="stylesheet" href="css/style.css">
	 
</head>

<body>


<form method="POST" action="/" enctype="multipart/form-data">
		<div>

			<div>
				<img src="images/hybris_logo.png" class="hybrislogo" />
			</div>


			<div class="inputsource">
				<input type="file" id="sourcefile" name="sourcefile" class="upload">
				<p id="sourcetext">Drop the Location file here</p>
			</div>
			<div class="inputtarget">

				<input type="file" id="targetfile" name="targetfile" class="upload">
				<p id="targettext">Drop the Destination file here</p>
			</div>

			<button type="submit" value="Submit" class="sub">
				<div class="subtext">Fulfill Orders</div>
			</button>
		</div>
	</form>
<script  src="js/jquery-3.2.1.min.js"></script>
 <script  src="js/main.js"></script>
 

</body>
</html>
