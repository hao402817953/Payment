/**
 * 
 */
package com.tmusoft.payment.domain;

import org.apache.commons.lang3.StringUtils;

import com.tmusoft.payment.util.DataUtil;

/**
 * @author wumeng
 *
 */
/**
 * @author wumeng
 *
 */
public class WeChatDomain_old {

	
	private String spbillCreateIp;//	用户端实际ip
	private String notifyUrl;//这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等
	private String body;// 商品描述根据情况修改
	private String openId;//微信用户对一个公众号唯一
	
	// 附加数据 原样返回
	private	String attach = "";
	
	
	public final static String TRADE_TYPE = "JSAPI";
	
	
	//随机字符串
	//private String nonce_str;
	
	// 商户订单号
	private String out_trade_no;
	
	
	private String device_info;//设备号
	
	private String detail;//商品详情
	
	private String fee_type; //货币类型
	private String totalFee;//金额
	
	
	/**
	 * 符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见
	 * @return
	 */
	public String getFee_type() {
		return fee_type;
	}

	/**
	 * 符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见
	 * @param fee_type
	 */
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	/**
	 * 获取商品详情
	 * @return
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * 设置商品详情（）
	 * {
"goods_detail":[
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
	 * @param detail
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public boolean checkInfo(){
		
		boolean flg = true;
		
		if(StringUtils.isBlank(totalFee)||StringUtils.isBlank(notifyUrl)||StringUtils.isBlank(openId)
				||StringUtils.isBlank(out_trade_no)||StringUtils.isBlank(body)){
			
			flg &= false;
		}else{
			
			if(!DataUtil.checkDouble(totalFee)||Double.valueOf(totalFee)<=0){
				
				flg &= false;
			}
			
		}
		
		
		return flg;
	}
	
	/**
	 * 附加数据 原样返回
	 */
	public String getAttach() {
		return attach;
	}

	/**
	 * 附加数据 原样返回
	 * 附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
	 */
	public void setAttach(String attach) {
		this.attach = attach;
	}


	public String getOut_trade_no() {
		return out_trade_no;
	}

	/**
	 * 订单号
	 */
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}


	public String getTotalFee() {
		return totalFee;
	}
	
	/**
	 * 金额
	 */
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	
	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}
	
	/**
	 * 	用户端实际ip
	 */
	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	
	/**
	 * 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等
	 */
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getBody() {
		return body;
	}
	
	/**
	 * 商品描述根据情况修改
	 */
	public void setBody(String body) {
		this.body = body;
	}
	public String getOpenId() {
		return openId;
	}
	
	/**
	 * 用户openid
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
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
	

	
}
