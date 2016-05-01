package com.moviebay.pkg.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.moviebay.pkg.ApplicationDAO;
import com.moviebay.pkg.Auction;
import com.moviebay.pkg.Bid;
import com.moviebay.pkg.Member;

/**
 * Servlet implementation class ProcessBidServlet
 */
public class ProcessBidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessBidServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bid_str = request.getParameter("bid");
		String auctionId_str = request.getParameter("auctionId");
		String itemId_str = request.getParameter("itemId");
		
		Float bid;
		Integer auctionId;
		
		//setting attributes to pass along to LoadItemServlet
		request.setAttribute("auctionId", auctionId_str);
		request.setAttribute("itemId", itemId_str);
		
		auctionId = Integer.parseInt(auctionId_str);
		//attempt to parse bid by user.  If fails, do nothing w/ the bid and pass along to next servlet
		try{
			bid = Float.parseFloat(bid_str);
		} catch (NumberFormatException e){
			e.printStackTrace();
			request.setAttribute("badBid", "Please enter a valid bid amount.");
			request.getRequestDispatcher("LoadItemServlet").forward(request, response);
			return;
		}
		
		String auction_query = "SELECT * FROM Auction WHERE auction_id=" + auctionId + ";";
		
		ApplicationDAO dao = new ApplicationDAO();
		try{
			Date date = new Date();
			Timestamp now = new Timestamp(date.getTime());
			HttpSession session = request.getSession();
			String username = ((Member)session.getAttribute("currentUser")).getUsername();	//get current member
			Auction auction = dao.queryDB(auction_query, Auction.class).get(0);
			
			//Checking if Bid is a valid entry
			
			//if bid is within time limit
			if (now.before(auction.getEndDateTime())){
				//if bid amount is high enough
				if (bid >= (auction.getTopBid()+auction.getMinimumIncrement())){
					Bid bidobj = new Bid(null, bid, now, username, auctionId);
					dao.insert(bidobj, Bid.class);
					//replaced by trigger "topbid"
					/*String update_auction = "UPDATE Auction SET top_bid=" + bid_str + ", "
							+ "bidder='" + username + "' WHERE auction_id=" + auctionId_str + ";";
					dao.del_or_upd(update_auction);*/
					//set new attributes
					request.setAttribute("goodBid", "Bid of " + bid_str + " successful.");
				} else {
					//bid amount is too low
					request.setAttribute("lowBid", "Bid amount is too low.");
				}
			} 
			else {
				//bid expired
				request.setAttribute("expiredBid", "Bid has expired.");
			}
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			dao.closeConnection();
		}
	
		request.getRequestDispatcher("LoadItemServlet").forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
