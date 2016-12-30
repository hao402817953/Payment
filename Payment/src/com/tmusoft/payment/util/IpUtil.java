package com.tmusoft.payment.util;

import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {

	public static String getRemoteAddress(HttpServletRequest req) {

		String ip = req.getHeader("x-forwarded-for");
		if (StrFunc.isNull(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (StrFunc.isNull(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (StrFunc.isNull(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
			if ("127.0.0.1".equals(ip) || "localhost".equalsIgnoreCase(ip)
					|| "0:0:0:0:0:0:0:1".equals(ip)) {//windows7上，还可能是0:0:0:0:0:0:0:1
				try {
					ip = InetAddress.getLocalHost().getHostAddress();
					return ip;
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return ip;
	}
}
