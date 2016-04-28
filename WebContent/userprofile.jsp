<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.moviebay.pkg.*" import ="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Profile Page</title>
<link rel="stylesheet" type="text/css" href="style.css"/>
</head>
<body>
	<%	Member user = (Member)request.getAttribute("user");
		String account_type = (String)request.getAttribute("acctype");
		LinkedList<Auction> buy_aucts = (LinkedList<Auction>)request.getAttribute("buyAuctions");
		LinkedList<Auction> sell_aucts = (LinkedList<Auction>)request.getAttribute("sellAuctions");
		LinkedList<Item> buy_items = (LinkedList<Item>)request.getAttribute("buyItems");
		LinkedList<Item> sell_items = (LinkedList<Item>)request.getAttribute("sellItems");
	%>
	<div><a href="mainpage.jsp">Main Page</a></div>
	<div>
		<h2>Profile
		</h2>
	</div>
	<h4>Account&nbsp;Information</h4>
	<div>
		<b>Username:&nbsp;</b><%=user.getUsername() %><br/>
		<b>First&nbsp;name:&nbsp;</b><%=user.getFirstName() %>&nbsp;
		<b>Last&nbsp;name:&nbsp;</b><%=user.getLastName() %><br/>
		<b>Account&nbsp;type:&nbsp;</b><%=account_type %><br/>	
	</div>
	<h4>Past&nbsp;Bids</h4>
	<div class="scrollbox">
		<%for (int i = 0; i < buy_items.size(); ++i){ 
			int itemId = buy_items.get(i).getItemId(); 
			int auctionId = buy_aucts.get(i).getAuctionId();
			String seller = buy_items.get(i).getSeller();
			%>
			<i>Item:&nbsp;</i><a href="LoadItemServlet?itemId=<%=itemId%>&auctionId=<%=auctionId%>"><%=buy_items.get(i).getTitle() %></a><br/>
			<i>Seller:&nbsp;</i><a href="ProcessProfileServlet?user=<%=seller%>"><%=buy_items.get(i).getSeller() %></a><br/>
			<i>Auction&nbsp;End:&nbsp;</i><%=buy_aucts.get(i).getEndDateTime() %><br/><br/>
		<%} %>
	</div>
	<h4>Past&nbsp;Auctions</h4>
	<div class="scrollbox">
		<%for (int i = 0; i < sell_items.size(); ++i){ 
			int itemId = sell_items.get(i).getItemId(); 
			int auctionId = sell_aucts.get(i).getAuctionId();
			String seller = sell_items.get(i).getSeller();
		%>
			<i>Item:&nbsp;</i><a href="LoadItemServlet?itemId=<%=itemId%>&auctionId=<%=auctionId%>"><%=sell_items.get(i).getTitle() %></a><br/>
			<i>Auction&nbsp;End:&nbsp;</i><%=sell_aucts.get(i).getEndDateTime() %><br/><br/>
		<%} %>
	</div>
</body>
</html>