<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="com.moviebay.pkg.*"
	import="java.sql.Timestamp" import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="style.css" />
<title>MoviEbay - main page</title>
</head>
<body>
	<%
		Member user = (Member) session.getAttribute("currentUser");
		LinkedList<Item> currItems = (LinkedList<Item>) request.getAttribute("currItems");
		LinkedList<Auction> currAucts = (LinkedList<Auction>) request.getAttribute("currAuctions");
		LinkedList<Bid> currBids = (LinkedList<Bid>) request.getAttribute("currBids");
		LinkedList<Item> pastItems = (LinkedList<Item>) request.getAttribute("pastItems");
		LinkedList<Auction> pastAucts = (LinkedList<Auction>) request.getAttribute("pastAuctions");
		LinkedList<Bid> pastBids = (LinkedList<Bid>) request.getAttribute("pastBids");
		LinkedList<Item> currBidItems = (LinkedList<Item>) request.getAttribute("currBidItems");
		LinkedList<Auction> currBidAucts = (LinkedList<Auction>) request.getAttribute("currBidAucts");
		LinkedList<Item> pastBidItems = (LinkedList<Item>) request.getAttribute("pastBidItems");
		LinkedList<Auction> pastBidAucts = (LinkedList<Auction>) request.getAttribute("pastBidAucts");
		LinkedList<UpperLimit> upperLimits = (LinkedList<UpperLimit>) request.getAttribute("upperLimits");
	%>
	<div id="header">
		<h2>MoviEbay</h2>
	</div>

	<!-- Formatting the search bar and the options to narrow the search (genre, duration, format) -->
	<div id="searchbar">
		<form action="SearchResServlet" method="GET">
			<!-- TODO: add action for search bar form -->
			Search Movies: <input type="text" name="title" /> <input
				type="submit" value="Submit" /> <br></br> <select name="genre">
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
			</select> <select name="duration">
				<option selected="selected" disabled="disabled">Duration</option>
				<option value="short">Short ( &lt30 min.)</option>
				<option value="medium">Medium (30 - 1hr30 min.)</option>
				<option value="long">Long ( &gt1hr30 min.)</option>
			</select> <select name="format">
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
				<li><a
					href="ProcessProfileServlet?user=<%=user.getUsername()%>">View
						Profile</a></li>
				<li><a href="makeauction.jsp">Auction an Item</a></li>
				<li><a href="ProcessAlertServlet">My Alerts</a></li>
				<li><a href="ProcessMessageServlet">My Messages</a></li>
				<%
					if (user.getAdminStatus() == true) {
				%>
				<li><a href="register.jsp">Create Customer Rep Account</a></li>
				<%
					}
				%>
			</ul>
		</div>
		<div id="mainauctions">
			<table>
				<tr>
					<td>
						<h3>Your Current Auctions</h3>
						<div class="mainscrollbox">
							<%
								for (int i = 0; i < currItems.size(); ++i) {
									int itemId = currItems.get(i).getItemId();
									int auctionId = currAucts.get(i).getAuctionId();
									String seller = currAucts.get(i).getSeller();
									Float topBid = currAucts.get(i).getTopBid();
							%>
							<i>Item:&nbsp;</i><a
								href="LoadItemServlet?itemId=<%=itemId%>&auctionId=<%=auctionId%>"><%=currItems.get(i).getTitle()%></a><br />
							<i>Auction&nbsp;End:&nbsp;</i><%=currAucts.get(i).getEndDateTime()%><br />
							<i>Top&nbsp;Bid:&nbsp;</i><%=topBid%><br /> <br />
							<%
								}
							%>
						</div>
					</td>
					<td>
						<h3>Your Current Bids</h3>
						<div class="mainscrollbox">
							<%
								for (int i = 0; i < currBids.size(); ++i) {
									int itemId = currBidItems.get(i).getItemId();
									int auctionId = currBidAucts.get(i).getAuctionId();
									String seller = currBidAucts.get(i).getSeller();
									Float topBid = currBidAucts.get(i).getTopBid();
									String upperLimit = "none";
									for (int j = 0; j < upperLimits.size(); ++j) {
										if (upperLimits.get(j).getAuctionId() == auctionId)
											upperLimit = upperLimits.get(j).getUpperLimit().toString();
									}
							%>
							<i>Item:&nbsp;</i><a
								href="LoadItemServlet?itemId=<%=itemId%>&auctionId=<%=auctionId%>"><%=currBidItems.get(i).getTitle()%></a><br />
							<i>Auction&nbsp;End:&nbsp;</i><%=currBidAucts.get(i).getEndDateTime()%><br />
							<i>Your&nbsp;Bid:&nbsp;</i><%=currBids.get(i).getBidAmount()%><br />
							<i>Top&nbsp;Bid:&nbsp;</i><%=topBid%><br /> <i>Upper&nbsp;Limit:&nbsp;</i><%=upperLimit%><br />
							<br />
							<%
								}
							%>
						</div>
					</td>
				</tr>
			</table>
			<table>
				<tr>
					<td>
						<h3>Your Past Auctions</h3>
						<div class="mainscrollbox">
							<%
								for (int i = 0; i < pastItems.size(); ++i) {
									int itemId = pastItems.get(i).getItemId();
									int auctionId = pastAucts.get(i).getAuctionId();
									String seller = pastAucts.get(i).getSeller();
							%>
							<i>Item:&nbsp;</i><a
								href="LoadItemServlet?itemId=<%=itemId%>&auctionId=<%=auctionId%>"><%=pastItems.get(i).getTitle()%></a><br />
							<i>Auction&nbsp;End:&nbsp;</i><%=pastAucts.get(i).getEndDateTime()%><br />
							<%
								}
							%>
						</div>
					</td>
					<td>
						<h3>Your Past Bids</h3>
						<div class="mainscrollbox">
							<%
								for (int i = 0; i < pastBids.size(); ++i) {
									int itemId = pastBidItems.get(i).getItemId();
									int auctionId = pastBidAucts.get(i).getAuctionId();
									String seller = pastBidAucts.get(i).getSeller();
							%>
							<i>Item:&nbsp;</i><a
								href="LoadItemServlet?itemId=<%=itemId%>&auctionId=<%=auctionId%>"><%=pastItems.get(i).getTitle()%></a><br />
							<i>Auction&nbsp;End:&nbsp;</i><%=pastAucts.get(i).getEndDateTime()%><br />
							<%
								if (pastAucts.get(i).getWinner() == null) {
							%>
							<i>Winner:&nbsp;</i>Nobody<br /> <br />
							<%
								} else {
							%>
							<i>Winning&nbsp;Bid:&nbsp;</i><%=pastAucts.get(i).getTopBid()%><br />
							<i>Winner:&nbsp;</i><%=pastAucts.get(i).getWinner()%><br /> <br />
							<%
								}
							%>
							<%
								}
							%>
						</div>
					</td>
				</tr>
			</table>




		</div>
	</div>
	<div id="footer">
		Logged in as:
		<%
		if (session.getAttribute("currentUser") == null)
			out.println("nobody");
		else
			out.println(user.getUsername());
	%>
		<br /> <a href="LogoutServlet">Logout</a>
	</div>
</body>
</html>