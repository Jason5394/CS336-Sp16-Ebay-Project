package com.moviebay.pkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;

public class ApplicationDAO {
	private static final String USER = "root";
	private static final String PASS = "BecauseLwe3";
	//private static final String PASS = "yourownpass";
	private static final String URL = "jdbc:mysql://localhost:3306/projDB?autoReconnect=true";
	private Connection connection = null;
	
	/* Default constructor establishes connection with database immediately - 
	 * the class variable connection is instantiated.
	 */
	public ApplicationDAO() {
		getConnection();
	}
	
	public void getConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(URL, USER, PASS);	//VM Settings
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Connected to database.");
	}
	
	public void closeConnection(){
		try {
			if (connection != null)
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Closed connection to database.");
	}
	
	/*
	 * Method to query any table.  The method CANNOT accept 
	 * insertions, deletions, or updates, otherwise an error occurs.  The 
	 * input value "query" indicates the query in the form of a string 
	 * to be issued to the database.  The method also takes a type of class.
	 * Example method call:
	 * 		LinkedList<Member> = dao.queryDB(querystring, Member.class);
	 * Queries must be of the correct syntax consistent with MySQL.
	 * The method returns a generic LinkedList, with the list of results.
	 */ 
	@SuppressWarnings("unchecked")
	public <T> LinkedList<T> queryDB(String query, Class<T> cls) throws SQLException{
		LinkedList<T> answers = new LinkedList<T>();
		PreparedStatement prepState = null;
		ResultSet rs = null;
		try{
			prepState = connection.prepareStatement(query);
			System.out.println(prepState.toString());
			rs = prepState.executeQuery();
			while(rs.next()){
				if (cls == Alert.class){
					answers.add((T) new Alert(rs.getInt("alert_id"), rs.getString("movie_title"), rs.getString("genre"),
							rs.getString("movie_format"), rs.getString("owner")));
				}
				else if (cls == Auction.class){
					answers.add((T) new Auction(rs.getInt("auction_id"), rs.getTimestamp("start_datetime"),
							rs.getTimestamp("end_datetime"), rs.getFloat("minimum_increment_price"), 
							(Float)rs.getObject("hidden_minimum_price"), rs.getString("seller"),
							rs.getFloat("top_bid"), rs.getString("bidder"), rs.getString("winner")));
				}
				else if (cls == Bid.class){
					answers.add((T) new Bid(rs.getInt("bid_id"), rs.getFloat("bid_amount"), 
							rs.getTimestamp("creation_datetime"), rs.getString("bidder"), rs.getInt("auction_id")));
				}
				else if (cls == Email.class){
					//TODO
				}
				else if (cls == Item.class){
					answers.add((T) new Item(rs.getInt("item_id"), rs.getInt("auction_id"), (Integer)rs.getObject("movie_length"),
							rs.getString("seller"), rs.getString("movie_title"), rs.getString("genre"), 
							rs.getString("description"), rs.getString("movie_format")));
				}
				else if (cls == Member.class){
					answers.add((T) new Member(rs.getString("username"), rs.getString("first_name"),
						rs.getString("last_name"), rs.getString("password"), rs.getBoolean("is_customer_rep"), 
						rs.getBoolean("is_admin")));
				}
				else if (cls == UpperLimit.class){
					answers.add((T) new UpperLimit(rs.getInt("auction_id"), rs.getString("bidder"), rs.getFloat("upper_limit")));
				}
				
			}
		} catch (SQLException e){
			e.printStackTrace();
		} finally{
			try { if (rs != null) rs.close(); } catch (SQLException e){};
			try { if (prepState != null) prepState.close(); } catch (SQLException e){};
		}
		return answers;
	}
	
	/*
	 * Important: only use this method to query the DB using a count operator in mysql, 
	 * or any other query that returns only a single integer result.
	 * Otherwise, it will throw an exception.  An example query that would work with
	 * this method could be: "Select count(*) from Member;"
	 */
	public int countDB(String query) throws SQLException{
		int count = 0;
		PreparedStatement prepState = null;
		ResultSet rs = null;
		try{
			prepState = connection.prepareStatement(query);
			System.out.println(prepState.toString());
			rs = prepState.executeQuery();
			while(rs.next()){
				count = rs.getInt(1);
			}
		} catch (SQLException e){
			e.printStackTrace();
		} finally{
			try { if (rs != null) rs.close(); } catch (SQLException e){};
			try { if (prepState != null) prepState.close(); } catch (SQLException e){};
		}
		return count;
	}
	
	/*
	 * Method to insert a row into any table.  The insertion object
	 * must contain the correct fields consistent with the table in the 
	 * database.  The method takes two inputs, a table of generic class T,
	 * and the type of class, e.g. Member.class.  
	 */
	public <T> void insert(T table, Class<T> cls) throws SQLException{
		PreparedStatement prepState = null;
		String insertString = null;
		
		try{
			if (cls == Alert.class){
				insertString = "INSERT INTO Alert (alert_id, movie_title, genre, movie_format, owner) "
						+ "VALUES(?, ?, ?, ?, ?);";
				prepState = connection.prepareStatement(insertString);
				prepState.setNull(1, Types.INTEGER);	//set NULL because first parameter is auto-incremented
				prepState.setString(2, ((Alert) table).getMovieTitle());
				prepState.setString(3, ((Alert) table).getGenre());
				prepState.setString(4, ((Alert) table).getMovieFormat());
				prepState.setString(5, ((Alert) table).getOwner());
			}
			else if (cls == Auction.class){
				insertString = "INSERT INTO Auction (auction_id, start_datetime, end_datetime,"
						+ " minimum_increment_price, hidden_minimum_price, seller, top_bid, bidder, winner) "
						+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
				prepState = connection.prepareStatement(insertString);
				prepState.setNull(1, Types.INTEGER);	//set NULL because first parameter is auto-incremented
				prepState.setTimestamp(2, ((Auction) table).getStartDateTime());
				prepState.setTimestamp(3, ((Auction) table).getEndDateTime());
				prepState.setObject(4, (Float)((Auction) table).getMinimumIncrement());
				prepState.setObject(5, (Float)((Auction) table).getHiddenMinimum());
				prepState.setString(6, ((Auction) table).getSeller());
				prepState.setObject(7, (Float)((Auction) table).getTopBid());	
				prepState.setString(8, ((Auction) table).getBidder());
				prepState.setString(9, ((Auction) table).getWinner());
				
				System.out.println(prepState.toString());
			}
			else if (cls == Bid.class){
				insertString = "INSERT INTO Bid (bid_id, bid_amount, creation_datetime, bidder, auction_id) "
						+ "VALUES(?, ?, ?, ?, ?);";
				prepState = connection.prepareStatement(insertString);
				prepState.setNull(1, Types.INTEGER);
				prepState.setFloat(2, ((Bid) table).getBidAmount());
				prepState.setTimestamp(3, ((Bid) table).getCreationDateTime());
				prepState.setString(4, ((Bid) table).getBidder());
				prepState.setInt(5, ((Bid) table).getAuctionId());
				
				System.out.println(prepState.toString());
			}
			else if (cls == Email.class){
				//TODO
			}
			else if (cls == Item.class){
				insertString = "INSERT INTO Item (item_id, movie_title, genre, movie_length,"
						+ " description, movie_format, seller, auction_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
				prepState = connection.prepareStatement(insertString);
				prepState.setNull(1, Types.INTEGER);	//set NULL because first parameter is auto-incremented
				prepState.setString(2, ((Item) table).getTitle());
				prepState.setString(3, ((Item) table).getGenre());
				prepState.setObject(4, (Integer)((Item) table).getLength());
				prepState.setString(5, ((Item) table).getDescription());
				prepState.setString(6, ((Item) table).getFormat());
				prepState.setString(7, ((Item) table).getSeller());
				prepState.setObject(8, (Integer)((Item) table).getAuctionId());
				
				System.out.println(prepState.toString());
			}
			else if (cls == Member.class){
				insertString = "INSERT INTO Member (username, first_name, last_name, password, "
						+ "is_customer_rep, is_admin) VALUES(?, ?, ?, ?, ?, ?);";
				prepState = connection.prepareStatement(insertString);
				prepState.setString(1, ((Member) table).getUsername());
				prepState.setString(2, ((Member) table).getFirstName());
				prepState.setString(3, ((Member) table).getLastName());
				prepState.setString(4, ((Member) table).getPassword());
				prepState.setBoolean(5, ((Member) table).getCusRepStatus());
				prepState.setBoolean(6, ((Member) table).getAdminStatus());
				
				System.out.println(prepState.toString());
			}
			else if (cls == UpperLimit.class){
				insertString = "INSERT INTO UpperLimit (auction_id, bidder, upper_limit) VALUES(?, ?, ?);";
				prepState = connection.prepareStatement(insertString);
				prepState.setInt(1, ((UpperLimit) table).getAuctionId());
				prepState.setString(2, ((UpperLimit) table).getBidder());
				prepState.setFloat(3, ((UpperLimit) table).getUpperLimit());	
				
				System.out.println(prepState.toString());
			}
			// execute select SQL statement
			prepState.executeUpdate();
			System.out.println("Added into table " + cls.toString());
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			try { if (prepState != null) prepState.close(); } catch (SQLException e){};
		}
	}

	/*
	 * Generic method that's useful for updating or deleting an entry in a table.
	 * Be warned that the string must follow the correct MySQL syntax, as well as
	 * adhering to the correct formatting for each kind of table.  An example input
	 * to this method is:
	 * 		"UPDATE Member SET first_name=John, last_name=Doe WHERE username=admin;"
	 */
	public void del_or_upd(String query) throws SQLException{
		PreparedStatement prepState = null;
		try{
			prepState = connection.prepareStatement(query);
			System.out.println(prepState.toString());
			prepState.executeUpdate();
		} catch (SQLException e){
			e.printStackTrace();
		} finally{
			try { if (prepState != null) prepState.close(); } catch (SQLException e){};
		}
	}
	
	//public boolean 
	public static void main(String[] args) {
		ApplicationDAO dao = new ApplicationDAO();
		//Connection connection = dao.getConnection();
		
		//System.out.println(connection);
		
		/*
		try {
			Member newmember = new Member("Jason5394", "Jason", "Yang", "abcde", false, false);
			dao.insertMember(newmember);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		*/
		//Updating member
		try{
			dao.del_or_upd("Update Member set first_name=\"Jason\" WHERE username=\"Jason5394\";");
			System.out.println("Updated");
		} catch (SQLException e){
			e.printStackTrace();
		}
		try{
			int count = dao.countDB("Select count(*) from member;");
			System.out.println(count);
		} catch (SQLException e){
			e.printStackTrace();
		}
		//dao.closeConnection(connection);	*/
	}
}
