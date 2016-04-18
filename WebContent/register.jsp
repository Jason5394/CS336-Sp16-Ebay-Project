<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.moviebay.pkg.*" import="com.moviebay.pkg.servlets.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register page</title>
</head>
<body>
	<h1>Create your account</h1>
	<form action="RegisterServlet" method="post">
		Username <input type="text" name="username"/>
		<br></br>
		First Name <input type="text" name="firstname"/>
		<br></br>
		Last Name <input type="text" name="lastname"/>
		<br></br>
		Password <input type="password" name="password"/>
		<br></br>
		<input type="submit" value="Register"/>
	</form>

	<br></br>
	<div style="color: #FF0000;">${usernameTaken}</div>
	<div style="color: #FF0000;">${noUsername}</div>
	<div style="color: #FF0000;">${noFirstName}</div>
	<div style="color: #FF0000;">${noLastName}</div>
	<div style="color: #FF0000;">${noPassword}</div>
</body>
</html>