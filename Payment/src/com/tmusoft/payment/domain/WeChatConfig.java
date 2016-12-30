/**
 * 
 */
package com.tmusoft.payment.domain;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.tmusoft.payment.util.MD5Util;

/**
 * @author wumeng
 *
 */
public class WeChatConfig {
	
	
	//微信支付商户开通后 微信会提供appid和appsecret和商户号partner
	private String appid = ""; //微信开放平台审核通过的应用APPID
	private String mch_id = ""; //商户号
	
	private String device_info = "WEB"; //终端设备号(门店号或收银设备ID)，默认请传"WEB"
	
	private String nonce_str; //随机字符串，不长于32位
	
	private String sign;// 签名
	
	private String sign_type = "MD5"; //签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
	
	private String body ; //商品描述交易字段格式根据不同的应用场景按照以下格式：APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
	
	/**
	 * {
"
goods_detail":[
{
"goods_id":"iphone6s_16G",
"wxpay_goods_id":"1001",
"goods_name":"iPhone6s 16G",
"quantity":1,
"price":528800,
"goods_category":"123456",
"body":"苹果手机"
},
{
"goods_id":"iphone6s_32G",
"wxpay_goods_id":"1002",
"goods_name":"iPhone6s 32G",
"quantity":1,
"price":608800,
"goods_category":"123789",
"body":"苹果手机"
}
]
}
商品详细列表，使用Json格式，传输签名前请务必使用CDATA标签将JSON文本串保护起来。
goods_detail 服务商必填 []：
└ goods_id String 必填 32 商品的编号
└ wxpay_goods_id String 可选 32 微信支付定义的统一商品编号
└ goods_name String 必填 256 商品名称
└ quantity Int 必填 商品数量
└ price Int 必填 商品单价，单位为分
└ goods_category String 可选 32 商品类目ID
└ body String 可选 1000 商品描述信息
	 */
	private String detail;//商品详情
	
	private String attach;//附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据:如 深圳分店
	
	private String out_trade_no;//商户系统内部的订单号,32个字符内、可包含字母
	
	private String fee_type = "CNY";//符合ISO 4217标准的三位字母代码，默认人民币：CNY
	
	private String total_fee; //订单总金额，单位为分
	
	private String spbill_create_ip; //用户端实际ip
	
	
	private String time_start;//订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010
	
	private String time_expire;//订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010;注意：最短失效时间间隔必须大于5分钟
	
	private String goods_tag;//商品标记，代金券或立减优惠功能的参数;如:WXG
	
	private String notify_url;//接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
	
	private String trade_type; //交易类型  如：JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
								//MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
	
	private String limit_pay; //指定支付方式;如no_credit--指定不能使用信用卡支付
	
	//这个参数partnerkey是在商户后台配置的一个32位的key,微信商户平台-账户设置-安全设置-api安全
	private String partnerkey = "";
	
	private String charset = "UTF-8";
	
	private String openid = "";
	
	
	
	
	public String getOpenid() {
		return openid;
	}


	public void setOpenid(String openid) {
		this.openid = openid;
	}


