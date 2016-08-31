/*
 * 文 件 名:  WxUserCardRequest.java
 * 版    权:  Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  james
 * 创建时间:  2016年5月25日
 */
package nic.web.entity;

import com.google.gson.annotations.SerializedName;

/**
 * <一句话功能简述>
 *  
 * @author  james
 * @version  [V1.00, 2016年5月25日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class WxUserCardRequest {
	
	@SerializedName("openid")
	private String openid;
	
	@SerializedName("card_id")
	private String card_id;

	/**
	 * 获取 openid
	 * @return 返回 openid
	 */
	public String getOpenid() {
		return openid;
	}

	/**
	 * 设置 openid
	 * @param 对openid进行赋值
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/**
	 * 获取 card_id
	 * @return 返回 card_id
	 */
	public String getCard_id() {
		return card_id;
	}

	/**
	 * 设置 card_id
	 * @param 对card_id进行赋值
	 */
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	
	
}
