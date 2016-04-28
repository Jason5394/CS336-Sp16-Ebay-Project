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
		
		String goodBid = (String)request.getAttribute("goodBid");
		String lowBid = (String)request.getAttribute("lowBid");
		String expiredBid = (String)request.getAttribute("expiredBid");
		String badBid = (String)request.getAttribute("badBid");
		
		request.setAttribute("goodBid", goodBid);
		request.setAttribute("lowBid", lowBid);
		request.setAttribute("expiredBid", expiredBid);
		request.setAttribute("badBid", badBid);
		
		LinkedList<Item> item;
		LinkedList<Auction> auction;
		LinkedList<Bid> bids; 
		//Create queries for item and auction
		String itemQuery = "SELECT * FROM Item WHERE item_id='" + itemId + "';";
		String auctionQuery = "SELECT * FROM Auction WHERE auction_id='" + auctionId + "';";
		String bidsQuery = "SELECT * FROM Bid WHERE auction_id='" + auctionId + "' ORDER BY bid_amount DESC;";
		
		ApplicationDAO dao = new ApplicationDAO();
		try{
			item = dao.queryDB(itemQuery, Item.class);
			auction = dao.queryDB(auctionQuery, Auction.class);
			bids = dao.queryDB(bidsQuery, Bid.class);
			
			request.setAttribute("item", item.get(0));
			request.setAttribute("auction", auction.get(0));
			request.setAttribute("bids", bids);
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