	/**
	 * 校验信息是否合法
	 * @return
	 */
	public AjaxDomain_pay infoIsPass(){
		
		AjaxDomain_pay ajaxDomain_pay = new AjaxDomain_pay();
		
		StringBuffer sb = new StringBuffer();
		
		
		if(StringUtils.isBlank(this.getAppid())){
			
			sb.append("appid is not null;");
		}
		if(StringUtils.isBlank(this.getMch_id())){
			
			sb.append("Mch_id is not null;");
		}
		if(StringUtils.isBlank(this.getNonce_str())){
			
			sb.append("Nonce_str is not null;");
			
		}
		//!StringUtils.equalsIgnoreCase("HMAC-SHA256", this.getSign_type())&&
		if(!StringUtils.equalsIgnoreCase("MD5", this.getSign_type())){
			
			sb.append("Sign_type is illegal;");
		}else{
			this.setSign_type(this.sign_type.toUpperCase());
		}
		if(StringUtils.isBlank(this.getBody())){
			
			sb.append("Body is illegal;");
		}
		
		if(StringUtils.isNotBlank(this.getDetail())){
			
			if(this.getDetail().indexOf("goods_detail")==-1){
				
				sb.append("Detail's goods_detail is not null;");
			}else if(this.getDetail().indexOf("goods_name")==-1){
				
				sb.append("Detail's goods_name is not null;");
			}else if(this.getDetail().indexOf("quantity")==-1){
				
				sb.append("Detail's quantity is not null;");
			}else if(this.getDetail().indexOf("price")==-1){
				
				sb.append("Detail's price is not null;");
			}
			
		}
		if(StringUtils.isBlank(this.getOut_trade_no())){
			
			sb.append("out_trade_no is not null;");
		}
		
		if(StringUtils.isBlank(this.getTotal_fee())||Double.valueOf(this.getMoney(this.getTotal_fee()))<=0){
			
			sb.append("Total_fee is illegal;");
			
		}
		if(StringUtils.isBlank(this.getSpbill_create_ip())){
			
			sb.append("Spbill_create_ip is not null;");
		}
		
		if(StringUtils.isBlank(this.getNotify_url())){
			
			sb.append("Notify_url is not null;");
		}else if(this.getNotify_url().indexOf("?")!=-1||
				this.getNotify_url().indexOf("&")!=-1){
			
			sb.append("Notify_url is illegal;");
			
		}
		if(StringUtils.isBlank(this.getTrade_type())){
			
			sb.append("Trade_type is not null;");
		}else{
			
			if(StringUtils.equalsIgnoreCase("JSAPI", this.getTrade_type())){
				
				if(StringUtils.isBlank(this.getOpenid())){
					
					sb.append("JSAPI支付必须传openid");
				}
				
			}
		}
		
		if(StringUtils.isBlank(this.getPartnerkey())){
			
			sb.append("Partnerkey is not null;");
		}
		
		if(StringUtils.isNotBlank(sb.toString())){
			
			ajaxDomain_pay.setStatusCode("300");
			ajaxDomain_pay.setMessage(sb.toString());
			
		}else{
			ajaxDomain_pay.setStatusCode("200");
		}
		
		return ajaxDomain_pay;
	}
	
	
    public String getAppid() {
		return appid;
	}




	public void setAppid(String appid) {
		this.appid = appid;
	}




