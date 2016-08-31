/*
 * 文 件 名:  WxAction.java
 * 版    权:  Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  james
 * 创建时间:  2016年8月31日
 */
package nic.web.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import nic.web.common.AccessTokenInfo;
import nic.web.entity.WxCardBaseInfo;
import nic.web.entity.WxCardGroupon;
import nic.web.entity.WxCheckCodeRequest;
import nic.web.entity.WxCreateCardResponse;
import nic.web.entity.WxPoiListRequest;
import nic.web.entity.WxUserCardRequest;
import nic.web.entity.WxUserCardResponse;
import nic.web.entity.WxUserCardResponse.WxUserCard;
import nic.web.util.HttpClientHelper;
import nic.web.util.JSONException;
import nic.web.util.JsSign;
import nic.web.util.JsonHelper;
import nic.web.util.WxCardSign;

/**
 * <一句话功能简述>
 * 
 * @author james
 * @version [V1.00, 2016年8月31日]
 * @see [相关类/方法]
 * @since V1.00
 */
@Controller
@RequestMapping("wxCard")
public class WxAction {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	// 创建卡券
	private static String cardCreateUrl = "https://api.weixin.qq.com/card/create?access_token=%s";

	// 查询code
	private static String checkCodeUrl = "https://api.weixin.qq.com/card/code/get?access_token=%s";

	// 获取门店
	private static String getPoiListUrl = "https://api.weixin.qq.com/cgi-bin/poi/getpoilist?access_token=%s";

	// 获取用户已领取卡券
	private static String userCardGetUrl = "https://api.weixin.qq.com/card/user/getcardlist?access_token=%s";

	// 设置卡券失效
	private static String unavailableCodeUrl = "https://api.weixin.qq.com/card/code/unavailable?access_token=%s";

	private static String getTemplate = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static String appId = "wxdf737893056e23f5";
	
	private static WxCreateCardResponse resp = new WxCreateCardResponse();


	@RequestMapping("/wxcardindex")
	public ModelAndView index(HttpServletRequest request) throws JSONException, ParseException{
		
		String accessToken = AccessTokenInfo.accessToken.getAccessToken();
		cardCreate(accessToken);
		//userCardGet(accessToken);
		ModelAndView mav = new ModelAndView();
		if(!"0".equals(resp.getErrCode())){
			mav.setViewName("card/error");
			mav.addObject("errcode", resp.getErrCode());
			mav.addObject("errmsg", resp.getErrMsg());
			return mav;
		}
		
		StringBuilder url = new StringBuilder();
		url.append(request.getScheme()).append("://")
			.append(request.getServerName())
			.append(request.getContextPath()).append(request.getServletPath());
		if (null != request.getQueryString()) {
			url.append(request.getQueryString());
		}
		
		// config中的参数
		String jsApiTicket = AccessTokenInfo.jsApiTicket.getTicket();
		Map<String, String> signMap = JsSign.sign(jsApiTicket, url.toString());
		String nonceStr = signMap.get("nonceStr");
		String timestamp = signMap.get("timestamp");
		String js_signature = signMap.get("signature");
		mav.addObject("wxAppid",appId);
		mav.addObject("timestamp", timestamp);
		mav.addObject("nonceStr", nonceStr);
		mav.addObject("js_signature", js_signature);
		
		log.info("appid: {},timestamp: {},nonceStr: {},url: {}.js_signature: {}", appId,timestamp,nonceStr,url,js_signature);
		
		//addCard
		String cardapi_ticket = AccessTokenInfo.cardApiTicket.getTicket();
		//String code = "n18951853372";
		String cardId = resp.getCard_id();
		WxCardSign sign = new WxCardSign();
		sign.AddData(nonceStr);
		sign.AddData(timestamp);
		sign.AddData(cardapi_ticket);
		//sign.AddData(code);
		sign.AddData(cardId);
		//cardsign签名
		String card_signature =sign.GetSignature();
		
		log.info("nonceStr: {},timestamp: {},cardapi_ticket: {},cardId: {};card_signature: {}",nonceStr,timestamp,cardapi_ticket,cardId,card_signature);
		
		mav.addObject("cardId", cardId);
		//mav.addObject("openid",openid);
		//mav.addObject("code",code);
		mav.addObject("card_signature",card_signature);
		mav.setViewName("index2");
		
		return mav;
	}

