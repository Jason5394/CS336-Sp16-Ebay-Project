package com.moviebay.pkg;

public class Member {

	private String username;
	private String firstName;
	private String lastName;
	private String password;
	private boolean is_customer_rep;
	private boolean is_admin;
	
	public Member(String username, String firstName, String lastName,
			String password, boolean isCusRep, boolean isAdmin) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		is_customer_rep = isCusRep;
		is_admin = isAdmin;
	}

	public Member(){
	}
	
	//getter and setter methods for class Member
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean getCusRepStatus() {
		return is_customer_rep;
	}

	public void setCusRepStatus(boolean is_customer_rep) {
		this.is_customer_rep = is_customer_rep;
	}

	public boolean getAdminStatus() {
		return is_admin;
	}

	public void setAdminStatus(boolean is_admin) {
		this.is_admin = is_admin;
	}
	
}
