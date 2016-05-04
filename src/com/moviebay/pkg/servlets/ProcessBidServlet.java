package com.moviebay.pkg.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.moviebay.pkg.ApplicationDAO;
import com.moviebay.pkg.Auction;
import com.moviebay.pkg.Bid;
import com.moviebay.pkg.Member;
import com.moviebay.pkg.UpperLimit;

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
	
		Date date = new Date();
		Timestamp now = new Timestamp(date.getTime());
		HttpSession session = request.getSession();
		String username = ((Member)session.getAttribute("currentUser")).getUsername();	//get current member
		String auction_query = "SELECT * FROM Auction WHERE auction_id=" + auctionId + ";";
		String autobid_query = "SELECT * FROM UpperLimit WHERE auction_id=" + auctionId + " AND bidder<>'" + username + "';";
		String bidder_query = "SELECT * FROM UpperLimit WHERE auction_id=" + auctionId + " AND bidder='" + username + "';";
		
		boolean goodBid = false;
		ApplicationDAO dao = new ApplicationDAO();
		try{
			Auction auction = dao.queryDB(auction_query, Auction.class).get(0);
			
			//Checking if Bid is a valid entry
			
			//if bid is within time limit
			if (now.before(auction.getEndDateTime())){
				//if bid amount is good
				if (bid >= (auction.getTopBid()+auction.getMinimumIncrement())){
					goodBid = true;
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
		//do auto-bidding amongst competitors
		if (goodBid){
			ApplicationDAO dao2 = new ApplicationDAO();
			try {
				Auction auction = dao2.queryDB(auction_query, Auction.class).get(0);
				//find list of all autobids that aren't the original bidder's
				LinkedList<UpperLimit> upperLimits = dao2.queryDB(autobid_query, UpperLimit.class);
				//find autobid that is the original bidders, if he set one
				LinkedList<UpperLimit> bidderupper = dao2.queryDB(bidder_query, UpperLimit.class);
				if (bidderupper != null)
					upperLimits.add(bidderupper.get(0));
				int ind = 0;
				float min_price = auction.getMinimumIncrement() + auction.getTopBid();
				//autobidders take turns bidding until one "winner" remains
				while (upperLimits.size() > 0){
					if (ind == upperLimits.size())
						ind = 0;
					if (upperLimits.get(ind).getUpperLimit() >= min_price){
						Bid autobid = new Bid(null, min_price, now, upperLimits.get(ind).getBidder(), auctionId);
						dao2.insert(autobid, Bid.class);
					}
					else {
						upperLimits.remove(ind);
						--ind;
					}
					if (upperLimits.size() == 1){
						break;
					}
					min_price += auction.getMinimumIncrement();
					++ind;
				}
			} catch (SQLException e){
				e.printStackTrace();
			} finally {
				dao2.closeConnection();
			}
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
