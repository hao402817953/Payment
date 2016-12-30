/**
 * 
 */
package com.tmusoft.payment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.tmusoft.payment.domain.AjaxDomain_pay;
import com.tmusoft.payment.domain.WeChatConfig;
import com.tmusoft.payment.util.CreatePngFile;
import com.tmusoft.payment.util.GetWxOrderno;

/**
 * @author wumeng
 *
 */
public class PayHandlerForWechat {
	
	public static String prepay_url="https://api.mch.weixin.qq.com/pay/unifiedorder";//获取预支付id的url
	

	
	public AjaxDomain_pay getPrepayInfo(WeChatConfig weChatConfig){
		
		AjaxDomain_pay ajaxDomain = new AjaxDomain_pay();
		ajaxDomain.setMethod("getPrepayInfo");
		
		if(weChatConfig==null){
			
			ajaxDomain.setStatusCode("300");
			ajaxDomain.setMessage("weChatConfig is not null;");
			
			return ajaxDomain;
		}
		
		ajaxDomain = weChatConfig.infoIsPass();
		
		if(!StringUtils.equals("200", ajaxDomain.getStatusCode())){
			
			return ajaxDomain;
		}
		
		String prepay_id = "";
		String code_url = "";
		String trade_type = "";
		String nonce_str = "";
		try {
			Map<String,String> map = new GetWxOrderno().getWxPayAppInfo(prepay_url, weChatConfig.createPrepayXml());

			
		  if(map==null){
				
				ajaxDomain.setStatusCode("300");
				ajaxDomain.setMessage("系统异常");
				return ajaxDomain;
			}else{
				String return_code = map.get("return_code");
				String return_msg = map.get("return_msg");
				String result_code = map.get("result_code");
				if(StringUtils.equalsIgnoreCase("SUCCESS", return_code)){
					
					String err_code_des = map.get("err_code_des");
					if(StringUtils.equalsIgnoreCase("SUCCESS", result_code)){
						
						prepay_id = map.get("prepay_id");
						trade_type = map.get("trade_type");
						
						nonce_str = map.get("nonce_str");
						if(StringUtils.equalsIgnoreCase("NATIVE", trade_type)){//扫码支付
							
							code_url = map.get("code_url");
							System.out.println(code_url);
						}
						
						
					}else{
						ajaxDomain.setStatusCode("300");
						ajaxDomain.setMessage(err_code_des);
						return ajaxDomain;
					}
					
				}else{
					
					ajaxDomain.setStatusCode("300");
					ajaxDomain.setMessage(return_msg);
					return ajaxDomain;
				}
			}
			
		} catch (Exception e) {
			
			ajaxDomain.setStatusCode("300");
			ajaxDomain.setMessage(e.getMessage());
			return ajaxDomain;
		}
		
		if(StringUtils.isBlank(prepay_id)){
			
			ajaxDomain.setStatusCode("300");
			ajaxDomain.setMessage("未获取到预支付id.");
			return ajaxDomain;
		}
		
		//获取prepay_id后，拼接最后请求支付所需要的package
		
		SortedMap<String, String> finalpackage = new TreeMap<String, String>();
		
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
//		String nonce_str = DataUtil.createUniqueCode("WX", 29);
		
		String packages = "prepay_id="+prepay_id;
		finalpackage.put("appId", weChatConfig.getAppid());  
		finalpackage.put("timeStamp", timestamp); 
		
		finalpackage.put("nonceStr", nonce_str);  
		finalpackage.put("package", packages);  
		finalpackage.put("signType", weChatConfig.getSign_type());
		//要签名
		String finalsign = weChatConfig.createSign(finalpackage);
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		
		map.put("appId", weChatConfig.getAppid());  
		map.put("timeStamp", timestamp);  
		map.put("nonceStr", nonce_str);  
		map.put("package", packages);  
		map.put("signType", weChatConfig.getSign_type());
		map.put("paySign", finalsign);
		
		map.put("code_url", code_url);
		map.put("trade_type", trade_type);
		
		ajaxDomain.setStatusCode("200");
		ajaxDomain.setMessage("请求成功");
		ajaxDomain.setMap(map);
		
		return ajaxDomain;
	}
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	public static void main(String[] args) {
		
		AjaxDomain_pay ajaxDomain_pay = new PayHandlerForWechat().getPrepayInfo(new WeChatConfig());
		
		System.out.println(ajaxDomain_pay);
		
		try {
			CreatePngFile.createPngFile("/workSpace/pngFile/", "扫码支付.png", "weixin://wxpay/bizpayurl?pr=f50XDg5");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		WeChatConfig weChatConfig = new WeChatConfig();
		AjaxDomain_pay ajaxDomain = new AjaxDomain_pay();
		String detail = "{goods_detail:[{\"goods_id\":\"201611280801111\"," +
				"\"wxpay_goods_id\":\"201611280801111\",\"goods_name\":\"测试\"," +
				"\"quantity\":1,\"price\":0.01,\"goods_category\":\"123456\"," +
				"\"body\":\"测试0.1啊\"}]}";
		
		weChatConfig.setAppid("wx3d6d98ff776e3368");
		weChatConfig.setMch_id("1266962801");
		weChatConfig.setNonce_str("1234512345");
		weChatConfig.setBody("微信支付测试");
		weChatConfig.setAttach("test shop");
		weChatConfig.setOut_trade_no("20161128080111158");
		weChatConfig.setTotal_fee("0.01");
		
		Date now = new Date();
		Calendar cc = Calendar.getInstance();
		
		cc.setTime(now);
		cc.add(Calendar.MINUTE, 5);
		weChatConfig.setTime_start(sdf.format(now));
		weChatConfig.setTime_expire(sdf.format(cc.getTime()));
		weChatConfig.setGoods_tag("test");
		weChatConfig.setNotify_url("http://192.168.1.132:8080/wx_app_shop/wxmobile/afterWxPay/201611280801111.json");
		weChatConfig.setTrade_type("NATIVE");//NATIVE,JSAPI
		weChatConfig.setPartnerkey("0c8ce55163055c4da50a81e0a273468c");
		weChatConfig.setSpbill_create_ip("127.0.0.1");
		weChatConfig.setOpenid("oBhXst2ifGFRzIcuu9MoFiTeytzc");
		
		weChatConfig.setDetail(detail);
		PayHandlerForWechat payHandler = new PayHandlerForWechat();
		
		ajaxDomain = payHandler.getPrepayInfo(weChatConfig);
		
		System.out.println(ajaxDomain.getMessage());
	}
	
	
	
}
