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

/**
 * Servlet implementation class DeleteBidServlet
 */
public class DeleteBidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteBidServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bidId = request.getParameter("bidId");
		String auctionId = request.getParameter("auctionId");
		String remove_bid = "DELETE FROM Bid WHERE bid_id=" + bidId + ";";
		ApplicationDAO dao = new ApplicationDAO();
		try {
			dao.del_or_upd(remove_bid);
			String topbidQuery = "SELECT * FROM Bid WHERE auction_id=" + auctionId + " AND bid_amount="
					+ "(SELECT MAX(bid_amount) FROM Bid WHERE auction_id=" + auctionId + ");";
			String auctionQuery = "SELECT * FROM Auction WHERE auction_id=" + auctionId + ";";
			LinkedList<Bid> topBids = dao.queryDB(topbidQuery, Bid.class);
			LinkedList<Auction> auctions = dao.queryDB(auctionQuery, Auction.class);
			if (topBids.size() > 0 && auctions.size() > 0){
				Bid topBid = topBids.get(0);
				Auction auction = auctions.get(0);
				String updateAuction = "UPDATE Auction SET bidder='" + topBid.getBidder() + "', top_bid=" + topBid.getBidAmount()
						+ " WHERE auction_id = " + auctionId + ";";
				if (topBid.getBidAmount() >= auction.getHiddenMinimum()){
					String updateWinner = "UPDATE Auction SET winner='" + topBid.getBidder() + "' WHERE auction_id=" + auctionId + ";";
					dao.del_or_upd(updateWinner);
				}
				else {
					String updateWinner = "UPDATE Auction SET winner=NULL WHERE auction_id=" + auctionId + ";";
					dao.del_or_upd(updateWinner);
				}
				dao.del_or_upd(updateAuction);
			}
			else {
				String nullAll = "UPDATE Auction SET bidder=NULL, winner=NULL, top_bid=0.0 WHERE auction_id=" + auctionId + ";";
				dao.del_or_upd(nullAll);
			}
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			dao.closeConnection();
		}
		request.getRequestDispatcher("LoadItemServlet").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
