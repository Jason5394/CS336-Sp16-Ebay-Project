<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.moviebay.pkg.*" import ="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="style.css"/>
	<title>Messages</title>
</head>
<body>
	<%	LinkedList<Email> emails = (LinkedList<Email>)request.getAttribute("emails"); %>
	<div><a href="ProcessMainPageServlet">Main Page</a></div>
	<div><h2>Messages</h2></div>
	<div>
		<h3>Send A Message</h3>
		<form action="MakeMessageServlet" method="GET">	
			To&nbsp;<input type="text" name="recipient"/>
			Subject&nbsp;<input type="text" name="subject"/>
			<br></br>
			<textarea rows="5" cols="50" name="content" placeholder="Enter message body here." ></textarea>
			<input type="submit" value="Submit"/>
		</form>
		<div style="color: #FF0000;">${noRecipient}</div>
		<div style="color: #FF0000;">${noContent}</div>
		<div style="color: #009900;">${goodEmail}</div>
	</div>
	<div>
		<h3>Your&nbsp;Recent&nbsp;Messages</h3>
			<div class="mainscrollbox">
				<%for (int i = 0; i < emails.size(); ++i){ 
					int email_id = emails.get(i).getEmailId();
					String sender = emails.get(i).getSender();
					String recipient = emails.get(i).getRecipient();
					String subject = emails.get(i).getSubject();
					//Timestamp date_time = emails.get(i).getDateTime(); 
					String content = emails.get(i).getContent();
				%>
					<i>From:&nbsp;</i><%=sender%><br/>
					<i>To:&nbsp;</i><%=recipient %><br/>
					<i>Subject:&nbsp;</i><%=subject %><br/>
					<i>Body:&nbsp;</i><%=content%><br/><br/>
				<%} %>
			</div>

	</div>
</body>
</html>