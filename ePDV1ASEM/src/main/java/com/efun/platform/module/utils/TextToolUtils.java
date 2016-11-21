package com.efun.platform.module.utils;

import java.util.Iterator;
import java.util.Map;

import android.text.TextUtils;

public class TextToolUtils {

	/**
	 * String 定義字符大小范圍
	 * @param mStr
	 * @param minLength
	 * @param maxLength
	 * @return
	 */
	public static boolean isStringLimited(String mStr,int minLength,int maxLength){
		if(TextUtils.isEmpty(mStr)){
			return false;
		}
		if(mStr.length() >= minLength && mStr.length() < maxLength){
			return true;
		}else{			
			return false;
		}
		
	}
	/**
	 * 以字母打頭的限制
	 * @param mStr
	 * @return
	 */
	public static boolean isStringLimited(String mStr){
		if(TextUtils.isEmpty(mStr)){
			return false;
		}
		char[] mchars = mStr.toCharArray();
		if((mchars[0] > 64 && mchars[0] < 91) || (mchars[0] > 96 && mchars[0] < 123)){
			return true;
		}else{
			return false;
		}
		
	}
	
	/** 
	 * 方法名称:transMapToString 
	 * 传入参数:map 
	 * 返回值:String 形如 username'chenziwen^password'1234 
	*/  
	public static String transMapToString(Map map){  
	  java.util.Map.Entry entry;  
	  StringBuffer sb = new StringBuffer();  
	  for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)  
	  {  
	    entry = (java.util.Map.Entry)iterator.next();  
	      sb.append(entry.getKey().toString()).append( "=" ).append(null==entry.getValue()?"":  
	      entry.getValue().toString()).append (iterator.hasNext() ? "," : "");  
	  }  
	  return sb.toString();  
	}  
}
