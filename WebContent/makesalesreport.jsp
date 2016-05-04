<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="style.css"/>
	<title>Sales Reports</title>
</head>
	
	<body>
		<div> Total Earnings </div>
		<form action = "SalesReportServlet">
			Select Movie: <input type = "text" name = "textfield"/>
			Select User: <input type = "text" name = "namefield"/>						
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
			
			</select>
		</form>
		
	</body>
	
</html>