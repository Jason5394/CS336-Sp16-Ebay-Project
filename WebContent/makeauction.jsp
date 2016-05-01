<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="style.css"/>
	<title>Make an Auction</title>
</head>
<body>
	<div><a href="ProcessMainPageServlet">Main Page</a></div>
	<div>
		<h2>Auction a Movie</h2>
	</div>
	<div>
		<form action="MakeAuctionServlet" method="GET">
			<h3>Item Information</h3>
			Movie Title* &nbsp;<input type="text" name="title"/>
			<br></br>
			Duration &nbsp;<input type="number" name="duration" min="0" step="1"/>  
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
			<br></br>
			<select name="format">
				<option selected="selected" disabled="disabled">Format*</option>
				<option value="bluray">BluRay</option>
				<option value="dvd">DVD</option>
				<option value="vhs">VHS</option>
			</select>
			<br></br>
			<textarea rows="5" cols="50" name="description" placeholder="Provide a description of the movie." ></textarea>
			<br></br>
			<h3>Auction Information</h3>
			Auction Length*
			<select name="auctionLength">
				<option value="1">1 day</option>
				<option value="3">3 days</option>
				<option value="7">7 days</option>
				<option value="14">14 days</option>
			</select>
			<br></br>
			Minimum Increment Price* &nbsp;<input type="number" name="minprice" min="0.01" step="0.01"/>
			<br></br>
			Hidden Minimum Price (Optional)&nbsp;<input type="number" name="hiddenmin" min="0.00" step="0.01"/>
			<br></br>
			<input type="submit" value="Submit"/>
			&#09;*Required fields
		</form>
	</div>
	<div style="color: #FF0000;">${noTitle}</div>
	<div style="color: #FF0000;">${noFormat}</div>
	<div style="color: #FF0000;">${badMinimumIncr}</div>
	<div style="color: #FF0000;">${badDuration}</div>
	<div style="color: #FF0000;">${badHiddenMin}</div>
</body>
</html>