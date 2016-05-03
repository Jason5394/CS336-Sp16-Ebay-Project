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
import com.oracle.jrockit.jfr.RequestableEvent;


/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LinkedList<Member> members;
		String user = request.getParameter("username");
		String firstname = request.getParameter("firstname");
		String lastname  = request.getParameter("lastname");
		String password  = request.getParameter("password");
		
		//Tests if user has filled in ALL forms.  Will redirect to register page with error message if forms are not 
		//all filled.
		boolean exit = false;
		if (user.isEmpty()){
			request.setAttribute("noUsername", "Username field must be filled.");
			exit = true;
		}
		if (firstname.isEmpty()){
			request.setAttribute("noFirstName", "First name field must be filled.");
			exit = true;
		}
		if (lastname.isEmpty()){
			request.setAttribute("noLastName", "Last name field must be filled.");
			exit = true;
		}
		if (password.isEmpty()){
			request.setAttribute("noPassword", "Password field must be filled.");
			exit = true;
		}
		if (exit){
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}
		
		HttpSession session = request.getSession();
		Member currentUser = (Member)session.getAttribute("currentUser");
		String query = "SELECT * FROM Member WHERE username = '" + user + "';";
		ApplicationDAO dao = new ApplicationDAO();
		//Connection connectdb = dao.getConnection();
		try{
			members = dao.queryDB(query, Member.class);
			//checking if username exists in database
			if (members.size() == 0){
				//username is okay!
				
				//if a regular user is creating his own account
				if (currentUser == null){
					dao.insert(new Member(user, firstname, lastname, password, false, false), Member.class);
					request.getRequestDispatcher("/index.jsp").forward(request, response);
				}
				//if an admin is creating a customer rep account
				else if (currentUser.getAdminStatus() == true){
					dao.insert(new Member(user, firstname, lastname, password, true, false), Member.class);
					request.getRequestDispatcher("ProcessMainPageServlet").forward(request, response);
				}
				
			}
			else{
				//username is already taken
				request.setAttribute("usernameTaken", "Username is already taken.");
				request.getRequestDispatcher("/register.jsp").forward(request, response);
			}
		} catch (SQLException e){
			e.printStackTrace();
		} finally{
			dao.closeConnection();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
