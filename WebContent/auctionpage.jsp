<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.moviebay.pkg.*" import="java.util.*" import=java.sql.Timestamp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Auction Page</title>
<link rel="stylesheet" type="text/css" href="style.css"/>
</head>
<body>
	<%	
		Date date = new Date();
		Timestamp now = new Timestamp(date.getTime());
		Item item = (Item)request.getAttribute("item");
		Auction auction = (Auction)request.getAttribute("auction");	
		LinkedList<Bid> bids = (LinkedList<Bid>)request.getAttribute("bids");
	%>
	<%	if (now.after(auction.getEndDateTime())){%>
		<div><h1 style="color:red;">This bid is now closed.</h1></div>
	<%} %>	
	<div><a href="ProcessMainPageServlet">Main Page</a></div>
	<div>
		<h3>Auction Page</h3>
		<b>Title:&nbsp;</b><%=item.getTitle() %> <br/>
		<b>Genre:&nbsp;</b><%=item.getGenre() %> <br/>
		<b>Length&nbsp;(min):&nbsp;</b><s%=item.getLength() %> <br/>
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
	<div style="color: #009900;">${goodBid}</div>
	<div style="color: #FF0000;">${expiredBid}</div>
	<div>
		<h4><b>Bid History</b></h4> 
		<div class="scrollbox">
			<%for (int i = 0; i < bids.size(); ++i){ %>
				<div>
					<i>Bid:&nbsp;</i><%=bids.get(i).getBidAmount() %><br/>
					<i>Date:&nbsp;</i><%=bids.get(i).getCreationDateTime() %><br/>
					<i>Bidder:&nbsp;</i>
					<a href="ProcessProfileServlet?user=<%=bids.get(i).getBidder() %>"><%=bids.get(i).getBidder() %></a>
					<br/><br/>
				</div>
			<%} %>
		</div>
	</div>
</body>
</html>