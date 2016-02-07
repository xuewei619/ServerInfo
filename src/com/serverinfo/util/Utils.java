package com.serverinfo.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Utils {
	
	
	public static String firstUpper(String str) {
		if (!"".equals(str)) {
			return str.substring(0, 1).toUpperCase() + str.substring(1);
		}
		return null;
	}
	
	public static Map<String,Object> getParams(Object record){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			
			Field[] fields = record.getClass().getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				String property = field.getName();
				String methodName = "get" + Utils.firstUpper(property);
				Method method = record.getClass().getMethod(methodName);				
				Object obj = method.invoke(record);
				map.put(property, obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static String getUuid(){
		return UUID.randomUUID().toString().substring(0, 5).replace("-", "");
	}
	
	public static String date2str(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	public static boolean Random(double rate){
		double random_num = Math.random() * 100;
		if(random_num > 0 && random_num < (rate *100)){
			return true;
		}
		return false;
	}	
}
