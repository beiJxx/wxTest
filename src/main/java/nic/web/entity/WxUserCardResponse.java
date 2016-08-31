/*
 * 文 件 名:  WxUserCardResponse.java
 * 版    权:  Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  james
 * 创建时间:  2016年5月25日
 */
package nic.web.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * <一句话功能简述>
 *  
 * @author  james
 * @version  [V1.00, 2016年5月25日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class WxUserCardResponse extends WxCardResponse{

	@SerializedName("card_list")
	private List<WxUserCard> card_list;
	
	
	
	/**
	 * 获取 card_list
	 * @return 返回 card_list
	 */
	public List<WxUserCard> getCard_list() {
		return card_list;
	}



	/**
	 * 设置 card_list
	 * @param 对card_list进行赋值
	 */
	public void setCard_list(List<WxUserCard> card_list) {
		this.card_list = card_list;
	}



	public static class WxUserCard{
		@SerializedName("code")
		private String code;
		
		@SerializedName("card_id")
		private String card_id;

		/**
		 * 获取 code
		 * @return 返回 code
		 */
		public String getCode() {
			return code;
		}

		/**
		 * 设置 code
		 * @param 对code进行赋值
		 */
		public void setCode(String code) {
			this.code = code;
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
	
}
