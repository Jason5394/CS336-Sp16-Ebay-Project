<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.moviebay.pkg.*" import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="style.css"/>
	<title>Sales Reports</title>
</head>	
	<%	Float totalearnings = (Float)request.getAttribute("totalearnings");
		Float genreearnings[] = (Float[])request.getAttribute("genreearnings");
		Float formatearnings[] = (Float[])request.getAttribute("formatearnings");
		LinkedList<Member> bestbuyers = (LinkedList<Member>)request.getAttribute("bestbuyers");
		LinkedList<Integer> amtbuyers = (LinkedList<Integer>)request.getAttribute("amtbuyers");
		String genreArray[] = new String[] {"Action", "Adventure", "Animation", "Comedy", "Documentary", "Drama", "Horror", "Mystery", "Romance", "Scifi", "Thriller"};
		String[] formatArray = new String[] {"VHS","DVD","Bluray"};
	%>
	<body>
		<h2>Sales Report</h2>
		<div>
			<h3>Total Earnings:&nbsp;<h3>
			<%=totalearnings%>
			<h3>Earnings by Genre</h3>
			<% for (int i = 0; i < genreArray.length; ++i){%>
			<%=genreArray[i] %> Earnings:&nbsp;<%=genreearnings[i]%><br/>
			<%}%>
			<h3>Earnings by Format</h3>
			<% for (int i = 0; i < formatArray.length; ++i){%>
			<%=formatArray[i] %> Earnings:&nbsp;<%=formatearnings[i]%><br/>
			<%}%>
			<h3>Top Bidders</h3>
			<% for (int i = 0; i < bestbuyers.size(); ++i){%>
			User:&nbsp;<%=bestbuyers.get(i).getUsername()%><br/>
			Bids Won:&nbsp;<%=amtbuyers.get(i)%><br/><br/>
			<%}%>
		</div>
	</body>
</html>