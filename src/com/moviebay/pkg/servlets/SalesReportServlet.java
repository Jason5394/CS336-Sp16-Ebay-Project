package com.moviebay.pkg.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moviebay.pkg.ApplicationDAO;
import com.moviebay.pkg.Auction;
import com.moviebay.pkg.Member;

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
		Float total_earnings = 0f;
		Float[] genre_earnings = new Float[11];
		Float[] format_earnings = new Float[3];
		
		/* QUERY IMPLEMENTATIONS */
		String totalQuery = "SELECT SUM(top_bid) FROM Auction WHERE end_datetime < now() AND winner IS NOT NULL;";
		
		// Array of Genres
		String[] genreArray;
		genreArray = new String[] {"action", "adventure", "animation", "comedy", "documentary", "drama", "horror", "mystery", "romance", "scifi", "thriller"};
		
		// Array of Queries per Genre
		String[] genreQueryArray = new String[11];
		for (int i=0; i<genreArray.length; i++)
		{
			genreQueryArray[i] = "SELECT SUM(a.top_bid) FROM Auction a, Item i WHERE a.end_datetime < now() AND a.winner IS NOT NULL AND i.genre = '" + genreArray[i] 
					+ "' AND i.auction_id=a.auction_id;";
		}
				
		/*	FORMAT REPORT */
		
		// Format Array
		String[] formatArray = new String[] {"vhs","dvd","bluray"};
		
		// Format Query Array
		String[] formatQueryArray = new String[3];
		
		for (int i=0; i<formatArray.length; i++)
		{
			formatQueryArray[i] = "SELECT SUM(a.top_bid) FROM Auction a, Item i WHERE a.end_datetime < now() AND a.winner IS NOT NULL AND i.movie_format = '" + formatArray[i] + 
					"' AND i.auction_id=a.auction_id;";
		}
		
		/*	END-USER REPORT 
		 *  For simplicity we are doing the top 5 sellers
		 */
		
		// End User Array (BUYERS)
		//String userArray = new String();
		
		// Figure out instances of when buyer appears on bid table
		LinkedList<Integer> buyerAppears = new LinkedList<Integer>();
		LinkedList<Member> winners = null;
		LinkedList<Auction> numberOfAuctions = null;
		
		// Show entire Member table
		String winnerQuery = "SELECT * FROM Member M, Auction A WHERE M.username = A.winner AND a.end_datetime < NOW() GROUP BY username;";
		String auctionQuery = "SELECT * FROM Auction WHERE end_datetime < NOW();";
		
		// DAO object
		ApplicationDAO dao = new ApplicationDAO();
		try {
			
			// Total Earnings
			total_earnings = dao.floatDB(totalQuery);
			
			// Genre Earnings
			for (int i=0; i<genreArray.length; i++)
			{
				genre_earnings[i] = dao.floatDB(genreQueryArray[i]);
			}
			
			// Format Earnings
			for (int i=0; i<formatArray.length; i++)
			{
				format_earnings[i] = dao.floatDB(formatQueryArray[i]);
			}
			
			// Buyer Earnings
			winners = dao.queryDB(winnerQuery, Member.class);
			numberOfAuctions = dao.queryDB(auctionQuery, Auction.class);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dao.closeConnection();
		}
		
		/* Buyer Earnings (BEST BUYERS)*/ 
		for (int i = 0; i < winners.size(); ++i){
			buyerAppears.add(0);
		}
		for(int i=0; i<numberOfAuctions.size(); i++)
		{
			for (int j=0; j<winners.size(); j++)
			{
				if (numberOfAuctions.get(i).getWinner().equals(winners.get(j).getUsername()));
				{
					buyerAppears.set(j, buyerAppears.get(j)+1);
				}
			}
		}
		
		request.setAttribute("totalearnings", total_earnings);
		request.setAttribute("genreearnings", genre_earnings);
		request.setAttribute("formatearnings", format_earnings);
		request.setAttribute("bestbuyers", winners);
		request.setAttribute("amtbuyers", buyerAppears);
		request.getRequestDispatcher("/makesalesreport.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
