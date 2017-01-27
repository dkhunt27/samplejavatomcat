<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hello!</title>
</head>
<body>
	<form action="index" method="post">

		<center>
			<h2>Hello, ${firstName} ${lastName}!</h2>
			<h3>
				<label for="name">Enter your name:</label> <input name="firstName"
					id="firstName" type="text"> <input name="lastName"
					id="lastName" type="text">
				<p></p>
				<%-- <a href='' onclick="this.href='hello?name='+document.getElementById('nameInput').value">submit</a> --%>
				<input type="submit" style="padding: 30px 30px 30px 30px;"
					value="submit" />
			</h3>
		</center>
	</form>
</body>

</html>
