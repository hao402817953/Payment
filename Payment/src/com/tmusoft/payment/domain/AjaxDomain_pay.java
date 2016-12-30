package com.tmusoft.payment.domain;

import java.util.Map;

/**
 * @title WachatAjaxDomain
 * @description ajax domain
 * @author Micle
 * @version 
 * 
 */
public class AjaxDomain_pay {

	
	private String statusCode;//200成功，300失败,400异常
	private String message;
	private Map<String,Object> map;
	
	private String method;
	
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	
	
}
