/*
 * 文 件 名:  WxCardResponse.java
 * 版    权:  Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  james
 * 创建时间:  2016年4月29日
 */
package nic.web.entity;

import com.google.gson.annotations.SerializedName;

/**
 * <一句话功能简述>
 *  
 * @author  james
 * @version  [V1.00, 2016年4月29日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class WxCreateCardResponse extends WxCardResponse{
	
	@SerializedName(value="card_id")
	private String card_id;

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
