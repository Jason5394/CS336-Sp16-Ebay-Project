<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="com.moviebay.pkg.*"
	import="java.sql.Timestamp" import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="style.css" />
<title>Messages</title>
</head>
<body>
	<%
		LinkedList<Email> inboxEmails = (LinkedList<Email>) request.getAttribute("inboxEmails");
		LinkedList<Email> sentEmails = (LinkedList<Email>) request.getAttribute("sentEmails");
	%>
	<div>
		<a href="ProcessMainPageServlet">Main Page</a>
	</div>
	<div>
		<h2>Messages</h2>
	</div>
	<div>
		<h3>Send A Message</h3>
		<form action="MakeMessageServlet" method="GET">
			To&nbsp;<input type="text" name="recipient" /> Subject&nbsp;<input
				type="text" name="subject" /> <br></br>
			<textarea rows="5" cols="50" name="content"
				placeholder="Enter message body here."></textarea>
			<input type="submit" value="Submit" />
		</form>
		<div style="color: #FF0000;">${error}</div>
		<div style="color: #009900;">${goodEmail}</div>
	</div>
	<div>
		<h3>Your&nbsp;Recent&nbsp;Messages</h3>
		<table>
			<tr>
				<td>
					<h3>Inbox&nbsp;</h3>
					<div class="mainscrollbox">
						<%
							for (int i = 0; i < inboxEmails.size(); ++i) {
								int email_id = inboxEmails.get(i).getEmailId();
								String sender = inboxEmails.get(i).getSender();
								String subject = inboxEmails.get(i).getSubject();
								Timestamp date_time = inboxEmails.get(i).getDateTime();
								String content = inboxEmails.get(i).getContent();
						%>
						<i>From:&nbsp;</i><%=sender%><br />
						<i>Date&Time:&nbsp;</i><%=date_time%><br /> 
						<i>Subject:&nbsp;</i><%=subject%><br />
						<%=content%><br /> <br />
						<%
							}
						%>
					</div>
				</td>
				<td>
					<h3>Sent&nbsp;</h3>
					<div class="mainscrollbox">
						<%
							for (int i = 0; i < sentEmails.size(); ++i) {
								int email_id = sentEmails.get(i).getEmailId();
								String recipient = sentEmails.get(i).getRecipient();
								String subject = sentEmails.get(i).getSubject();
								Timestamp date_time = sentEmails.get(i).getDateTime();
								String content = sentEmails.get(i).getContent();
						%>
						<i>To:&nbsp;</i><%=recipient%><br />
						<i>Date&Time:&nbsp;</i><%=date_time%><br /> 
						<i>Subject:&nbsp;</i><%=subject%><br />
						<%=content%><br /> <br />
						<%
							}
						%>
					</div>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>