package com.moviebay.pkg.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.moviebay.pkg.ApplicationDAO;
import com.moviebay.pkg.Email;
import com.moviebay.pkg.Member;

/**
 * Servlet implementation class MakeAlertServlet
 */
@WebServlet("/makeMessage")
public class MakeMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MakeMessageServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String recipient = request.getParameter("recipient").trim();
		String subject = request.getParameter("subject").trim();
		String content = request.getParameter("content").trim();
		
		//Error checking
		if (recipient == null || recipient.trim() == "")
		{
			request.setAttribute("error", "Must specify another user as the recipient of your message.");
			request.getRequestDispatcher("ProcessMessageServlet").forward(request, response);
			return;
		}
		//Check for existent recipient
		String user_query = "SELECT * FROM Member WHERE username = '" + recipient + "';";
		LinkedList<Member> members = null; 
		ApplicationDAO dao = new ApplicationDAO();
		try {
			members = dao.queryDB(user_query, Member.class);
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			dao.closeConnection();
		}
		if (members == null || members.size() == 0)
		{
			request.setAttribute("error", "Invalid Recipient. Specified User does not exist.");
			request.getRequestDispatcher("ProcessMessageServlet").forward(request, response);
			return;
		}
		//More error checking
		if (subject == null || subject.trim() == "")
		{
			subject = "No Subject";
		}
		if (subject.length() > 100)
		{
			request.setAttribute("error", "Subject must be under 100 characters.");
			request.getRequestDispatcher("ProcessMessageServlet").forward(request, response);
			return;
		}
		if(content == null || content.trim() == "")
		{
			request.setAttribute("error", "Your message must have a body.");
			request.getRequestDispatcher("ProcessMessageServlet").forward(request, response);
			return;
		}
		//Adding more parameters
		HttpSession session = request.getSession();
		String username = ((Member)session.getAttribute("currentUser")).getUsername();
		//Quick error check
		if(username.endsWith(recipient))
		{
			request.setAttribute("error", "You cannot send yourself a message.");
			request.getRequestDispatcher("ProcessMessageServlet").forward(request, response);
			return;
		}
		Date now = new Date();
		Timestamp dateTime = new Timestamp(now.getTime());	
		Email email = new Email(null, username, recipient, subject, dateTime, content);
		
		//Query DB
		ApplicationDAO dao2 = new ApplicationDAO();
		try{
			dao2.insert(email, Email.class);
			request.setAttribute("goodEmail", "Your message has been sent.");
		} catch(SQLException e){
			e.printStackTrace();
		} finally{
			dao2.closeConnection();
		}
		request.getRequestDispatcher("ProcessMessageServlet").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