	public String getMch_id() {
		return mch_id;
	}




	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}




	public String getDevice_info() {
		return device_info;
	}




	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}




	public String getNonce_str() {
		return nonce_str;
	}




	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}




	public String getSign() {
		return sign;
	}




	public void setSign(String sign) {
		this.sign = sign;
	}




	public String getSign_type() {
		return sign_type;
	}




	public void setSign_type(String sign_type) {
		
		this.sign_type = sign_type;
	}




	public String getBody() {
		return body;
	}




	public void setBody(String body) {
		this.body = body;
	}




	public String getDetail() {
		return detail;
	}




	public void setDetail(String detail) {
		this.detail = detail;
	}




	public String getAttach() {
		return attach;
	}




	public void setAttach(String attach) {
		this.attach = attach;
	}




	public String getOut_trade_no() {
		return out_trade_no;
	}




	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}




	public String getFee_type() {
		return fee_type;
	}




	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}




	public String getTotal_fee() {
		return total_fee;
	}




	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}




	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}




	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}




	public String getTime_start() {
		return time_start;
	}




	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}




	public String getTime_expire() {
		return time_expire;
	}




	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}




	public String getGoods_tag() {
		return goods_tag;
	}




	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}




	public String getNotify_url() {
		return notify_url;
	}




	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}




	public String getTrade_type() {
		return trade_type;
	}




	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}




	public String getLimit_pay() {
		return limit_pay;
	}




	public void setLimit_pay(String limit_pay) {
		this.limit_pay = limit_pay;
	}




	public String getPartnerkey() {
		return partnerkey;
	}




	public void setPartnerkey(String partnerkey) {
		this.partnerkey = partnerkey;
	}




	public String getCharset() {
		return charset;
	}




	public void setCharset(String charset) {
		this.charset = charset;
	}




	/**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 */
	public String createSign(SortedMap<String, String> packageParams) {
		
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v.trim()) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key="+this.getPartnerkey());
		
		String sign = MD5Util.MD5Encode(sb.toString(), this.charset)
				.toUpperCase();
		return sign;

	}

	/**
	 * @param amount
	 * 金额转换为分
	 * @return
	 */
	public static String getMoney(String amount) {
		if(amount==null){
			return "";
		}
		// 金额转化为分为单位
		String currency =  amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额  
        int index = currency.indexOf(".");  
        int length = currency.length();  
        Long amLong = 0l;  
        if(index == -1){  
            amLong = Long.valueOf(currency+"00");  
        }else if(length - index >= 3){  
            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));  
        }else if(length - index == 2){  
            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);  
        }else{  
            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");  
        }  
        return amLong.toString(); 
	}
	
	public String createPrepayXml(){
		
		
		StringBuffer xmlSb = new StringBuffer("<xml encoding='"+this.charset+"' >");
		
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		
		if(StringUtils.isNotBlank(this.getAppid())){
			
			packageParams.put("appid", this.getAppid());
			xmlSb.append("<appid>" + this.getAppid() + "</appid>"  );
		}
		
		if(StringUtils.isNotBlank(this.getMch_id())){
			
			packageParams.put("mch_id",this.getMch_id());
			xmlSb.append( "<mch_id>"+ this.getMch_id() + "</mch_id>");
			
		}
		
		if(StringUtils.isNotBlank(this.getDevice_info())){
			
			packageParams.put("device_info",this.getDevice_info());
			xmlSb.append("<device_info>"+ this.getDevice_info() + "</device_info>" );
		}
		if(StringUtils.isNotBlank(this.getNonce_str())){
			
			packageParams.put("nonce_str",this.getNonce_str());
			xmlSb.append("<nonce_str>" + this.getNonce_str()+ "</nonce_str>");
			
		}
		if(StringUtils.isNotBlank(this.getSign_type())){
			
			packageParams.put("sign_type",this.getSign_type());
			xmlSb.append("<sign_type>" + this.getSign_type() + "</sign_type>");
			
		}
		if(StringUtils.isNotBlank(this.getBody())){
			
			packageParams.put("body",this.getBody());
			xmlSb.append("<body>" + this.getBody() + "</body>");
		}
		if(StringUtils.isNotBlank(this.getDetail())){
			
			packageParams.put("detail",this.getDetail());
			xmlSb.append("<detail><![CDATA[" + this.getDetail() + "]]></detail>");
			
		}
		if(StringUtils.isNotBlank(this.getAttach())){
			
			packageParams.put("attach",this.getAttach());
			
			xmlSb.append("<attach>" + this.getAttach() + "</attach>");
		}
		if(StringUtils.isNotBlank(this.getOut_trade_no())){
			
			packageParams.put("out_trade_no",this.getOut_trade_no());
			xmlSb.append("<out_trade_no>" + this.getOut_trade_no() + "</out_trade_no>");
		}
		if(StringUtils.isNotBlank(this.getFee_type())){
			
			packageParams.put("fee_type",this.getFee_type());
			xmlSb.append("<fee_type>" + this.getFee_type() + "</fee_type>");
		}
		if(StringUtils.isNotBlank(this.getTotal_fee())){
			
			packageParams.put("total_fee",this.getMoney(this.getTotal_fee()));
			xmlSb.append("<total_fee>" + this.getMoney(this.getTotal_fee()) + "</total_fee>");
		}
		if(StringUtils.isNotBlank(this.getSpbill_create_ip())){
			
			packageParams.put("spbill_create_ip",this.getSpbill_create_ip());
			xmlSb.append("<spbill_create_ip>" + this.getSpbill_create_ip() + "</spbill_create_ip>");
		}
		if(StringUtils.isNotBlank(this.getTime_start())){
			
			packageParams.put("time_start",this.getTime_start());
			xmlSb.append("<time_start>" + this.getTime_start() + "</time_start>");
		}
		if(StringUtils.isNotBlank(this.getTime_expire())){
			
			packageParams.put("time_expire",this.getTime_expire());
			xmlSb.append("<time_expire>" + this.getTime_expire() + "</time_expire>");
		}
		if(StringUtils.isNotBlank(this.getGoods_tag())){
			
			packageParams.put("goods_tag",this.getGoods_tag());
			xmlSb.append("<goods_tag>" + this.getGoods_tag() + "</goods_tag>");
		}
		if(StringUtils.isNotBlank(this.getNotify_url())){
			
			packageParams.put("notify_url",this.getNotify_url());
			xmlSb.append("<notify_url>" + this.getNotify_url() + "</notify_url>");
			
		}
		if(StringUtils.isNotBlank(this.getTrade_type())){
			
			packageParams.put("trade_type",this.getTrade_type());
			xmlSb.append("<trade_type>" + this.getTrade_type() + "</trade_type>");
		}
		if(StringUtils.isNotBlank(this.getLimit_pay())){
			
			packageParams.put("limit_pay",this.getLimit_pay());
			xmlSb.append("<limit_pay>" + this.getTrade_type() + "</limit_pay>");
		}
		if(StringUtils.isNotBlank(this.getOpenid())){
			
			packageParams.put("openid",this.getOpenid());
			xmlSb.append("<openid>" + this.getOpenid()+ "</openid>");
		}
		
		String sign = this.createSign(packageParams);
		xmlSb.append("<sign>" + sign + "</sign>");
		xmlSb.append("</xml>");
		System.out.println(xmlSb.toString());
		return xmlSb.toString();
	}
}
