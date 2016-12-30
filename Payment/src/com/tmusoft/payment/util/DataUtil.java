/**
 * 
 */
package com.tmusoft.payment.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;


/**
 * @author wumeng
 *
 */
public class DataUtil {
	
	
	
	
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	
	
	/**
	 * @param str
	 * 生成长度为len的字符串(如果len in [0,str.length] 返回str;如果len > （str.length+17） 返回唯一字符串)
	 * @return
	 */
	public static String createUniqueCode(String str,int len){
		
		if(str==null)
			str = "";
		
		str =str.trim();
		
		if(len<=str.length()){
			
			return str;
			
		}else if(len<(str.length()+18)){
			
			return str+generateWord(len-str.length());
		}else{
			
			return str+sdf.format(new Date())+generateWord(len-17-str.length());
		}
		
		
	}
	

    private static String generateWord(int n) {
		String[] beforeShuffle = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
				"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		List list = Arrays.asList(beforeShuffle);
		Collections.shuffle(list);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}
		String afterShuffle = sb.toString();
		String result = afterShuffle.substring(1, 2+n);
		return result;
	}
    
	public static boolean checkDouble(String i){
		
		boolean flg = true;
		try {
			Double i_d = Double.valueOf(i);
			
		} catch (Exception e) {
			flg &= false;
		}
		
		return flg;
	}
	


}
