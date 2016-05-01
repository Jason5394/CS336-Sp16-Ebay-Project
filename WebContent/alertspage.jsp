<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.moviebay.pkg.*" import ="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="style.css"/>
<title>Alerts Page</title>
</head>
<body>
	<%	LinkedList<Alert> alerts = (LinkedList<Alert>)request.getAttribute("alerts"); %>
	<div><a href="ProcessMainPageServlet">Main Page</a></div>
	<div><h2>Alerts Page</h2></div>
	<div>
		<h3>Set an alert</h3>
		<form action="MakeAlertServlet" method="GET">	
			Movie&nbsp;Title:&nbsp;<input type="text" name="title"/>
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
			<select name="format">
				<option selected="selected" disabled="disabled">Format</option>
				<option value="bluray">BluRay</option>
				<option value="dvd">DVD</option>
				<option value="vhs">VHS</option>
			</select>
		</form>
		<div style="color: #FF0000;">${noFormat}</div>
		<div style="color: #009900;">${goodAlert}</div>
	</div>
	<div>
		<h3>Your&nbsp;Alerts</h3>
		<table>
			<tr>
				<th></th>
				<th>Movie Title</th>
				<th>Genre</th>
				<th>Format</th>
			</tr>
			<% 	
				for (int i = 0; i < alerts.size(); ++i){ %>
			<tr>
				<td>
					<form action="RemoveAlertServlet" method="post">
					<input type="submit" value="Remove"/>
					<input type="hidden" name="alertId" value="${alerts.get(i).getAlertId()}"/>
					</form>
				</td>
				<td><%= alerts.get(i).getMovieTitle() %></td>
				<td><%= alerts.get(i).getGenre() %></td>
				<td><%= alerts.get(i).getMovieFormat()%></td>
			</tr>
			<%} %>
		</table>
	</div>
</body>
</html>