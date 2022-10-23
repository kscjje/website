package com.hisco.cmm.util;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Utils {
	
	// Key 값의 대문자를 소문자로 변경
	public static List keyChangeLower2(List<HashMap> list) {
		List<HashMap> newList = new LinkedList<HashMap>();
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> tm = new HashMap<String, Object>(list.get(i));
			Iterator<String> iteratorKey = tm.keySet().iterator(); // 키값 오름차순
			HashMap  newMap = new HashMap();
			// //키값 내림차순 정렬
			while (iteratorKey.hasNext()) {
				String key = iteratorKey.next();
				newMap .put(key.toLowerCase(), tm.get(key));
			}
			newList.add(newMap);
		}
		return newList;
	}	
	
	public static List<?> keyChangeLower(List<HashMap<String, Object>> list) {
		List<HashMap<String, Object>> newList = new LinkedList<HashMap<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> tm = new HashMap<String, Object>(list.get(i));
			Iterator<String> iteratorKey = tm.keySet().iterator(); // 키값 오름차순
			HashMap<String, Object>  newMap = new HashMap<String, Object>();
			// //키값 내림차순 정렬
			while (iteratorKey.hasNext()) {
				String key = iteratorKey.next();
				newMap .put(key.toLowerCase(), tm.get(key));
			}
			newList.add(newMap);
		}
		return newList;
	}	
	
	public static List<HashMap<String, Object>> keyChangeLower3(List<HashMap<String, Object>> list) {
		List<HashMap<String, Object>> newList = new LinkedList<HashMap<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> tm = new HashMap<String, Object>(list.get(i));
			Iterator<String> iteratorKey = tm.keySet().iterator(); // 키값 오름차순
			HashMap<String, Object>  newMap = new HashMap<String, Object>();
			// //키값 내림차순 정렬
			while (iteratorKey.hasNext()) {
				String key = iteratorKey.next();
				newMap .put(key.toLowerCase(), tm.get(key));
			}
			newList.add(newMap);
		}
		return newList;
	}
	
	public static Map<String, Object> convertToMap(Object obj) throws
	IllegalAccessException, InstantiationException,
	IllegalArgumentException,InvocationTargetException, NoSuchMethodException, SecurityException{
		if(obj == null){
			return Collections.emptyMap();
		}
		Map<String,Object> convertMap = new HashMap<String,Object>();
		Field[] fields = obj.getClass().getDeclaredFields();
		for(Field field : fields){
			field.setAccessible(true);
			convertMap.put(field.getName(), field.get(obj));
		}
		return convertMap;
	}
	
	
	public static List<Map<String, Object>> convertToMaps(List<?> list) throws
	IllegalAccessException, InstantiationException,
	IllegalArgumentException,InvocationTargetException, NoSuchMethodException, SecurityException{
		if(list == null || list.isEmpty()){
			return Collections.emptyList();
		}
		List<Map<String, Object>> convertList = new ArrayList<Map<String, Object>>();
		for(Object obj : list){
			convertList.add(convertToMap(obj));
		}
		return convertList;
	}
	
	public static List<String> makeForeach(String code){
	    String gb = ",";
	    return makeForeach(code, gb);
	}
	public static List<String> makeForeach(String code, String gb){
	     
	    if (code == null || "".equals(code)) {
	        return null;
	    }
	     
	    List<String> codeList = new ArrayList<String>();
	     
	    String[] aCode = code.split(gb);
	    for(int i=0; i< aCode.length; i++){
	         
	        codeList.add(aCode[i].toString());
	    }
	 
	    return codeList;       
	}
}
