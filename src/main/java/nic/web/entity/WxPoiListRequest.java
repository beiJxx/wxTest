/*
 * 文 件 名:  WxPoiListRequest.java
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
public class WxPoiListRequest {

	JSONObject m_data;
	
	/** 
	 * <默认构造函数>
	 */
	public WxPoiListRequest() {
		m_data = new JSONObject();
	}
	
	public String toJsonString() {
		return m_data.toString();
	}

	public String toString() {
		return toJsonString();
	}
	
	/*
	 * 	开始位置，0 即为从第一条开始查询
	 */
	public void setBegin(Integer begin) {
		m_data.put("begin", begin);
	}

	public int getBegin() {
		return m_data.optInt("begin");
	}
	
	
	
	/*
	 * 	返回数据条数，最大允许50，默认为20
	 */
	public void setLimit(Integer limit){
		m_data.put("limit", limit);
	}
	public int getLimit(){
		return m_data.optInt("limit");
	}
}
