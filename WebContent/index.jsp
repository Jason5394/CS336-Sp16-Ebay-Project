<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.moviebay.pkg.*" import ="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MoviEbay - the place to auction movies</title>
<link rel="stylesheet" type="text/css" href="style.css"/>
</head>
<body>
	<div id="header">
		<h1>MoviEbay</h1>
	</div>

	<hr/>
	<% if (session.getAttribute("currentUser") == null){
			out.println("<a href =\"register.jsp\">Create Account</a><br></br>");
			out.println("<a href = \"login.jsp\">Login</a>");
		}
		else {
			out.println("<a href = \"LogoutServlet\">Logout</a>");
			out.println("<br></br><a href = \"mainpage.jsp\">Main Page</a>");
		}
	%>
	<br></br>
	
</body>
</html>