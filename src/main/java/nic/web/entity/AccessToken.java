/*
 * 文 件 名:  AccessToken.java
 * 版    权:  Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  james
 * 创建时间:  2016年8月25日
 */
package nic.web.entity;

/**
 * <一句话功能简述>
 *  
 * @author  james
 * @version  [V1.00, 2016年8月25日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class AccessToken {

	private String accessToken;
	
	private int expiresin;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresin() {
		return expiresin;
	}

	public void setExpiresin(int expiresin) {
		this.expiresin = expiresin;
	}
	
}