	@RequestMapping("/createCard")
	public WxCreateCardResponse cardCreate(String accessToken) throws ParseException, JSONException{
		accessToken = AccessTokenInfo.accessToken.getAccessToken();
		String url = String.format(cardCreateUrl,accessToken);
		
		
		WxCardGroupon card = new WxCardGroupon();
		card.setDealDetail("团购详情啊啊啊啊啊！！！");
		WxCardBaseInfo baseInfo = card.getBaseInfo();
		
		baseInfo.setLogoUrl(
				"http://mmbiz.qpic.cn/mmbiz/iaL1LJM1mF9aRKPZJkmG8xXhiaHqkKSVMMWeN3hLut7X7hicFNjakmxibMLGWpXrEXB33367o7zHN0CwngnQY7zb7g/0");
		baseInfo.setBrandName(formatter.format(new Date()).split(" ")[1]+"海底捞测试券");
		baseInfo.setCodeType(2);
		baseInfo.setTitle("酷券测试");
		baseInfo.setSubTitle("周末狂欢必备");
		baseInfo.setColor("Color010");
		baseInfo.setNotice("使用时向服务员出示此券");
		baseInfo.setServicePhone("020-88888888");
		baseInfo.setDescription("不可与其他优惠同享\n如需团购券发票，请在消费时向商户提出\n店内均可使用，仅限堂食");
		
		
		 /* DateInfo dateInfo = new DateInfo();
		 * dateInfo.setType("DATE_TYPE_FIX_TERM"); dateInfo.setFixed_term(15);
		  dateInfo.setFixed_begin_term(0); baseInfo.setDate_info(dateInfo);*/
		 
		//baseInfo.setDateInfoFixTerm(15, 0);
		Date begin = formatter.parse("2016-08-11 00:00:00");
		Date end = formatter.parse("2016-09-21 23:59:59"); 
		baseInfo.setDateInfoTimeRange(begin, end);
		
		baseInfo.setQuantity(100);
		//baseInfo.setUseCustomCode(true);
		//baseInfo.setBindOpenid(true);
		baseInfo.setCanShare(false);
		baseInfo.setCanGiveFriend(true);
		//baseInfo.setLocationIdList(getPoilist(accessToken));
		String result = HttpClientHelper.postString(url, card.toJsonString());

		System.out.println("cardCreate:" + result);

		ModelAndView mav = new ModelAndView();
		JsonObject asJsonObject = new JsonParser().parse(result).getAsJsonObject();
		if(0 != asJsonObject.get("errcode").getAsInt()){
			resp.setErrCode(String.valueOf(asJsonObject.get("errcode").getAsInt()));
			resp.setErrMsg(asJsonObject.get("errmsg").getAsString());
			return resp;
		}
		
		resp = JsonHelper.str2bean(result, WxCreateCardResponse.class);
		
		//mav.setViewName("card/index2");
		//mav.addObject("resp", resp);
		return resp;
	}

	@RequestMapping("/wxcardcheck")
	public ModelAndView checkCode(){
		ModelAndView mav = new ModelAndView();
		
		
		String url = String.format(checkCodeUrl,AccessTokenInfo.accessToken.getAccessToken());
		
		WxCheckCodeRequest req = new WxCheckCodeRequest();
		
		req.setCardId(resp.getCard_id());
		req.setCode("728322922686");
		String result = HttpClientHelper.postString(url, req.toJsonString());
		
		log.debug("checkCodeResult:[{}]", result);
		JsonObject asJsonObject = new JsonParser().parse(result).getAsJsonObject();
		if(0 != asJsonObject.get("errcode").getAsInt()){
			error(mav,asJsonObject);
			return mav;
		}
		mav.setViewName("card/success");
		return mav;
	}
	
	private List<Integer> getPoilist(String accessToken){
		List<Integer> poilist = new ArrayList<Integer>();
		
		String url = String.format(getPoiListUrl,accessToken);
		
		WxPoiListRequest req = new WxPoiListRequest();
		req.setBegin(0);
		req.setLimit(10);
		String result = HttpClientHelper.postString(url, req.toJsonString());
		System.out.println("getPoiListResult:"+ result);
		log.debug("getPoiListResult:[{}]", result);
		
		JsonArray asJsonArray = new JsonParser().parse(result).getAsJsonObject().get("business_list").getAsJsonArray();
		Iterator<JsonElement> iterator = asJsonArray.iterator();
		while(iterator.hasNext()){
			JsonObject asJsonObject = iterator.next().getAsJsonObject().get("base_info").getAsJsonObject();
			poilist.add(asJsonObject.get("poi_id").getAsInt());
			System.out.println(asJsonObject.get("poi_id"));
		}
		
		//ModelAndView mav = new ModelAndView();
		//mav.setViewName("/card/success");
		return poilist;
		
	}
	
	/**
	 * 核销接口
	 */
	
	/**
	 * 设置卡券失效接口
	 */
	@RequestMapping("/unavailalbecode")
	public ModelAndView unavailableCode(){
		ModelAndView mav = new ModelAndView();
		
		String accessToken = AccessTokenInfo.accessToken.getAccessToken();
		String url = String.format(unavailableCodeUrl,accessToken);
		
		List<WxUserCard> userCardGet = userCardGet(accessToken);
		
		for(WxUserCard card : userCardGet){
			String result = HttpClientHelper.postString(url,new Gson().toJson(card));
			log.debug("unavailableCode Result: {}",result);
		}
		
		mav.setViewName("card/success");
		return mav;
	}
	
	/**
	 * 获取用户已领取卡券接口
	 * <一句话功能简述>
	 * <功能详细描述>
	 * @see [类、类#方法、类#成员]
	 */
	private List<WxUserCard> userCardGet(String accessToken){
		String url = String.format(userCardGetUrl,accessToken);
		WxUserCardRequest req = new WxUserCardRequest();
		//req.setCard_id(resp.getCard_id());
		req.setCard_id("pa-UKw4ukFBGmdyPE1tH24KZV2us");
		req.setOpenid("oa-UKw2o8oxbkhDC0DzcJzxEWaOc");
		String result = HttpClientHelper.postString(url,new Gson().toJson(req));
		log.debug("result: {}" , result);
		JsonArray asJsonArray = new JsonParser().parse(result).getAsJsonObject().get("card_list").getAsJsonArray();
		WxUserCardResponse str2bean = JsonHelper.str2bean(result, WxUserCardResponse.class);
		List<WxUserCard> list = str2bean.getCard_list();
		return list;
	}
	
	@RequestMapping("getTemplate")
	public void getTemplate(){
		String url = String.format(getTemplate,AccessTokenInfo.accessToken.getAccessToken());
		
		String string = HttpClientHelper.get(url);
		
		System.out.println(string);
		
	}
	
	
	
	private void error(ModelAndView mav,JsonObject asJsonObject){
		mav.setViewName("card/error");
		mav.addObject("errcode", asJsonObject.get("errcode"));
		mav.addObject("errmsg", asJsonObject.get("errmsg"));
	}
	
	public static void main(String[] args) {
		System.out.println((formatter.format(new Date())+"酷券测试").length());
	}

}
