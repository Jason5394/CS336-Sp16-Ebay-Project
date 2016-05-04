<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.moviebay.pkg.*" import="java.util.*" import="java.sql.Timestamp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Auction Page</title>
<link rel="stylesheet" type="text/css" href="style.css"/>
</head>
<body>
	<%	
		Member user = (Member)session.getAttribute("currentUser");
		Date date = new Date();
		Timestamp now = new Timestamp(date.getTime());
		Item item = (Item)request.getAttribute("item");
		Auction auction = (Auction)request.getAttribute("auction");	
		LinkedList<Bid> bids = (LinkedList<Bid>)request.getAttribute("bids");
		LinkedList<Item> itemResults = (LinkedList<Item>)request.getAttribute("simItems");
		LinkedList<Auction> auctionResults = (LinkedList<Auction>)request.getAttribute("simAucts");
	%>
	<%	if (now.after(auction.getEndDateTime())){%>
		<div><h1 style="color:red;">This bid is now closed.</h1></div>
	<%} %>	
	<div><a href="ProcessMainPageServlet">Main Page</a></div>
	<div class="container">
		<div class="auction">
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
				<b>Seller:&nbsp;</b><a href="ProcessProfileServlet?user=<%=auction.getSeller() %>"><%=auction.getSeller() %></a> <br/>
				<b>Auction&nbsp;End:&nbsp;</b><%=auction.getEndDateTime() %> <br/>
				<b>Minimum&nbsp;Increment:&nbsp;</b><%=auction.getMinimumIncrement() %> <br/>
				<b>Current&nbsp;Bid:&nbsp;</b><%=auction.getTopBid() %> <br/>
				<b>Minimum&nbsp;Bid:&nbsp;</b>${auction.getTopBid()+auction.getMinimumIncrement() }<br/>
				<b>Bidder:&nbsp;</b><%=auction.getBidder() %> <br/>
				<!-- Only show bid options for users who didn't put up item for auction -->
				<%if (!user.getUsername().equals(auction.getSeller())){ %>
				<form action="ProcessBidServlet" method="post">
					<input type="number" name="bid" step="0.01"/>
					<input type="submit" value="Bid"/>
					<input type="hidden" name="auctionId" value="${auction.getAuctionId()}"/>
					<input type="hidden" name="itemId" value="${item.getItemId()}"/>
				</form>
				<form action="MakeUpperLimServlet" method="post">
					<input type="number" name="upperLimit" step="0.01"/>
					<input type="submit" value="Upper Limit (Auto-bid)"/>
					<input type="hidden" name="auctionId" value="${auction.getAuctionId()}"/>
					<input type="hidden" name="itemId" value="${item.getItemId()}"/>
				</form>
				<%} %>
			</div>
			<div style="color: #FF0000;">${badBid}</div>
			<div style="color: #FF0000;">${lowBid}</div>
			<div style="color: #009900;">${goodBid}</div>
			<div style="color: #FF0000;">${expiredBid}</div>
			<div style="color: #FF0000;">${badLim}</div>
			<div style="color: #FF0000;">${lowLim}</div>
			<div style="color: #009900;">${goodLim}</div>
			<div>
				<h4><b>Bid History</b></h4> 
				<div class="scrollbox">
					<%for (int i = 0; i < bids.size(); ++i){ %>
						<div>
							<i>Bid:&nbsp;</i><%=bids.get(i).getBidAmount() %><br/>
							<i>Date:&nbsp;</i><%=bids.get(i).getCreationDateTime() %><br/>
							<i>Bidder:&nbsp;</i>
							<a href="ProcessProfileServlet?user=<%=bids.get(i).getBidder() %>"><%=bids.get(i).getBidder() %></a><br/>
							<%if (user.getCusRepStatus()){ %>
							<a href="DeleteBidServlet?auctionId=<%=auction.getAuctionId()%>&itemId=<%=item.getItemId()%>
										&bidId=<%=bids.get(i).getBidId()%>">Remove Bid</a><br/>
							<%} %>
							<br/>
						</div>
					<%} %>
				</div>
			</div>
			<div>
				<h4><b>Similar Movies Auctioned</b></h4>
				<table>
				<tr>
					<th>Movie Title</th>
					<th>Genre</th>
					<th>Length</th>
					<th>Format</th>
					<th>Seller</th>
					<th>Auction End</th>
				</tr>
				<% 	
					for (int i = 0; i < itemResults.size(); ++i){ 
						String title = itemResults.get(i).getTitle();
						Integer itemId = itemResults.get(i).getItemId();
						Integer auctionId = itemResults.get(i).getAuctionId();
				%>
				<tr>
					<td>
						<a href="LoadItemServlet?itemId=<%=itemId%>&auctionId=<%=auctionId%>"><%=title%></a>
					</td>
					<td><%= itemResults.get(i).getGenre() %></td>
					<td><%= itemResults.get(i).getLength() %></td>
					<td><%= itemResults.get(i).getFormat()%></td>
					<td><%= itemResults.get(i).getSeller()%></td>
					<td><%= auctionResults.get(i).getEndDateTime() %></td>
				</tr>
				<%} %>
			</table>
			</div>
		</div>
		<%if (user.getCusRepStatus() || user.getUsername().equals(auction.getSeller())){ %>
			<div class="changes">
				<h3>Make Changes</h3>
				<form action="ModifyAuctionServlet" method="GET">
					<h3>Modify Information</h3>
					Movie Title&nbsp;<input type="text" name="title"/>
					<br></br>
					Duration&nbsp;<input type="number" name="duration" min="0" step="1"/>  
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
					<textarea rows="5" cols="50" name="description" placeholder="Change description of movie." ></textarea>
					<br></br>
					<h3>Auction Information</h3>
					<select name="auctionLength">
						<option selected="selected" disabled="disabled">Add Time</option>
						<option value="1">1 day</option>
						<option value="3">3 days</option>
						<option value="7">7 days</option>
						<option value="14">14 days</option>
					</select>
					<br></br>
					Minimum Increment Price&nbsp;<input type="number" name="minprice" min="0.01" step="0.01"/>
					<br></br>
					Hidden Minimum Price&nbsp;<input type="number" name="hiddenmin" min="0.00" step="0.01"/>
					<br></br>
					<input type="submit" value="Make Changes"/>
					
					<input type="hidden" value="<%=item.getItemId()%>" name="itemId"/>
					<input type="hidden" value="<%=auction.getAuctionId()%>" name="auctionId"/>
				</form>
				<div style="color: #FF0000;">${badDuration}</div>
				<div style="color: #FF0000;">${badMinimumIncr}</div>
				<div style="color: #FF0000;">${badHiddenMin}</div>
				<div style="color: #009900;">${updatedItem}</div>
				<div style="color: #009900;">${updatedAuction}</div>
			</div>
		<%} %>
	</div>
	
</body>
</html>