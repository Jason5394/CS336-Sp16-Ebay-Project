package com.moviebay.pkg;

public class Item {
	private Integer itemId = null;		//primary key, autoincremented
	private Integer auctionId;	//foreign key references auction
	private Integer length;
	private String seller;	//foreign key references member
	private String title;
	private String genre;
	private String description;
	private String format;
	
	//Default constructor has no field for itemId because it is autoincremented in the DB.
	public Item(Integer itemId, Integer auctionId, Integer length, String seller, String title, String genre,
			String description, String format) {
		this.itemId = itemId;
		this.auctionId = auctionId;
		this.length = length;
		this.seller = seller;
		this.title = title;
		this.genre = genre;
		this.description = description;
		this.format = format;
	}
	
	public Item(){
		
	}
	
	//getter and setter methods
	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	
	public Integer getAuctionId() {
		return auctionId;
	}

	public void setAuctionId(Integer auctionId) {
		this.auctionId = auctionId;
	}
	
	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}
	
	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}
