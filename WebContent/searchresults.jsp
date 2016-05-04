<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.moviebay.pkg.*" import ="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Results</title>
<link rel="stylesheet" type="text/css" href="style.css"/>	
</head>
<body>
	<div><a href="ProcessMainPageServlet">Main Page</a></div>
	<% 	
		Member user = (Member)session.getAttribute("currentUser");
		boolean isCusRep = user.getCusRepStatus();
		String title = request.getParameter("title");
		String genre = request.getParameter("genre");
		String duration = request.getParameter("duration");
		String format = request.getParameter("format");
		LinkedList<Item> itemResults = (LinkedList<Item>)request.getAttribute("itemResults");
		LinkedList<Auction> auctionResults = (LinkedList<Auction>)request.getAttribute("auctionResults");
		if (itemResults == null)
			out.println("No search results returned.");
		else{ 
			out.println("Search returned " + itemResults.size() + " results.");
	%>
	<table>
		<tr>
			<th>Movie Title</th>
			<th>Genre</th>
			<th>Length</th>
			<th>Format</th>
			<th>Seller</th>
			<th>Auction End</th>
			<%if (isCusRep){ %>
			<th>Delete</th>
			<%} %>
		</tr>
		<% 	
			for (int i = 0; i < itemResults.size(); ++i){ 
				String titleres = itemResults.get(i).getTitle();
				Integer itemId = itemResults.get(i).getItemId();
				Integer auctionId = itemResults.get(i).getAuctionId();
		%>
		<tr>
			<td>
				<a href="LoadItemServlet?itemId=<%=itemId%>&auctionId=<%=auctionId%>"><%=titleres%></a>
			</td>
			<td><%= itemResults.get(i).getGenre() %></td>
			<td><%= itemResults.get(i).getLength() %></td>
			<td><%= itemResults.get(i).getFormat()%></td>
			<td><%= itemResults.get(i).getSeller()%></td>
			<td><%= auctionResults.get(i).getEndDateTime() %></td>
			<%if (isCusRep){ %>
			<td>
				<a href="DeleteAuctionServlet?auctionId=<%=auctionId%>">Delete</a>
			</td>
			<%} %>
		</tr>
		<%}} %>
	</table>
</body>
</html>