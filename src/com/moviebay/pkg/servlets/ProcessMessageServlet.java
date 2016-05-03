package com.moviebay.pkg.servlets;

import java.io.IOException;
import java.sql.SQLException;
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
 * Servlet implementation class ProcessMessageServlet
 */
@WebServlet("/processMessage")
public class ProcessMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessMessageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = ((Member)session.getAttribute("currentUser")).getUsername();
		String email_query = "SELECT * FROM Email WHERE sender='" + username + "' OR recipient='" + username + "';";
		LinkedList<Email> emails; 
		ApplicationDAO dao = new ApplicationDAO();
		try {
			emails = dao.queryDB(email_query, Email.class);
			request.setAttribute("emails", emails);
			System.out.println("Number of emails: " + emails.size());
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			dao.closeConnection();
		}
		request.getRequestDispatcher("/messagespage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
