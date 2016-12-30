/**
 * 
 */
package com.tmusoft.payment.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wumeng
 *
 */
public class VerificationUtil {
	
	
	/**
	 * @param map
	 * 校验map中的key对应的value是否为空
	 * @return
	 */
	public static Map<String,String> isNotNull(Map<String,String> map){
		
		
		Map<String,String> mapR = new HashMap<String, String>();
		
		mapR.put("flag", "200");
		
		if(map==null||map.isEmpty()){
			
			mapR.put("flag", "200");
			
			return mapR;
		}
		
		for(Map.Entry<String, String> entry:map.entrySet()){
			
				String k = entry.getKey();
				
				
			
		}
		
		
		
		
		return mapR;
		
	}

}
