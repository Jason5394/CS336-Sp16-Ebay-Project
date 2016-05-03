package com.moviebay.pkg;

import java.sql.Timestamp;

public class Email {
	
	private Integer email_id;
	private String sender;
	private String recipient;
	private String subject;
	private Timestamp date_time; 
	private String content;
	
	public Email(){}
	
	public Email(Integer email_id, String sender, String recipient, String subject, Timestamp date_time, String content)
	{
		this.setEmailId(email_id);
		this.setSender(sender);
		this.setRecipient(recipient);
		this.setSubject(subject);
		this.setDateTime(date_time);
		this.setContent(content);
	}
	
	public Integer getEmailId(){
		return email_id;
	}
	
	public void setEmailId(Integer email_id){
		this.email_id = email_id;
	}
	
	public String getSender(){
		return sender;
	}
	
	public void setSender(String sender){
		this.sender = sender;
	}
	
	public String getRecipient(){
		return recipient;
	}
	
	public void setRecipient(String recipient){
		this.recipient = recipient;
	}
	
	public String getSubject(){
		return subject;
	}
	
	public void setSubject(String subject){
		this.subject = subject;
	}
	
	public Timestamp getDateTime(){
		return date_time;
	}
	
	public void setDateTime(Timestamp date_time){
		this.date_time = date_time;
	}
	
	public String getContent(){
		return content;
	}
	
	public void setContent(String content){
		this.content = content;
	}
}
