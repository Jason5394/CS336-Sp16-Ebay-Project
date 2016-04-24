<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.moviebay.pkg.*" import ="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Auction Page</title>
<link rel="stylesheet" type="text/css" href="style.css"/>
</head>
<body>
	<%	Item item = (Item)request.getAttribute("item");
	Auction auction = (Auction)request.getAttribute("auction");	%>
	<div><a href="mainpage.jsp">Main Page</a></div>
	<div>
		<h3>Auction Page</h3>
		<b>Title:&nbsp;</b><%=item.getTitle() %> <br/>
		<b>Genre:&nbsp;</b><%=item.getGenre() %> <br/>
		<b>Length&nbsp;(min):&nbsp;</b><%=item.getLength() %> <br/>
		<b>Format:&nbsp;</b><%=item.getFormat() %> 
	</div>
	<br/>
	<div>
		<b>Description</b>
		<p><%=item.getDescription() %></p>
	</div>
	<div>
		<b>Seller:&nbsp;</b><%=auction.getSeller() %> <br/>
		<b>Auction&nbsp;End:&nbsp;</b><%=auction.getEndDateTime() %> <br/>
		<b>Minimum&nbsp;Increment:&nbsp;</b><%=auction.getMinimumIncrement() %> <br/>
		<b>Current&nbsp;Bid:&nbsp;</b><%=auction.getTopBid() %> <br/>
		<b>Minimum&nbsp;Bid:&nbsp;</b>${auction.getTopBid()+auction.getMinimumIncrement() }<br/>
		<b>Bidder:&nbsp;</b><%=auction.getBidder() %> <br/>
		<form action="ProcessBidServlet" method="post">
			<input type="number" name="bid" step="0.01"/>
			<input type="submit" value="Bid"/>
			<input type="hidden" name="auctionId" value="${auction.getAuctionId()}"/>
			<input type="hidden" name="itemId" value="${item.getItemId()}"/>
		</form>
	</div>
	<div style="color: #FF0000;">${badBid}</div>
	<div style="color: #FF0000;">${lowBid}</div>
	<div style="color: #00FF00;">${goodBid}</div>
	<div style="color: #FF0000;">${bidExpired}</div>
</body>
</html>