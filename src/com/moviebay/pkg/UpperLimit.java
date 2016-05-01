package com.moviebay.pkg;

public class UpperLimit {
	private Integer auction_id;
	private String bidder;
	private Float upper_limit;
	
	public UpperLimit(Integer auction_id, String bidder, Float upper_limit){
		this.auction_id = auction_id;
		this.bidder = bidder;
		this.upper_limit = upper_limit;
	}
	
	public Integer getAuctionId(){
		return auction_id;
	}
	
	public void setAuctionId(Integer auction_id){
		this.auction_id = auction_id;
	}
	
	public String getBidder(){
		return bidder;
	}
	
	public void setBidder(String bidder){
		this.bidder = bidder;
	}
	
	public Float getUpperLimit(){
		return upper_limit;
	}
	
	public void setUpperLimit(Float upper_limit){
		this.upper_limit = upper_limit;
	}
}
