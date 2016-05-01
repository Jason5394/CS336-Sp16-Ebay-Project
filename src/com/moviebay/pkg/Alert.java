package com.moviebay.pkg;

public class Alert {
	private Integer alert_id;
	private String movie_title;
	private String genre;
	private String movie_format;
	private String owner;		
	
	public Alert(Integer alert_id, String movie_title, String genre, String movie_format, String owner){
		this.alert_id = alert_id;
		this.movie_title = movie_title;
		this.genre = genre;
		this.movie_format = movie_format;
		this.owner = owner;
	}
	
	public Alert(){
		
	}
	
	public Integer getAlertId(){
		return alert_id;
	}
	
	public void setAlertId(Integer alert_id){
		this.alert_id = alert_id;
	}
	
	public String getMovieTitle(){
		return movie_title;
	}
	
	public void setMovieTitle(String movie_title){
		this.movie_title = movie_title;
	}
	
	public String getGenre(){
		return genre;
	}
	
	public void setGenre(String genre){
		this.genre = genre;
	}
	
	public String getMovieFormat(){
		return movie_format;
	}
	
	public void setMovieFormat(String movie_format){
		this.movie_format = movie_format;
	}
	
	public String getOwner(){
		return owner;
	}
	
	public void setOwner(String owner){
		this.owner = owner;
	}
}
