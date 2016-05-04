package com.moviebay.pkg.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.moviebay.pkg.ApplicationDAO;
import com.moviebay.pkg.Member;

/**
 * Servlet implementation class DeleteAccountServlet
 */
@WebServlet("/deleteAccount")
public class DeleteAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteAccountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = ((Member)session.getAttribute("currentUser")).getUsername();
		session.removeAttribute("currentUser");
		session.invalidate();
		String delete_Query = "DELETE FROM Member WHERE username='" + username + "';";
		ApplicationDAO dao = new ApplicationDAO();
		try{
			dao.del_or_upd(delete_Query);
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			dao.closeConnection();
		}
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
