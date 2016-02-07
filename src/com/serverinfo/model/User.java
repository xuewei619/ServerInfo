package com.serverinfo.model;

import java.util.Date;

public class User {
	private String id;
	
	private String username;
	
	private String password;
	
	private Date reg_time;
	
	private Date lastlogin_time;
	
	private String address;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getReg_time() {
		return reg_time;
	}

	public void setReg_time(Date reg_time) {
		this.reg_time = reg_time;
	}

	public Date getLastlogin_time() {
		return lastlogin_time;
	}

	public void setLastlogin_time(Date lastlogin_time) {
		this.lastlogin_time = lastlogin_time;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", reg_time=" + reg_time
				+ ", lastlogin_time=" + lastlogin_time + ", address=" + address + "]";
	}	
}
