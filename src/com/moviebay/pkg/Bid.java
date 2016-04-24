package com.moviebay.pkg;

import java.sql.Timestamp;

public class Bid {
	private Integer bid_id;			//primary key
	private Float bid_amount;
	private Timestamp creation_datetime;
	private String bidder;			//references Member
	private Integer auction_id;		//references Auction	
	
	public Bid(Integer bid_id, Float bid_amount, Timestamp creation_datetime, String bidder,
			Integer auction_id){
		
		this.bid_id = bid_id;
		this.bid_amount = bid_amount;
		this.creation_datetime = creation_datetime;
		this.bidder = bidder;
		this.auction_id = auction_id;
	}
	
	public Bid(){
		
	}
	
	public Integer getBidId(){
		return bid_id;
	}
	
	public void setBidId(Integer bid_id){
		this.bid_id = bid_id;
	}
	public Float getBidAmount(){
		return bid_amount;
	}
	
	public void setBidAmount(Float bid_amount){
		this.bid_amount = bid_amount;
	}
	
	public Timestamp getCreationDateTime(){
		return creation_datetime;
	}
	
	public void setCreationDateTime(Timestamp creation_datetime){
		this.creation_datetime = creation_datetime;
	}
	
	public String getBidder(){
		return bidder;
	}
	
	public void setBidder(String bidder){
		this.bidder = bidder;
	}
	
	public Integer getAuctionId(){
		return auction_id;
	}
	
	public void setAuctionId(Integer auction_id){
		this.auction_id = auction_id;
	}
	
}
