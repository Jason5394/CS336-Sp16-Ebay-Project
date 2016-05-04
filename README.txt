Project Members
Jason Yang
Haris Samim
David Kollender

Contents:
	- SQL Scripts
		- proj2016.sql: main sql script that creates the tables 
		- triggers.sql: sql script that creates triggers 
	- Servlets
		- Most of the servlets interface with the database - some of them populate jsp pages with 
			information from the db, others deal with forms submitted by the jsp pages 
		- Acts as the Controller in the MVC design pattern.
	- jsp pages
		- Responsible for displaying the UI to the user
		- Acts as the View in the MVC design pattern.
	- Java Classes
		- Each class represents a corresponding db table.
		- ApplicationDAO.java contains all the important functions for connecting, querying and modifying the target db.
		- Acts as the Model in the MVC design pattern.
		
Credit (rough estimations)
	Jason Yang
		.java:
			ApplicationDAO
			AuthenticationFilter
			LoginServlet
			LogoutServlet
			LoadItemServlet
			SearchResServlet
			RegisterServlet
			ProcessMainPageServlet
			ProcessBidServlet
			MakeAuctionServlet
			ModifyAuctionServlet
			DeleteAuctionServlet
		.jsp:
			mainpage
			makeauction
			searchresults
			register
		.sql:
			triggers
			
	Haris Samim	
		.java:
			Alert
			Auction
			Bid
			Email
			Item
			Member
			UpperLimit
			SalesReportServlet
			ProcessProfileServlet
		.jsp:
			makesalesreport
			index
			login
			userprofile
		.sql:
			proj2016	
		.css:
			style
			
	David Kollender
		.java:
			MakeMessageServlet
			ProcessMessageServlet
			MakeUpperLimServlet
			DeleteAccountServlet
			DeleteBidServlet
			MakeAlertServlet
			ProcessAlertServlet
			RemoveAlertServlet
		.jsp:
			alertspage
			auctionpage
			makesalesreport
			messagespage
			
			