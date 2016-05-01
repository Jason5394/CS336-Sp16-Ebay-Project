package com.moviebay.pkg.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.moviebay.pkg.Alert;
import com.moviebay.pkg.ApplicationDAO;
import com.moviebay.pkg.Member;

/**
 * Servlet implementation class ProcessAlertServlet
 */
public class ProcessAlertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessAlertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = ((Member)session.getAttribute("currentUser")).getUsername();
		String alert_query = "SELECT * FROM Alert WHERE owner='" + username + "';";
		LinkedList<Alert> alerts; 
		ApplicationDAO dao = new ApplicationDAO();
		try {
			alerts = dao.queryDB(alert_query, Alert.class);
			request.setAttribute("alerts", alerts);
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			dao.closeConnection();
		}
		request.getRequestDispatcher("/alertspage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
