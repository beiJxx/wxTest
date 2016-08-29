/*
 * 文 件 名:  NewsItem.java
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
public class NewsItem {
	private String title;
	private String description;
	private String picUrl;
	private String url;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	

}
