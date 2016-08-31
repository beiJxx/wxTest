/*
 * 文 件 名:  MessageHandlerUtil.java
 * 版    权:  Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  james
 * 创建时间:  2016年8月25日
 */
package nic.web.util;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import nic.web.entity.Music;
import nic.web.entity.Video;

/**
 * <一句话功能简述>
 *  
 * @author  james
 * @version  [V1.00, 2016年8月25日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class MessageHandlerUtil {

	public static Map<String,String> parseXml(HttpServletRequest request) throws Exception {
		Map<String,String> map = new HashMap();
		InputStream inputStream = request.getInputStream();
		System.out.println("获取输入流");
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();
		for(Element e : elementList){
			System.out.println(e.getName() + "|" + e.getText());
			map.put(e.getName(), e.getText());
		}
		
		inputStream.close();
		inputStream = null;
		return map;
	}
	
	//构造消息类型，构造返回消息
	public static String buildXml(Map<String,String> map){
		String result;
		String msgType = map.get("MsgType").toString();
		System.out.println("MsgType:" + msgType);
		if(msgType.toUpperCase().equals("TEXT")){
			result = buildTextMessage(map,"hello world");
		}else{
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			result = String.format(
					"<xml>" + 
							"<ToUserName><![CDATA[%s]]></ToUserName>" +
							"<FromUserName><![CDATA[%s]]></FromUserName>" +
							"<CreateTime>%s</CreateTime>" +
							"<MsgType><![CDATA[text]]></MsgType>" +
							"<Content><![CDATA[%s]]></Content>" +
					"</xml>",
					fromUserName,toUserName,getUtcTime(),
					"请回复如下关键字:\n文本\n图片\n语音\n视频\n音乐\n图文"
					);
		}
		return result;
		
	}
	
	public static String buildWelcomeXml(Map<String,String> map){
		String fromUserName = map.get("FromUserName");
		String toUserName = map.get("ToUserName");
		String result = String.format(
				"<xml>" + 
						"<ToUserName><![CDATA[%s]]></ToUserName>" +
						"<FromUserName><![CDATA[%s]]></FromUserName>" +
						"<CreateTime>%s</CreateTime>" +
						"<MsgType><![CDATA[text]]></MsgType>" +
						"<Content><![CDATA[%s]]></Content>" +
				"</xml>",
				fromUserName,toUserName,getUtcTime(),
				"请回复如下关键字:\n文本\n图片\n语音\n视频\n音乐\n图文"
				);
		return result;
	}

	/** 
	 * <一句话功能简述>
	 * <功能详细描述>
	 * @param map
	 * @param string
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	private static String buildTextMessage(Map<String, String> map, String content) {
		String fromUserName = map.get("FromUserName");
		String toUserName = map.get("ToUserName");
		
		return String.format(
				"<xml>" + 
						"<ToUserName><![CDATA[%s]]></ToUserName>" +
						"<FromUserName><![CDATA[%s]]></FromUserName>" +
						"<CreateTime>%s</CreateTime>" +
						"<MsgType><![CDATA[text]]></MsgType>" +
						"<Content><![CDATA[%s]]></Content>" +
				"</xml>",
				fromUserName,toUserName,getUtcTime(),content);
	}
	
	public static String buildResponseMessage(Map<String,String> map){
		String responseMessage = "";
		String msgType = map.get("MsgType").toString();
		System.out.println("MsgType:" + msgType);
		MessageType messageEnumType = MessageType.valueOf(MessageType.class,msgType.toUpperCase());
		switch (messageEnumType) {
		case TEXT:
			// 处理文本消息
			responseMessage = handleTextMessage(map);
			break;
		case IMAGE:
			// 处理图片消息
			//responseMessage = handleImageMessage(map);
			break;
		case VOICE:
			// 处理语音消息
			//responseMessage = handleVoiceMessage(map);
			break;
		case VIDEO:
			// 处理视频消息
			//responseMessage = handleVideoMessage(map);
			break;
		case SHORTVIDEO:
			// 处理小视频消息
			//responseMessage = handleSmallVideoMessage(map);
			break;
		case LOCATION:
			// 处理位置消息
			//responseMessage = handleLocationMessage(map);
			break;
		case LINK:
			// 处理链接消息
			//responseMessage = handleLinkMessage(map);
			break;
		case EVENT:
			// 处理事件消息,用户在关注与取消关注公众号时，微信会向我们的公众号服务器发送事件消息,开发者接收到事件消息后就可以给用户下发欢迎消息
			//responseMessage = handleEventMessage(map);
		default:
			break;
		}
		 return responseMessage;
	}
	
	/**
     * 接收到文本消息后处理
     * @param map 封装了解析结果的Map
     * @return
     */
    private static String handleTextMessage(Map<String, String> map) {
        //响应消息
        String responseMessage = null;
        // 消息内容
        String content = map.get("Content");
        switch (content) {
            case "文本":
                String msgText = "孤傲苍狼又要开始写博客总结了,欢迎朋友们访问我在博客园上面写的博客\n" +
                        "<a href=\"http://www.cnblogs.com/xdp-gacl\">孤傲苍狼的博客</a>";
                responseMessage = buildTextMessage(map, msgText);
                break;
            case "图片":
                //通过素材管理接口上传图片时得到的media_id
                String imgMediaId = "QVUrYbINRtpvN2ugKn7wOt7glKgGPmD5k8KigcAddZRELtx9CbDzJHkDgp2f-r-y";
                responseMessage = MessageBuildUtil.buildImageMessage(map, imgMediaId);
                break;
            case "语音":
                //通过素材管理接口上传语音文件时得到的media_id
                String voiceMediaId = "tTOs7P-0HQvhwnZvUJUb2oNOAOCDo2gkD_yRAKP5YgCazVZ2FUCwvwNQRnvwgdpO";
                responseMessage = MessageBuildUtil.buildVoiceMessage(map,voiceMediaId);
                break;
            case "图文":
                responseMessage = MessageBuildUtil.buildNewsMessage(map);
                break;
            case "音乐":
                Music music = new Music();
                music.setTitle("赵丽颖、许志安 - 乱世俱灭");
                music.setDescription("电视剧《蜀山战纪》插曲");
                music.setMusicUrl("http://gacl.ngrok.natapp.cn/music/music.mp3");
                music.setHqMusicUrl("http://gacl.ngrok.natapp.cn/music/music.mp3");
                responseMessage = MessageBuildUtil.buildMusicMessage(map, music);
                break;
            case "视频":
                Video video = new Video();
                video.setMediaId("GqmIGpLu41rtwaY7WCVtJAL3ZbslzKiuLEXfWIKYDnHXGObH1CBH71xtgrGwyCa3");
                video.setTitle("小苹果");
                video.setDescription("小苹果搞笑视频");
                responseMessage = MessageBuildUtil.buildVideoMessage(map, video);
                break;
            default:
            	responseMessage = buildWelcomeXml(map);
                break;

        }
        //返回响应消息
        return responseMessage;
    }

	/** 
	 * <一句话功能简述>
	 * <功能详细描述>
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	private static Object getUtcTime() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String nowTime = df.format(date);
		return nowTime;
	}
	
	
}
