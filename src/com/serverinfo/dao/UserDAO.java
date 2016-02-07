package com.serverinfo.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.serverinfo.jdbc.DBUtil;
import com.serverinfo.model.User;

public class UserDAO {
	
	public final static String selectUser = "select * from user where username = ? and password = ?";
	
	public final static String selectUserByName = "select * from user where username = ?";
	
	public final static String updateUserLastTime = "update user set lastlogin_time = ? , address = ? where id = ?";
	
	public User selectUser(String username,String password){
		User user = null;
		DBUtil db = new DBUtil();
		List<Object> params = new ArrayList<Object>();
		params.add(username);
		params.add(password);
		try {
			db.getConn();
			user = db.getSingleResult(selectUser, params, User.class);
		} catch (Exception e) {			
			e.printStackTrace();
		}finally {
			db.release();
		}		
		
		return user;
	}
	
	public User selectUser(String username){
		User user = null;
		DBUtil db = new DBUtil();
		List<Object> params = new ArrayList<Object>();
		params.add(username);		
		try {
			db.getConn();
			user = db.getSingleResult(selectUserByName, params, User.class);
		} catch (Exception e) {			
			e.printStackTrace();
		}finally {
			db.release();
		}		
		
		return user;
	}
	
	public boolean updateLastTime(String id,Date lastlogin_time,String address){
		boolean flag = false;
		List<Object> params = new ArrayList<Object>();
		params.add(lastlogin_time);
		params.add(address);
		params.add(id);
		DBUtil db = new DBUtil();
		try {
			db.getConn();
			flag = db.update(updateUserLastTime, params);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean insertUser(User user){
		boolean flag = true;
		DBUtil db = new DBUtil();
		try {
			db.getConn();
			flag = db.insert("user", user);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return flag;
	}
	

		

}
