package com.moviebay.pkg.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.ServletException;
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
		String recipient = request.getParameter("recipient");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		if (recipient == null || recipient.trim() == "")
		{
			request.setAttribute("noRecipient", "Must specify another user as the recipient of your message.");
			request.getRequestDispatcher("ProcessMessageServlet").forward(request, response);
			return;
		}
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
			request.setAttribute("noRecipient", "Invalid Recipient. Specified User does not exist.");
			request.getRequestDispatcher("ProcessMessageServlet").forward(request, response);
			return;
		}
		if (subject == null || subject.trim() == "")
		{
			subject = "No Subject";
		}
		if(content == null || content.trim() == "")
		{
			request.setAttribute("noContent", "Your message must have a body.");
			request.getRequestDispatcher("ProcessMessageServlet").forward(request, response);
			return;
		}
		HttpSession session = request.getSession();
		String username = ((Member)session.getAttribute("currentUser")).getUsername();
		Date now = new Date();
		Timestamp dateTime = new Timestamp(now.getTime());	
		Email email = new Email(null, username, recipient, subject, dateTime, content);
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
