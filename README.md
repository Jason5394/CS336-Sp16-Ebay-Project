# Moviebay

####Background
Project Members:
Jason Yang
Haris Samim
David Kollender

  Moviebay is an online auction site for users to sell and bid on movies.  Users can create accounts, auction movies, and bid on them, as well as many other features.  This was the semester-long project for the class Info and Data Management at Rutgers.  The main motivation for this project was to incorporate the topics learned in class about relational databases and apply them in a dynamic web application.
  
####Implementation
  The database used to store all relevant information is MySQL, with a total of 7 tables. A data access object was then implemented to allow access to the db.
  
  The application follows the MVC design pattern.  JSP's act as the View, generating the content for each page.  In most cases, there is a corresponding servlet that pairs with each JSP, which performs the db access methods (such as querying the db, updating and deleting entries), as well as the relevant business logic for each feature.   
  
####Main Features

  - Create/Delete User accounts
  - Auction movies
  - Bid on movies
  - Search for movies with specifications
  - View user profiles
  - Send messages to users
  
  Along with the above, special accounts like Admins and Customer Representatives have extra features.  Admins can create new customer representative accounts, while customer reps can delete illegal auctions and modify any info about existing auctions.
