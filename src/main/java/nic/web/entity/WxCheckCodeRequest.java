/*
 * 文 件 名:  CheckCodeRequest.java
 * 版    权:  Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  james
 * 创建时间:  2016年5月24日
 */
package nic.web.entity;

import nic.web.util.JSONObject;

/**
 * <一句话功能简述>
 *  
 * @author  james
 * @version  [V1.00, 2016年5月24日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class WxCheckCodeRequest{

	
	JSONObject m_data;
	
	/** 
	 * <默认构造函数>
	 */
	public WxCheckCodeRequest() {
		m_data = new JSONObject();
	}
	
	public String toJsonString() {
		return m_data.toString();
	}

	public String toString() {
		return toJsonString();
	}
	
	public void setCardId(String cardId){
		m_data.put("card_id", cardId);
	}
	public String getCardId(){
		return m_data.optString("card_id");
	}
	
	public void setCode(String code){
		m_data.put("code", code);
	}
	public String getCode(){
		return m_data.optString("code");
	}
	
	public void setCheckCousume(boolean checkCousume){
		m_data.put("check_consume", checkCousume);
	}
	public boolean getCheckCousume(){
		return m_data.optBoolean("check_consume");
	}
	
}
