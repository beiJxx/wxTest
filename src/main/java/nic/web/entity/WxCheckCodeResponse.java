/*
 * 文 件 名:  WxCheckCodeResponse.java
 * 版    权:  Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  james
 * 创建时间:  2016年5月24日
 */
package nic.web.entity;

import com.google.gson.annotations.SerializedName;

/**
 * <一句话功能简述>
 *  
 * @author  james
 * @version  [V1.00, 2016年5月24日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class WxCheckCodeResponse extends WxCardResponse{
	
	@SerializedName("openid")
	private String openid;
	
	@SerializedName("card_id")
	private String card_id;
	
	@SerializedName("begin_time")
	private String begin_time;
	
	/*
	 * 固定时长有效期会根据用户实际领取时间转换，
	 * 如用户2013年10月1日领取，固定时长有效期为90天，即有效时间为2013年10月1日-12月29日有效。
	 */
	@SerializedName("end_time")
	private String end_time;

	/*
	 * 当前code对应卡券的状态，
	 *  NORMAL    		 正常 
	 *  CONSUMED   		已核销 
	 *  EXPIRE     		已过期 
	 *  GIFTING 		转赠中
     *  GIFT_TIMEOUT 	转赠超时
     *  DELETE 			已删除
     *  UNAVAILABLE 	已失效
     *  code未被添加或被转赠领取的情况则统一报错：invalid serial code
	 */
	@SerializedName("user_card_status")
	private String user_card_status;

	@SerializedName("can_consume")
	private String can_consume;
	
	
}
