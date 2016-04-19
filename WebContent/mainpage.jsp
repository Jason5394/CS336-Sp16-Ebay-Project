<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.moviebay.pkg.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="style.css"/>	
	<title>MoviEbay - main page</title>
</head>
<body>
	<div id="header">
		<h2>MoviEbay</h2>
	</div>
	
	<!-- Formatting the search bar and the options to narrow the search (genre, duration, format) -->
	<div id="searchbar">
		<form action="SearchResServlet" method="GET">	<!-- TODO: add action for search bar form -->
			Search Movies: <input type="text" name="title"/>
			<input type="submit" value="Submit"/>
			<br></br>
			<select name="genre">
				<option selected="selected" disabled="disabled">Genre</option>
				<option value="action">Action</option>
				<option value="adventure">Adventure</option>
				<option value="animation">Animation</option>
				<option value="comedy">Comedy</option>
				<option value="documentary">Documentary</option>
				<option value="drama">Drama</option>
				<option value="horror">Horror</option>
				<option value="mystery">Mystery</option>
				<option value="romance">Romance</option>
				<option value="scifi">Sci-fi</option>
				<option value="thriller">Thriller</option>
			</select>
			<select name="duration">
				<option selected="selected" disabled="disabled">Duration</option>
				<option value="short">Short ( &lt30 min.)</option>
				<option value="medium">Medium (30 - 1hr30 min.)</option>
				<option value="long">Long ( &gt1hr30 min.)</option>
			</select>
			<select name="format">
				<option selected="selected" disabled="disabled">Format</option>
				<option value="bluray">BluRay</option>
				<option value="dvd">DVD</option>
				<option value="vhs">VHS</option>
			</select>
		</form>
	</div>
	<div id="content">
		<div id="nav">
			<h3>Navigation</h3>
			<ul>
				<li><a href="">View Profile</a></li>
				<li><a href="makeauction.jsp">Auction an Item</a></li>
				<li><a href="">Set an Alert</a></li>
			</ul>
		</div>
		<div id="mainauctions">
			<h3>Popular Auctions</h3>
		</div>
	</div>
	<div id="footer">
		Logged in as: <%Member user = (Member)session.getAttribute("currentUser");
						if (session.getAttribute("currentUser") == null)
						out.println("nobody");
						else 
						out.println(user.getUsername());%>
		<br></br>
		<a href="LogoutServlet">Logout</a>
	</div>
</body>
</html>