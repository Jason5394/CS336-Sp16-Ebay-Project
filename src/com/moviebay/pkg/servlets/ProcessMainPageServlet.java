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
import com.moviebay.pkg.Auction;
import com.moviebay.pkg.Bid;
import com.moviebay.pkg.Item;
import com.moviebay.pkg.Member;

/**
 * Servlet implementation class ProcessMainPageServlet
 */
public class ProcessMainPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessMainPageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		Member user = (Member)session.getAttribute("currentUser");
		String username = user.getUsername();
		
		LinkedList<Item> currItems;
		LinkedList<Auction> currAuctions;
		LinkedList<Bid> currBids; 
		LinkedList<Item> pastItems;
		LinkedList<Auction> pastAuctions;
		LinkedList<Bid> pastBids;
		LinkedList<Item> currBidItems;
		LinkedList<Auction> currBidAucts;
		LinkedList<Item> pastBidItems;
		LinkedList<Auction> pastBidAucts; 
		
		//find items that user is still auctioning
		String current_auction = "SELECT * FROM Auction a, Item i WHERE i.auction_id=a.auction_id AND i.seller=a.seller AND "
				+ "i.seller='" + username + "' AND a.end_datetime > NOW();";
		//find items that user auctioned but have completed
		String completed_auction = "SELECT * FROM Auction a, Item i WHERE i.auction_id=a.auction_id AND i.seller=a.seller AND "
				+ "i.seller='" + username + "' AND a.end_datetime <= NOW();";
		//find items that user is still bidding on
		String current_bid = "SELECT * FROM Bid b, Auction a, Item i WHERE b.bid_amount=(SELECT MAX(bid_amount) FROM Bid b2 WHERE"
				+ " b2.auction_id=b.auction_id AND b2.bidder='" + username + "') AND b.auction_id=i.auction_id AND"
				+ " i.auction_id=a.auction_id AND a.end_datetime>NOW();";
		//find items that user bid on but have completed
		String completed_bid = "SELECT * FROM Bid b, Auction a, Item i WHERE b.bid_amount=(SELECT MAX(bid_amount) FROM Bid b2 WHERE"
				+ " b2.auction_id=b.auction_id AND b2.bidder='" + username + "') AND b.auction_id=i.auction_id AND"
				+ " i.auction_id=a.auction_id AND a.end_datetime<=NOW();";
		
		ApplicationDAO dao = new ApplicationDAO();
		try{
			//query items
			currItems = dao.queryDB(current_auction, Item.class);
			currAuctions = dao.queryDB(current_auction, Auction.class);
			currBids = dao.queryDB(current_bid, Bid.class);
			pastItems = dao.queryDB(completed_auction, Item.class);
			pastAuctions = dao.queryDB(completed_auction, Auction.class);
			pastBids = dao.queryDB(completed_bid, Bid.class);
			currBidItems = dao.queryDB(current_bid, Item.class);
			currBidAucts = dao.queryDB(current_bid, Auction.class);
			pastBidItems = dao.queryDB(completed_bid, Item.class);
			pastBidAucts = dao.queryDB(completed_bid, Auction.class);
			
			//set attributes
			request.setAttribute("currItems", currItems);
			request.setAttribute("currAuctions", currAuctions);
			request.setAttribute("currBids", currBids);
			request.setAttribute("pastItems", pastItems);
			request.setAttribute("pastAuctions", pastAuctions);
			request.setAttribute("pastBids", pastBids);
			request.setAttribute("currBidItems", currBidItems);
			request.setAttribute("currBidAucts", currBidAucts);
			request.setAttribute("pastBidItems", pastBidItems);
			request.setAttribute("pastBidAucts", pastBidAucts);
			
			//debugging
			System.out.println(currItems.size() + " " + currAuctions.size());
			System.out.println(pastItems.size() + " " + pastAuctions.size());
			System.out.println(currBids.size() + " " + currBids.size() + " " + currBidItems.size() + 
					" " + currBidAucts.size());
			System.out.println(pastBids.size() + " " + pastBids.size() + " " + pastBidItems.size() + 
					" " + pastBidAucts.size());
		} catch (SQLException e){
			e.printStackTrace();
		} finally{
			dao.closeConnection();
		}
		request.getRequestDispatcher("/mainpage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
