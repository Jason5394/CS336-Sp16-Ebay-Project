package com.moviebay.pkg;

import java.sql.Timestamp;

public class Auction {
	private Integer auctionId = null;	//primary key, autoincrement
	private Timestamp startDateTime;
	private Timestamp endDateTime;
	private Float minimumIncrement;
	private Float hiddenMinimum;
	private String seller;			//foreign key references member
	
	public Auction(Integer auctionId, Timestamp startDateTime, Timestamp endDateTime, Float minimumIncrement,
			Float hiddenMinimum, String seller) {
		this.auctionId = auctionId;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.minimumIncrement = minimumIncrement;
		this.hiddenMinimum = hiddenMinimum;
		this.seller = seller;
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
	
	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}
	
		
}
