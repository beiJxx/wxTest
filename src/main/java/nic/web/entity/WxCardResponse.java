/*
 * 文 件 名:  WxCardResponse.java
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
public class WxCardResponse {

	@SerializedName(value="errcode")
	private String errCode;

	@SerializedName(value="errmsg")
	private String errMsg;

	/**
	 * 获取 errCode
	 * @return 返回 errCode
	 */
	public String getErrCode() {
		return errCode;
	}

	/**
	 * 设置 errCode
	 * @param 对errCode进行赋值
	 */
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	/**
	 * 获取 errMsg
	 * @return 返回 errMsg
	 */
	public String getErrMsg() {
		return errMsg;
	}

	/**
	 * 设置 errMsg
	 * @param 对errMsg进行赋值
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	
	
}
