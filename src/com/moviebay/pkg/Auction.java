package com.moviebay.pkg;

import java.sql.Timestamp;

public class Auction {
	private Integer auctionId = null;	//primary key, autoincrement
	private Timestamp startDateTime;
	private Timestamp endDateTime;
	private Float minimumIncrement;
	private Float hiddenMinimum;
	private Float topBid;
	private String seller;			//foreign key references member
	private String bidder;			//foreign key references member
	
	public Auction(Integer auctionId, Timestamp startDateTime, Timestamp endDateTime, Float minimumIncrement,
			Float hiddenMinimum, String seller, Float topBid, String bidder) {
		this.auctionId = auctionId;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.minimumIncrement = minimumIncrement;
		this.hiddenMinimum = hiddenMinimum;
		this.topBid = topBid;
		this.seller = seller;
		this.bidder = bidder;
	}
	
	public Auction(){
		
	}
	
	//getter and setter methods
	public Integer getAuctionId() {
		return auctionId;
	}
	
	public void setAuctionId(Integer auctionId) {
		this.auctionId = auctionId;
	}
	
	public Timestamp getStartDateTime() {
		return startDateTime;
	}
	
	public void setStartDateTime(Timestamp startDateTime) {
		this.startDateTime = startDateTime;
	}
	
	public Timestamp getEndDateTime() {
		return endDateTime;
	}
	
	public void setEndDateTime(Timestamp endDateTime) {
		this.endDateTime = endDateTime;
	}
	
	public Float getMinimumIncrement() {
		return minimumIncrement;
	}

	public void setMinimumIncrement(Float minimumIncrement) {
		this.minimumIncrement = minimumIncrement;
	}
	
	public Float getHiddenMinimum() {
		return hiddenMinimum;
	}

	public void setHiddenMinimum(Float hiddenMinimum) {
		this.hiddenMinimum = hiddenMinimum;
	}
	
	public Float getTopBid() {
		return topBid;
	}

	public void setTopBid(Float topBid) {
		this.topBid = topBid;
	}
	
	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}
	
	public String getBidder() {
		return bidder;
	}

	public void setBidder(String bidder) {
		this.bidder = bidder;
	}
	
}
