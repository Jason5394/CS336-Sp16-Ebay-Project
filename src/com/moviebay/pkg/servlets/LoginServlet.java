package com.moviebay.pkg.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.moviebay.pkg.ApplicationDAO;
import com.moviebay.pkg.Member;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String)request.getParameter("username");
		String password = (String)request.getParameter("password");
		LinkedList<Member> member;
		
		if (username.isEmpty() || password.isEmpty()){
			//if user left either username or password blank, "refresh" the page
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		String query = "SELECT * FROM Member WHERE username = '" + username + "' AND password = '" + password + "';";
		ApplicationDAO dao = new ApplicationDAO();
		try{
			member = (LinkedList<Member>)dao.queryDB(query, Member.class);
			if (member.size() == 1 && username.equals(member.get(0).getUsername())){
				//user is found in database
				System.out.println("username found");
				HttpSession session = request.getSession();
				session.setAttribute("currentUser", member.get(0));
				request.getRequestDispatcher("ProcessMainPageServlet").forward(request, response);
			}
			else {
				//user not found in database
				System.out.println("username not found");
				request.setAttribute("invalidLogin", "Username or Password is invalid.");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}
		} catch(SQLException e){
			e.printStackTrace();
		} finally{
			dao.closeConnection();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
