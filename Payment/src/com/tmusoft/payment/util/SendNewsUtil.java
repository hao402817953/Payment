package com.tmusoft.payment.util;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpStatus;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SendNewsUtil {

	

	/**
	 * 发送消息
	 * 
	 * @param uploadurl
	 * @param apiurl
	 * @param access_token
	 * @param data
	 *            发送数据
	 * @return
	 * @throws Exception 
	 */
	public JsonObject sendMsg(String url, String token, String data) throws Exception {
		
		org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
		String sendurl = String.format("%s?access_token=%s", url, token);
		PostMethod post = new PostMethod(sendurl);
		post.setRequestHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:30.0) Gecko/20100101 Firefox/30.0");
		post.setRequestHeader("Host", "file.api.weixin.qq.com");
		post.setRequestHeader("Connection", "Keep-Alive");
		post.setRequestHeader("Cache-Control", "no-cache");
		post.addRequestHeader("Content-Type", "text/html; charset=utf-8"); 
		JsonObject result = null;
		try {
			post.setRequestBody(data);
			int status = client.executeMethod(post);
			if (status == HttpStatus.SC_OK) {
				String responseContent = post.getResponseBodyAsString();
				JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
				JsonObject json = jsonparer.parse(responseContent).getAsJsonObject();
				result = json;
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally{
		}
		return result;
	}

	public static class UTF8PostMethod extends PostMethod {// 防止发送的消息产生乱码
		public UTF8PostMethod(String url) {
			super(url);
		}

		@Override
		public String getRequestCharSet() {// 文本编码格式
			return "UTF-8";
		}
		
	}
	

	
	
	
}
