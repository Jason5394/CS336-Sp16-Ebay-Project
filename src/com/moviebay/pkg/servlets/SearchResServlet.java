package com.moviebay.pkg.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.moviebay.pkg.*;

/**
 * Servlet implementation class SearchResServlet
 */
public class SearchResServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchResServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Got to SearchResServlet");
		LinkedList<Item> itemResults;
		LinkedList<Auction> auctionResults; 
		String genre;
		String duration;
		String format;
		String title;
		
		//retrieve user's search parameters
		title = request.getParameter("title");
		/*if (title.isEmpty()){	//User must have entered at least a title, otherwise refresh page
			request.getRequestDispatcher("ProcessMainPageServlet").forward(request, response);
			return;
		}*/
		genre = request.getParameter("genre");
		duration = request.getParameter("duration");
		format = request.getParameter("format");
		
		//formulate the string to query DB with.
		String query_string = "SELECT * FROM Item I, Auction A WHERE I.auction_id=A.auction_id AND "
				+ "A.end_datetime>NOW() AND "
				+ "(I.movie_title LIKE '%" + title + "%' OR I.description LIKE '%" + title + "%')";
		if (genre != null){
			String genre_query = " AND I.genre='"+ genre + "'";
			query_string = query_string.concat(genre_query);
		}
		if (format != null){
			String format_query = " AND I.movie_format='"+ format + "'";
			query_string = query_string.concat(format_query);
		}
		if (duration != null){
			String dur_query = "";
			if (duration.equals("short"))
				dur_query = " AND I.movie_length<30";
			else if (duration.equals("medium"))
				dur_query = " AND I.movie_length>=30 AND I.movie_length<=90";
			else if (duration.equals("long"))
				dur_query = " AND I.movie_length>90";
			query_string = query_string.concat(dur_query);
		}
		query_string = query_string.concat(";");
		System.out.println("Resulting query:");
		System.out.println(query_string);
		
		//Connecting and querying database for results
		ApplicationDAO dao = new ApplicationDAO();
		try{
			itemResults = dao.queryDB(query_string, Item.class);
			auctionResults = dao.queryDB(query_string, Auction.class);
			request.setAttribute("itemResults", itemResults);
			request.setAttribute("auctionResults", auctionResults);
			System.out.println("Number of search results: " + itemResults.size());
		} catch (SQLException e){
			e.printStackTrace();
		} finally{
			dao.closeConnection();
		}
		request.getRequestDispatcher("/searchresults.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
