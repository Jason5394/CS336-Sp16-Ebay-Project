package com.moviebay.pkg.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moviebay.pkg.ApplicationDAO;

/**
 * Servlet implementation class SalesReportServlet
 */
public class SalesReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SalesReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Double total_earnings;
		Double[] genre_earnings = new Double[11];
		// Query Implementations
		String query = "SELECT SUM(top_bid) FROM Auction, WHERE end_datetime < now() AND winner IS NOT NULL";
		String[] genreArray;
		genreArray = new String[] {"action", "adventure", "animation", "comedy", "documentary", "drama", "horror", "mystery", "romance", "sci-fi", "thriller"};
		String[] queryArray = new String[11];
		for (int i=0; i<=genreArray.length; i++)
		{
			
			String temp_query = "SELECT SUM(top_bid) FROM Auction, WHERE end_datetime < now() AND winner IS NOT NULL AND genre = " + genreArray[i] + "";
			queryArray[i] = temp_query;
		}
		
		for (int i=0; i<=11; i++)
		{
			genre_earnings[i] = 
		}
		
		// DAO object
		ApplicationDAO dao = new ApplicationDAO();
		try {
			
		} catch {
			
		} finally {
			dao.closeConnection();
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
