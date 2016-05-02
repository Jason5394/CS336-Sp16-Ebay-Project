package com.moviebay.pkg.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.moviebay.pkg.ApplicationDAO;
import com.moviebay.pkg.Auction;
import com.moviebay.pkg.Member;

/**
 * Servlet implementation class MakeUpperLimServlet
 */
public class MakeUpperLimServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MakeUpperLimServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String upperLimit = request.getParameter("upperLimit");
		String auctionId = request.getParameter("auctionId");	
		String itemId = request.getParameter("itemId");
		
		request.setAttribute("auctionId", auctionId);
		request.setAttribute("itemId", itemId);

		HttpSession session = request.getSession();
		String username = ((Member)session.getAttribute("currentUser")).getUsername();
		Float upperLimf;
		
		//attempt to parse upper limit by user.  If fails, do nothing w/ the limit and pass along to next servlet
		try{
			upperLimf = Float.parseFloat(upperLimit);
		} catch (NumberFormatException e){
			e.printStackTrace();
			request.setAttribute("badLim", "Please enter a valid upper limit.");
			request.getRequestDispatcher("LoadItemServlet").forward(request, response);
			return;
		}
	
		Auction auction = null;
		String auction_query = "SELECT * FROM Auction WHERE auction_id='" + auctionId + "';";
		ApplicationDAO dao = new ApplicationDAO();
		try{
			auction = dao.queryDB(auction_query, Auction.class).get(0);
		} catch(SQLException e){
			e.printStackTrace();
		} finally{
			dao.closeConnection();
		}
		
		if (upperLimf < auction.getTopBid() + auction.getMinimumIncrement()){
			request.setAttribute("lowLim", "Your upper limit must be at least current bid + minimum increment.");
			request.getRequestDispatcher("LoadItemServlet").forward(request, response);
			return;
		}
		
		String upper_update = "REPLACE INTO UpperLimit (auction_id, bidder, upper_limit) "
				+ "VALUES(" + auctionId + ", '" + username + "', " + upperLimf + ");";
		//String upper_update = "UPDATE UpperLimit SET "/
		ApplicationDAO dao2 = new ApplicationDAO();
		try{
			dao2.del_or_upd(upper_update);
			request.setAttribute("goodLim", "Upper limit successfully set.");
		} catch(SQLException e){
			e.printStackTrace();
		} finally{
			dao2.closeConnection();
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
