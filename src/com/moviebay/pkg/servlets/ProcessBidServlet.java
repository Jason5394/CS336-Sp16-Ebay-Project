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
import com.moviebay.pkg.Item;
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
		Integer itemId;
		
		auctionId = Integer.parseInt(auctionId_str);
		itemId = Integer.parseInt(itemId_str);
		
		String auction_query = "SELECT * FROM Auction WHERE auction_id=" + auctionId + ";";
		String item_query = "SELECT * FROM Item WHERE item_id=" + itemId + ";";
		
		Auction auction;
		Item item;
		
		//get auction and item objects to be able to reload page
		ApplicationDAO dao2 = new ApplicationDAO();
		try{
			auction = dao2.queryDB(auction_query, Auction.class).get(0);
			item = dao2.queryDB(item_query, Item.class).get(0);
			//Parse bid into a float to see if it's a valid number
			if (!bid_str.isEmpty()){
				try{
					bid = Float.parseFloat(bid_str);
				} catch (NumberFormatException e){
					e.printStackTrace();
					dao2.closeConnection();
					request.setAttribute("item", item);
					request.setAttribute("auction", auction);
					request.setAttribute("badBid", "Please enter a valid bid amount.");
					request.getRequestDispatcher("/auctionpage.jsp").forward(request, response);
					return;
				}
			}
			else {
				dao2.closeConnection();
				request.setAttribute("item", item);
				request.setAttribute("auction", auction);
				request.getRequestDispatcher("/auctionpage.jsp").forward(request, response);
				return;
			}
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			dao2.closeConnection();
		}
		
		ApplicationDAO dao = new ApplicationDAO();
		try{
			Date date = new Date();
			Timestamp now = new Timestamp(date.getTime());
			HttpSession session = request.getSession();
			String username = ((Member)session.getAttribute("currentUser")).getUsername();	//get current member
			auction = dao.queryDB(auction_query, Auction.class).get(0);
			item = dao.queryDB(item_query, Item.class).get(0);
			bid = Float.parseFloat(bid_str);
			//Successful bid (bid is at least minimum incr bigger AND it's done before the auction end date)
			if (now.before(auction.getEndDateTime())){
				//bid is within time limit
				if (bid >= (auction.getTopBid()+auction.getMinimumIncrement())){
					//bid amount is high enough
					Bid bidobj = new Bid(null, bid, now, username, auctionId);
					dao.insert(bidobj, Bid.class);
					String update_auction = "UPDATE Auction SET top_bid=" + bid_str + ", "
							+ "bidder='" + username + "' WHERE auction_id=" + auctionId_str + ";";
					dao.del_or_upd(update_auction);
					//set new attributes
					request.setAttribute("goodBid", "Bid of " + bid_str + " successful.");
				} else {
					//bid amount is too low
					request.setAttribute("lowBid", "Bid amount is too low.");
				}
			} 
			else {
				//bid expired
				request.setAttribute("bidExpired", "Bid has expired.");
			}
			request.setAttribute("item", item);
			request.setAttribute("auction", auction);
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			dao.closeConnection();
		}
		
		request.getRequestDispatcher("/auctionpage.jsp").forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
