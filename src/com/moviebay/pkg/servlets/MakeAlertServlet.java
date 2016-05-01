package com.moviebay.pkg.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.moviebay.pkg.Alert;
import com.moviebay.pkg.ApplicationDAO;
import com.moviebay.pkg.Member;

/**
 * Servlet implementation class MakeAlertServlet
 */
public class MakeAlertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MakeAlertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String title = request.getParameter("title");
		String genre = request.getParameter("genre");
		String format = request.getParameter("format");
		if (format == null){
			request.setAttribute("noFormat", "Movie format field must be chosen.");
			request.getRequestDispatcher("ProcessAlertServlet").forward(request, response);
			return;
		}
		HttpSession session = request.getSession();
		String username = ((Member)session.getAttribute("currentUser")).getUsername();
		Alert alert = new Alert(null, title, genre, format, username);
		ApplicationDAO dao = new ApplicationDAO();
		try{
			dao.insert(alert, Alert.class);
			request.setAttribute("goodAlert", "Your alert has been saved.");
		} catch(SQLException e){
			e.printStackTrace();
		} finally{
			dao.closeConnection();
		}
		request.getRequestDispatcher("ProcessAlertServlet").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
