/*
 * 文 件 名:  MessageBuildUtil.java
 * 版    权:  Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  james
 * 创建时间:  2016年8月25日
 */
package nic.web.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import nic.web.entity.Music;
import nic.web.entity.NewsItem;
import nic.web.entity.Video;

/**
 * <一句话功能简述>
 *  
 * @author  james
 * @version  [V1.00, 2016年8月25日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class MessageBuildUtil {

	private final static String xml = "<xml>";
	private final static String endXml = "</xml>";
	private final static String toUserName = "<ToUserName><![CDATA[%s]]></ToUserName>";
	private final static String fromUserName = "<FromUserName><![CDATA[%s]]></FromUserName>";
	private final static String createTime = "<CreateTime>%s</CreateTime>";
	private final static String msgType = "<MsgType><![CDATA[%s]]></MsgType>";
	private final static String content = "<Content><![CDATA[%s]]></Content>";
	private final static String image = "<Image><MediaId><![CDATA[%s]]></MediaId></Image>";
	private final static String musicXml = "<Music>" +
												"	<Title><![CDATA[%s]]></Title>" +
												"	<Description><![CDATA[%s]]></Description>" +
												"	<MusicUrl><![CDATA[%s]]></MusicUrl>" +
												"	<HQMusicUrl><![CDATA[%s]]></HQMusicUrl>" +
											"</Music>";
	
	private final static String videoXml = "<Video>" +
						                        "   <MediaId><![CDATA[%s]]></MediaId>" +
						                        "   <Title><![CDATA[%s]]></Title>" +
						                        "   <Description><![CDATA[%s]]></Description>" +
			                         		"</Video>";
	private final static String voiceXml = "<Voice>" +
			                         			"   <MediaId><![CDATA[%s]]></MediaId>" +
			                         		"</Voice>";
	
	
	
	public static String buildTextMessage(Map<String,String> map,String contentStr){
		
		String fromUserNameStr = map.get("FromUserName");
		String toUserNameStr = map.get("ToUserName");
		
		return String.format(xml + toUserName + fromUserName + createTime + msgType + content + endXml,
				fromUserNameStr, toUserNameStr, getMessageCreateTime(), "text", contentStr);
	}
	
	public static String buildImageMessage(Map<String,String> map,String meidaId){
		String fromUserNameStr = map.get("FromUserName");
		String toUserNameStr = map.get("ToUserName");
		return String.format(xml + toUserName + fromUserName + createTime + msgType + image + endXml,
				fromUserNameStr, toUserNameStr, getMessageCreateTime(), "image", meidaId);
	}
	
	public static String buildMusicMessage(Map<String,String> map,Music music){
		String fromUserNameStr = map.get("FromUserName");
		String toUserNameStr = map.get("ToUserName");
		return String.format(xml + toUserName + fromUserName + createTime + msgType + musicXml + endXml,
				fromUserNameStr, toUserNameStr, getMessageCreateTime(), "music",
				music.getTitle(),music.getDescription(),music.getMusicUrl(),music.getHqMusicUrl());
	}
	
	public static String buildVideoMessage(Map<String,String> map,Video video){
		String fromUserNameStr = map.get("FromUserName");
		String toUserNameStr = map.get("ToUserName");
		return String.format(xml + toUserName + fromUserName + createTime + msgType + videoXml + endXml,
				fromUserNameStr, toUserNameStr, getMessageCreateTime(), "video",
				video.getMediaId(),video.getTitle(),video.getDescription());
	}
	
	public static String buildVoiceMessage(Map<String,String> map,String mediaId){
		String fromUserNameStr = map.get("FromUserName");
		String toUserNameStr = map.get("ToUserName");
		return String.format(xml + toUserName + fromUserName + createTime + msgType + voiceXml + endXml,
				fromUserNameStr, toUserNameStr, getMessageCreateTime(), "voice",mediaId);
	}
	
	/**
     * 构造图文消息
     * @param map 封装了解析结果的Map
     * @return 图文消息XML字符串
     */
    public static String buildNewsMessage(Map<String, String> map) {
        String fromUserName = map.get("FromUserName");
        // 开发者微信号
        String toUserName = map.get("ToUserName");
        NewsItem item = new NewsItem();
        item.setTitle("微信开发学习总结（一）——微信开发环境搭建");
        item.setDescription("工欲善其事，必先利其器。要做微信公众号开发，那么要先准备好两样必不可少的东西：\n" +
                "\n" +
                "　　1、要有一个用来测试的公众号。\n" +
                "\n" +
                "　　2、用来调式代码的开发环境");
        item.setPicUrl("http://images2015.cnblogs.com/blog/289233/201601/289233-20160121164317343-2145023644.png");
        item.setUrl("http://www.cnblogs.com/xdp-gacl/p/5149171.html");
        String itemContent1 = buildSingleItem(item);

        NewsItem item2 = new NewsItem();
        item2.setTitle("微信开发学习总结（二）——微信开发入门");
        item2.setDescription("微信服务器就相当于一个转发服务器，终端（手机、Pad等）发起请求至微信服务器，微信服务器然后将请求转发给我们的应用服务器。应用服务器处理完毕后，将响应数据回发给微信服务器，微信服务器再将具体响应信息回复到微信App终端。");
        item2.setPicUrl("");
        item2.setUrl("http://www.cnblogs.com/xdp-gacl/p/5151857.html");
        String itemContent2 = buildSingleItem(item2);


        String content = String.format("<xml>\n" +
                "<ToUserName><![CDATA[%s]]></ToUserName>\n" +
                "<FromUserName><![CDATA[%s]]></FromUserName>\n" +
                "<CreateTime>%s</CreateTime>\n" +
                "<MsgType><![CDATA[news]]></MsgType>\n" +
                "<ArticleCount>%s</ArticleCount>\n" +
                "<Articles>\n" + "%s" +
                "</Articles>\n" +
                "</xml> ", fromUserName, toUserName, getMessageCreateTime(), 2, itemContent1 + itemContent2);
        return content;

    }

    /**
     * 生成图文消息的一条记录
     *
     * @param item
     * @return
     */
    private static String buildSingleItem(NewsItem item) {
        String itemContent = String.format("<item>\n" +
                "<Title><![CDATA[%s]]></Title> \n" +
                "<Description><![CDATA[%s]]></Description>\n" +
                "<PicUrl><![CDATA[%s]]></PicUrl>\n" +
                "<Url><![CDATA[%s]]></Url>\n" +
                "</item>", item.getTitle(), item.getDescription(), item.getPicUrl(), item.getUrl());
        return itemContent;
    }
	
	

	/** 
	 * <一句话功能简述>
	 * <功能详细描述>
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	private static String getMessageCreateTime() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String nowTime = df.format(date);
		return nowTime;
	}
	
}
