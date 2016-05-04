package com.moviebay.pkg.servlets;

import java.io.IOException;
import java.sql.SQLException;

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
		Float total_earnings;
		Float[] genre_earnings = new Float[11];
		Float[] format_earnings = new Float[3];
		
		/*** QUERY IMPLEMENTATIONS ***/
		String totalQuery = "SELECT SUM(top_bid) FROM Auction, WHERE end_datetime < now() AND winner IS NOT NULL";
		
		// Array of Genres
		String[] genreArray;
		genreArray = new String[] {"action", "adventure", "animation", "comedy", "documentary", "drama", "horror", "mystery", "romance", "sci-fi", "thriller"};
		
		// Array of Queries per Genre
		String[] genreQueryArray = new String[11];
		for (int i=0; i<=genreArray.length; i++)
		{
			genreQueryArray[i] = "SELECT SUM(top_bid) FROM Auction, WHERE end_datetime < now() AND winner IS NOT NULL AND genre = " + genreArray[i] + "";
		}
				
		/*	FORMAT REPORT */
		
		// Format Array
		String[] formatArray = new String[] {"vhs","dvd","blu-ray"};
		
		// Query Array
		String[] formatQueryArray = new String[3];
		
		for (int i=0; i<=formatArray.length; i++)
		{
			formatQueryArray[i] = "SELECT SUM(top_bid) FROM Auction, WHERE end_datetime < now() AND winner IS NOT NULL AND movie_format = " + formatArray[i] + "";
		}
		
		/*	END-USER REPORT 
		 *  For simplicity we are doing the top 5 sellers
		 */
		
		// End User Array
		// Query the 
		String[] userArray = new String[5];
		
		for(int i=0; i<=4; i++)
		{
		}
		
		// DAO object
		ApplicationDAO dao = new ApplicationDAO();
		try {
			
			// Total Earnings
			total_earnings = dao.floatDB(totalQuery);
			
			// Genre Earnings
			for (int i=0; i<=genreArray.length; i++)
			{
				genre_earnings[i] = dao.floatDB(genreQueryArray[i]);
			}
			
			// Format Earnings
			for (int i=0; i<=formatArray.length; i++)
			{
				format_earnings[i] = dao.floatDB(formatQueryArray[i]);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
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
