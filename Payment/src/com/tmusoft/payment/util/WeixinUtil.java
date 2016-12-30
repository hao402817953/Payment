/**
 * 
 */
package com.tmusoft.payment.util;

import net.sf.json.JSONObject;

import com.google.gson.JsonObject;



/**
 * @author wumeng
 *
 */
public class WeixinUtil {

    //发送消息模板
    public static final String TEMPLATE_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send";
	
	
	
	/**
	 * 发送订单状态变化消息模板
	 * @param accessToken
	 * @param openid 用户的openid
	 * @param smsMessage 短信验证码内容
	 * @throws Exception 
	 */
	public JsonObject sendTemplate(String accessToken, String openid,WxTemplate t) throws Exception {
		
		
		return new SendNewsUtil().sendMsg(TEMPLATE_SEND_URL, accessToken, JSONObject.fromObject(t).toString());
		
	}
	
}
