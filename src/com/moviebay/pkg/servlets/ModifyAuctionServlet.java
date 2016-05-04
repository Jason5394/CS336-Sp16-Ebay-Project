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
 * Servlet implementation class ModifyAuctionServlet
 */
public class ModifyAuctionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyAuctionServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get parameters from request
		String title = request.getParameter("title");
		String duration_s = request.getParameter("duration");
		String genre = request.getParameter("genre");
		String format = request.getParameter("format");
		String description = request.getParameter("description");
		String auctionLength = request.getParameter("auctionLength");
		String minprice_s = request.getParameter("minprice");
		String hiddenmin_s = request.getParameter("hiddenmin");
		String auctionId = request.getParameter("auctionId");
		String itemId = request.getParameter("itemId");
		String update_item = "";
		String update_auction = "";
	
		boolean exit = false;
		float hiddenmin;
		
		if (!duration_s.isEmpty()){
			try{
				if (Integer.parseInt(duration_s) < 0)
					exit = true;
			} catch (NumberFormatException e){
				request.setAttribute("badDuration", "Movie duration must be in minutes.");
				exit = true;
				e.printStackTrace();
			}
		}
		
		if(!minprice_s.isEmpty()){
			try{
				if (Float.parseFloat(minprice_s) < 0)
					exit = true;
			} catch (NumberFormatException e){
				request.setAttribute("badMinimumIncr", "Minimum increment must have a valid price amount.");
				exit = true;
				e.printStackTrace();
			}
		}
		
		if (!hiddenmin_s.isEmpty()){
			try{
				if ((hiddenmin=Float.parseFloat(hiddenmin_s)) < 0)
					exit = true;
			} catch (NumberFormatException e){
				request.setAttribute("badHiddenMin", "Hidden minimum must have a valid price amount.");
				exit = true;
				e.printStackTrace();
			}
		}
		
		if (exit){
			request.getRequestDispatcher("LoadItemServlet").forward(request, response);
			return;
		}
		
		//if user modified just one of the columns in Item, create the string
		if (!title.isEmpty() || !duration_s.isEmpty() || genre != null || !description.isEmpty() || format != null){
			LinkedList<String> strings = new LinkedList<String>(); 
			update_item += "UPDATE Item SET ";
			if (!title.isEmpty())
				strings.add(" movie_title='" + title + "' ");
			if (!duration_s.isEmpty())
				strings.add(" movie_length='" + duration_s + "' ");
			if (genre != null)
				strings.add(" genre='" + genre + "' ");
			if (!description.isEmpty())
				strings.add(" description='" + description + "' ");
			if (format != null)
				strings.add(" movie_format='" + format + "' ");
			for (int i = 0; i < strings.size()-1; ++i) {
				update_item += strings.get(i) + ",";
			}
			update_item += strings.get(strings.size()-1);
			update_item += "WHERE auction_id=" + auctionId + " AND item_id=" + itemId + ";";
		}
		boolean hiddenminchanged = false;
		//if user modified just one of the columns in Auction, create the string
		if (auctionLength != null || !minprice_s.isEmpty() || !hiddenmin_s.isEmpty()){
			LinkedList<String> strings = new LinkedList<String>(); 
			update_auction += "UPDATE Auction SET ";
			if (auctionLength != null)
				strings.add(" end_datetime= DATE_ADD(end_datetime, INTERVAL " + auctionLength + " DAY) ");
			if (!minprice_s.isEmpty())
				strings.add(" minimum_increment_price='" + minprice_s + "' ");
			if (!hiddenmin_s.isEmpty()){
				hiddenminchanged = true;
				strings.add(" hidden_minimum_price='" + hiddenmin_s + "' ");
			}
			for (int i = 0; i < strings.size()-1; ++i) {
				update_auction += strings.get(i) + ",";
			}
			update_auction += strings.get(strings.size()-1);
			update_auction += "WHERE auction_id=" + auctionId + ";";
			System.out.println(update_auction);
		}
		
		ApplicationDAO dao = new ApplicationDAO();
		try{
			if (!update_item.isEmpty()){
				dao.del_or_upd(update_item);
				request.setAttribute("updatedItem", "Item information updated.");
			}
			if (!update_auction.isEmpty()){
				dao.del_or_upd(update_auction);
				request.setAttribute("updatedAuction", "Auction information updated.");
				if (hiddenminchanged){
					String hiddenminquery = "SELECT * FROM Auction WHERE auction_id=" + auctionId + ";";
					Auction auction = dao.queryDB(hiddenminquery, Auction.class).get(0);
					hiddenmin = Float.parseFloat(hiddenmin_s);
					if (hiddenmin <= auction.getTopBid()){
						String find_bidder = "SELECT * FROM Bid WHERE auction_id=" + auctionId + " AND "
								+ "bid_amount=(SELECT MAX(bid_amount) FROM Bid WHERE auction_id=" + auctionId + ");";
						Bid bid = dao.queryDB(find_bidder, Bid.class).get(0);
						String change_auction = "UPDATE Auction SET top_bid=" + bid.getBidAmount() + ", " +
								"winner='" + bid.getBidder() + "';";
						dao.del_or_upd(change_auction);
					}
					else {
						String change_auction = "UPDATE Auction SET winner=NULL";
						dao.del_or_upd(change_auction);
					}
				}
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
