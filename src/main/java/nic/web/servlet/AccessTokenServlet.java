/*
 * 文 件 名:  AccessTokenServlet.java
 * 版    权:  Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  james
 * 创建时间:  2016年8月25日
 */
package nic.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import nic.web.common.AccessTokenInfo;
import nic.web.entity.AccessToken;
import nic.web.entity.CardApiTicket;
import nic.web.entity.JsApiTicket;
import nic.web.util.NetWorkHelper;

/**
 * <一句话功能简述>
 *  
 * @author  james
 * @version  [V1.00, 2016年8月25日]
 * @see  [相关类/方法]
 * @since V1.00
 */
@WebServlet(
		name = "AccessTokenServlet",
		urlPatterns = {"/AccessTokenServlet"},
		loadOnStartup = 1,
		initParams = {
				@WebInitParam(name = "appId",value = "wxdf737893056e23f5"),
				@WebInitParam(name = "appSecret",value = "d51745ddc6b999f09a4586beb8179a10")
		})
public class AccessTokenServlet extends HttpServlet{

	/**
	 * 重写方法
	 * @throws ServletException
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		System.out.println("启动WebServlet");
		super.init();

		final String appId = getInitParameter("appId");
		final String appSecret = getInitParameter("appSecret");
		
		new Thread(new Runnable() {
			public void run() {
				while(true){
					try {
						AccessTokenInfo.accessToken = getAccessToken(appId,appSecret);
						if(null != AccessTokenInfo.accessToken){
							Thread.sleep(7000*1000);
						}else{
							Thread.sleep(3*1000);
						}
					} catch (Exception e) {
						System.out.println("发生异常:" + e.getMessage());
						e.printStackTrace();
						try {
							Thread.sleep(1000*10);
						} catch (Exception e2) {
							// TODO: handle exception
						}
					}
					
					
				}
				
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				while(true){
					try {
						if(null == AccessTokenInfo.accessToken){
							Thread.sleep(3*1000);
						} else {
							AccessTokenInfo.cardApiTicket = getCardApiTicket();
							if (null != AccessTokenInfo.cardApiTicket) {
								Thread.sleep(7000 * 1000);
							} else {
								Thread.sleep(3 * 1000);
							}
						}
					} catch (Exception e) {
						System.out.println("发生异常:" + e.getMessage());
						e.printStackTrace();
						try {
							Thread.sleep(1000*10);
						} catch (Exception e2) {
							// TODO: handle exception
						}
					}
					
					
				}
				
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				while(true){
					try {
						if(null == AccessTokenInfo.accessToken){
							Thread.sleep(3*1000);
						}else{
							AccessTokenInfo.jsApiTicket = getJsApiTicket();
							if (null != AccessTokenInfo.jsApiTicket) {
								Thread.sleep(7000 * 1000);
							} else {
								Thread.sleep(3 * 1000);
							}
						}
					} catch (Exception e) {
						System.out.println("发生异常:" + e.getMessage());
						e.printStackTrace();
						try {
							Thread.sleep(1000*10);
						} catch (Exception e2) {
							// TODO: handle exception
						}
					}
					
					
				}
				
			}
		}).start();
	}
	private AccessToken getAccessToken(String appId, String appSecret) {
		NetWorkHelper netHelper = new NetWorkHelper();
		
		String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
		
		String Url = String.format(accessTokenUrl, appId,appSecret);
		String result = netHelper.getHttpsResponse(Url, "");
		
		System.out.println("获取到的accessToken:" + result);
		
		JSONObject json = JSON.parseObject(result);
		AccessToken token = new AccessToken();
		token.setAccessToken(json.getString("access_token"));
		token.setExpiresin(json.getInteger("expires_in"));
		return token;
	}
	
	private JsApiTicket getJsApiTicket(){
		NetWorkHelper netHelper = new NetWorkHelper();
		String jsApi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";
		
		
		String Url = String.format(jsApi_ticket_url,AccessTokenInfo.accessToken.getAccessToken());
		String result = netHelper.getHttpsResponse(Url, "");
		
		System.out.println("获取到的jsApiTicket:" + result);
		
		JSONObject json = JSON.parseObject(result);
		JsApiTicket ticket = new JsApiTicket();
		ticket.setTicket(json.getString("ticket"));
		ticket.setExpiresIn(json.getInteger("expires_in"));
		return ticket;
	}
	
	private CardApiTicket getCardApiTicket(){
		NetWorkHelper netHelper = new NetWorkHelper();
		String cardApi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=wx_card";
		
		String Url = String.format(cardApi_ticket_url,AccessTokenInfo.accessToken.getAccessToken());
		String result = netHelper.getHttpsResponse(Url, "");
		
		System.out.println("获取到的cardApiTicket:" + result);
		
		JSONObject json = JSON.parseObject(result);
		CardApiTicket ticket = new CardApiTicket();
		ticket.setTicket(json.getString("ticket"));
		ticket.setExpiresIn(json.getInteger("expires_in"));
		return ticket;
	}
	
}
