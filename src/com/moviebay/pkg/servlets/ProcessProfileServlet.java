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
import com.moviebay.pkg.Member;

/**
 * Servlet implementation class ProcessProfileServlet
 */
public class ProcessProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String user = request.getParameter("user");
		String user_query = "SELECT * FROM Member WHERE username='" + user + "';";
		String bids_query = "SELECT *, MAX(bid_amount) FROM Bid b, Item i, Auction a WHERE b.bidder='" + user + "' AND "
				+ "b.auction_id=i.auction_id AND b.auction_id=a.auction_id GROUP BY i.auction_id;";
		String auctions_query = "SELECT * FROM Auction a, Item i WHERE a.seller='" + user + "' AND "
				+ "a.seller=i.seller AND a.auction_id=i.auction_id;";
		
		/*
		 * member is the current user's information.
		 * selling_items and selling_auctions is the list of item/auction pairs that the user has auctioned.
		 * buying_items and buying_items is the list of item/auction pairs that the user has bid on.s
		 */
		Member member;
		LinkedList<Item> buying_items;
		LinkedList<Item> selling_items;
		LinkedList<Auction> buying_auctions;
		LinkedList<Auction> selling_auctions;
		
		ApplicationDAO dao = new ApplicationDAO();
		try{
			member = dao.queryDB(user_query, Member.class).get(0);
			buying_items = dao.queryDB(bids_query, Item.class);
			buying_auctions = dao.queryDB(bids_query, Auction.class);
			selling_items = dao.queryDB(auctions_query, Item.class);
			selling_auctions = dao.queryDB(auctions_query, Auction.class);
			System.out.println(buying_items.size()+", " + buying_auctions.size());
			System.out.println(selling_items.size()+", " + selling_auctions.size());
			request.setAttribute("user", member);
			request.setAttribute("buyItems", buying_items);
			request.setAttribute("sellItems", selling_items);
			request.setAttribute("buyAuctions", buying_auctions);
			request.setAttribute("sellAuctions",selling_auctions);
			if (!member.getCusRepStatus() && !member.getAdminStatus())
				request.setAttribute("acctype", "Regular");
			else if (member.getCusRepStatus() && !member.getAdminStatus())
				request.setAttribute("acctype", "Customer Representative");
			else if (member.getCusRepStatus() && member.getAdminStatus())
				request.setAttribute("acctype", "Administrator");
		} catch(SQLException e){
			e.printStackTrace();
		} finally{
			dao.closeConnection();
		}
		request.getRequestDispatcher("/userprofile.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
