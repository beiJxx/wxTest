/*
 * 文 件 名:  WxWhiteList.java
 * 版    权:  Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  james
 * 创建时间:  2016年5月18日
 */
package nic.web.entity;

import java.util.List;

/**
 * <一句话功能简述>
 *  
 * @author  james
 * @version  [V1.00, 2016年5月18日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class WxWhiteList {
	private List<String> openid;
	private List<String> username;
	/**
	 * 获取 openid
	 * @return 返回 openid
	 */
	public List<String> getOpenid() {
		return openid;
	}
	/**
	 * 设置 openid
	 * @param 对openid进行赋值
	 */
	public void setOpenid(List<String> openid) {
		this.openid = openid;
	}
	/**
	 * 获取 username
	 * @return 返回 username
	 */
	public List<String> getUsername() {
		return username;
	}
	/**
	 * 设置 username
	 * @param 对username进行赋值
	 */
	public void setUsername(List<String> username) {
		this.username = username;
	}

	
	
}
