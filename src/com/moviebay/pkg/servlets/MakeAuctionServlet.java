package com.moviebay.pkg.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.moviebay.pkg.ApplicationDAO;
import com.moviebay.pkg.Auction;
import com.moviebay.pkg.Item;
import com.moviebay.pkg.Member;

/**
 * Servlet implementation class MakeAuctionServlet
 */
public class MakeAuctionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final long DAY_IN_MS = 1000*60*60*24;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MakeAuctionServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Item item;
		Auction auction;
		boolean exit = false;		//bool to check if user has inputted an error in the html form
		
		//Retrieve item parameters
		String title = request.getParameter("title");
		String genre = request.getParameter("genre");
		String description = request.getParameter("description");
		String format = request.getParameter("format");
		String duration_s = request.getParameter("duration");
		Integer duration = null;
		if (!duration_s.isEmpty()){
			try{
				duration = Integer.parseInt(duration_s);
			} catch (NumberFormatException e){
				request.setAttribute("badDuration", "Movie duration must be in minutes.");
				exit = true;
				e.printStackTrace();
			}
		}
		//Retrieve auction parameters
		String auctionLength_s = request.getParameter("auctionLength");
		String minimumIncrement_s = request.getParameter("minprice");
		String hiddenMinimum_s = request.getParameter("hiddenmin");
		Integer auctionLength = null;
		Float minimumIncrement = null;
		Float hiddenMinimum = null;
		System.out.println(auctionLength_s + " " + minimumIncrement_s + " " + hiddenMinimum_s);
		if(!auctionLength_s.isEmpty()){
			try{
				auctionLength = Integer.parseInt(auctionLength_s);
			} catch (NumberFormatException e){
				exit = true;
				e.printStackTrace();
			}
		}
		
		if(!minimumIncrement_s.isEmpty()){
			try{
				minimumIncrement = Float.parseFloat(minimumIncrement_s);
			} catch (NumberFormatException e){
				request.setAttribute("badMinimumIncr", "Minimum increment must have a valid price amount.");
				exit = true;
				e.printStackTrace();
			}
		}
		
		if (!hiddenMinimum_s.isEmpty()){
			try{
				hiddenMinimum= Float.parseFloat(hiddenMinimum_s);
			} catch (NumberFormatException e){
				request.setAttribute("badHiddenMin", "Hidden minimum must have a valid price amount.");
				exit = true;
				e.printStackTrace();
			}
		}
		
		//Tests if user has filled in the entries that are required.
		if (title.isEmpty()){
			request.setAttribute("noTitle", "Movie title field must be filled.");
			exit = true;
		}
		if (format == null){
			request.setAttribute("noFormat", "Movie format field must be chosen.");
			exit = true;
		}
		if (minimumIncrement == null){
			request.setAttribute("badMinimumIncr", "Minimum increment must be filled.");
			exit = true;
		}
		if (exit){
			request.getRequestDispatcher("/makeauction.jsp").forward(request, response);
			return;
		}
		
		if (description.isEmpty())
			description = null;
		
		//Instantiation of Auction and Item objects using above parameters
		//auction = new Auction(startDateTime, endDateTime, minimumIncrement, hiddenMinimum, seller)
		//item = new Item(null, duration, , title, genre, description, format)
		ApplicationDAO dao = new ApplicationDAO();
		try{
			Timestamp startDateTime = new Timestamp(System.currentTimeMillis());						//auction's start timestamp
			Timestamp endDateTime = new Timestamp(startDateTime.getTime() + auctionLength*DAY_IN_MS);	//auction's end timestamp
			HttpSession session = request.getSession();
			String username = ((Member)session.getAttribute("currentUser")).getUsername();	//get current session's username
			auction = new Auction(null, startDateTime, endDateTime, minimumIncrement, hiddenMinimum, username);
			dao.insert(auction, Auction.class);
			System.out.println("Auction inserted into DB");
			int auctionId = dao.countDB("SELECT LAST_INSERT_ID();");
			System.out.println("auctionId = " + auctionId);
			item = new Item(null, auctionId, duration, username, title, genre, description, format);
			dao.insert(item, Item.class);
			System.out.println("Item inserted into DB");
			request.getRequestDispatcher("/mainpage.jsp").forward(request, response);
		} catch(SQLException e){
			e.printStackTrace();
		} finally{
			dao.closeConnection();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
