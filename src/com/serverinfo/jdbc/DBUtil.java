package com.serverinfo.jdbc;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.serverinfo.util.Utils;


public class DBUtil {

	private final static String url = "jdbc:mysql://127.0.0.1:3306/minecraft";

	private final static String username = "root";

	private final static String password = "619";

	private Connection conn;

	private PreparedStatement pstmt;
	
	private Statement stmt;

	private ResultSet resultSet;

	
	public DBUtil() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConn() throws SQLException {
		if (conn == null) {
			conn = DriverManager.getConnection(url, username, password);
		}
		return conn;
	}
	
	/**
	 * 
	 * @param table 插入哪个表
	 * @param record model的实例
	 * @return
	 * @throws SQLException
	 */
	public boolean insert(String table,Object record) throws SQLException{
		
		Map<String,Object> map = Utils.getParams(record);
		StringBuilder params = new StringBuilder();
		StringBuilder values = new StringBuilder();
		for(Entry<String, Object> entry : map.entrySet()){			
			if(entry.getValue() != null){
				params.append(entry.getKey() + ",");
				Object value = entry.getValue();
				if(value instanceof String){
					value = "\'" + value + "\'";
				}else if(value instanceof Date){
					value = (Date)value;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					value = "\'" + sdf.format(value) + "\'";
				}
				values.append(value + ",");
				
			}
		}
		params.deleteCharAt(params.length() - 1);
		values.deleteCharAt(values.length() - 1);
		
		String sql = "insert into " + table + "(" + params.toString() + ") " + "values(" +  values.toString() + ")";
		stmt = conn.createStatement();	
		return stmt.execute(sql);
	} 
	
	
	public boolean update(String sql, List<Object> params)throws SQLException{
		int index = 1;
		pstmt = conn.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}		
		
		return pstmt.execute();
	}
	
	/**
	 * 反射获得单条记录
	 * @param sql
	 * @param params
	 * @param clazz model类
	 * @return
	 * @throws Exception
	 */
	public <T> T getSingleResult(String sql, List<Object> params, Class<T> clazz)
			throws Exception {
		
		T t = clazz.newInstance();
		int index = 1;
		pstmt = conn.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int col_len = metaData.getColumnCount();
		while (resultSet.next()) {
			for (int i = 0; i < col_len; i++) {
				String col_name = metaData.getColumnName(i + 1);
				Object col_value = resultSet.getObject(col_name);				
				Field field = clazz.getDeclaredField(col_name);
				field.setAccessible(true);
				field.set(t, col_value);
			}
		}
		return t;
	}
	
	
	/**
	 * 反射获得多条记录
	 * @param sql
	 * @param params
	 * @param clazz model类
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> getMultiResults(String sql, List<Object> params, Class<T> clazz) throws Exception{
		List<T> list = new ArrayList<T>();
		int index = 1;
		pstmt = conn.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int col_len = metaData.getColumnCount();
		while (resultSet.next()) {
			T t = clazz.newInstance();
			for (int i = 0; i < col_len; i++) {
				String col_name = metaData.getColumnName(i + 1);
				Object col_value = resultSet.getObject(col_name);				
				Field field = clazz.getDeclaredField(col_name);
				field.setAccessible(true);
				field.set(t, col_value);
			}
			list.add(t);
		}
		return list;
	}	
	
	public void release(){
		try {
			if(conn != null){
				conn.close();
			}
			
			if(pstmt != null){
				pstmt.close();
			}
			
			if(resultSet != null){
				resultSet.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
