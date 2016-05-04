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
import com.moviebay.pkg.Bid;
import com.moviebay.pkg.Item;

/**
 * Servlet implementation class LoadItemServlet
 */
public class LoadItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadItemServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get parameters from hidden field
		String itemId = request.getParameter("itemId");
		String auctionId = request.getParameter("auctionId");
		
		Item item;
		Auction auction;
		LinkedList<Bid> bids; 
		LinkedList<Item> similarItems;
		LinkedList<Auction> similarAucts; 
		//Create queries for item and auction
		String itemQuery = "SELECT * FROM Item WHERE item_id='" + itemId + "';";
		String auctionQuery = "SELECT * FROM Auction WHERE auction_id='" + auctionId + "';";
		String bidsQuery = "SELECT * FROM Bid WHERE auction_id='" + auctionId + "' ORDER BY bid_amount DESC;";
		String simMovieQuery;
		
		ApplicationDAO dao = new ApplicationDAO();
		try{
			item = dao.queryDB(itemQuery, Item.class).get(0);
			auction = dao.queryDB(auctionQuery, Auction.class).get(0);
			bids = dao.queryDB(bidsQuery, Bid.class);
			
			if (item != null && auction != null){
				request.setAttribute("item", item);
				request.setAttribute("auction", auction);
				request.setAttribute("bids", bids);
				simMovieQuery = "SELECT * FROM Item i, Auction a WHERE i.auction_id=a.auction_id AND i.item_id<>" + itemId + " AND "
						+ "a.auction_id<>" + auctionId + " AND a.end_datetime > NOW() AND ("
						+ "i.movie_title='" + item.getTitle() + "' OR i.genre='" + item.getGenre() + "');";
				similarItems = dao.queryDB(simMovieQuery, Item.class);
				similarAucts = dao.queryDB(simMovieQuery, Auction.class);
				request.setAttribute("simItems", similarItems);
				request.setAttribute("simAucts", similarAucts);
			}
			
		} catch(SQLException e){
			e.printStackTrace();
		} finally{
			dao.closeConnection();
		}
		request.getRequestDispatcher("/auctionpage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
