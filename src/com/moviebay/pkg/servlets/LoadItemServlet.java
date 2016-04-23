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
		
		LinkedList<Item> item;
		LinkedList<Auction> auction;
		
		//Create queries for item and auction
		String itemQuery = "SELECT * FROM Item WHERE item_id='" + itemId + "';";
		String auctionQuery = "SELECT * FROM Auction WHERE auction_id='" + auctionId + "';";
		
		ApplicationDAO dao = new ApplicationDAO();
		try{
			item = dao.queryDB(itemQuery, Item.class);
			auction = dao.queryDB(auctionQuery, Auction.class);
			request.setAttribute("item", item.get(0));
			request.setAttribute("auction", auction.get(0));
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
