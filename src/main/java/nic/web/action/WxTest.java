/*
 * 文 件 名:  WxTest.java
 * 版    权:  Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  james
 * 创建时间:  2016年5月11日
 */
package nic.web.action;

import org.springframework.stereotype.Controller;

/**
 * <一句话功能简述>
 *  
 * @author  james
 * @version  [V1.00, 2016年5月11日]
 * @see  [相关类/方法]
 * @since V1.00
 */
@Controller
//@RequestMapping("/WxTest")
public class WxTest {/*
	
	private Logger log = LoggerFactory.getLogger(getClass());

	private String qrcode_url = "https://api.weixin.qq.com/card/qrcode/create?access_token=%s";
	private String white_list_url = "https://api.weixin.qq.com/card/testwhitelist/set?access_token=%s";
	private String cardCreate_url = "https://api.weixin.qq.com/card/create?access_token=%s";
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static String appid = "wxdf737893056e23f5";
	private static String appSecret = "d51745ddc6b999f09a4586beb8179a10";
	private static WxCardResponse resp = new WxCardResponse();
	
	@RequestMapping("/test")
	public String test(){
		
		return "card/index";
	}
	
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request) throws ParseException, JSONException{
		log.debug("index start...........................................................");
		ModelAndView mav = new ModelAndView();
		
		String whiteListUrl = String.format(white_list_url, CacheManager.cacheMap.get("accesstoken"));
		
		WxWhiteList list = new WxWhiteList();
		List<String> openidList = new ArrayList<String>();
		String openid = "oa-UKw2o8oxbkhDC0DzcJzxEWaOc";
		openidList.add(openid);
		list.setOpenid(openidList);
		String whiteListResult = HttpClientHelper.postString(whiteListUrl, new Gson().toJson(list));
		
		log.info("\n=======whiteListResult:{}=========" , whiteListResult);
		
		cardCreate();
		
		//String code = "nic18951853792";
		
		//qrcodeCreate(code,openid);
		
		String jsapi_ticket = CacheManager.cacheMap.get("jsApiTicket");
		log.debug("jsApiTicket:{}",jsapi_ticket);
		StringBuilder url = new StringBuilder();
		url.append(request.getScheme()).append("://")
			.append(request.getServerName()).append(":").append(request.getServerPort())
			.append(request.getContextPath()).append(request.getServletPath());
		if (null != request.getQueryString()) {
			url.append(request.getQueryString());
		}
		
		log.debug("url:{}",url);
		
		Map<String, String> signMap = Sign.sign(jsapi_ticket, url.toString());
		String nonceStr = signMap.get("nonceStr");
		String timestamp = signMap.get("timestamp");
		String js_signature = signMap.get("signature");
		
		mav.addObject("wxAppid",appid);
		mav.addObject("timestamp", timestamp);
		mav.addObject("nonceStr", nonceStr);
		mav.addObject("js_signature", js_signature);
		
		
		String cardapi_ticket = CacheManager.cacheMap.get("cardApiTicket");
		log.debug("cardapi_ticket:{}",cardapi_ticket);
		String cardId = resp.getCard_id();
		WxCardSign sign = new WxCardSign();
		sign.AddData(nonceStr);
		sign.AddData(timestamp);
		sign.AddData(cardapi_ticket);
		//sign.AddData(code);
		sign.AddData(cardId);
		//sign.AddData(openid);
		//cardsign签名
		String card_signature =sign.GetSignature();
		log.info("card_signature:" + card_signature);
		
		mav.addObject("cardId", cardId);
		//mav.addObject("code",code);
		//mav.addObject("openid", openid);
		mav.addObject("card_signature",card_signature);
		mav.setViewName("card/index2");
		
		Iterator<Entry<String, Object>> iterator = mav.getModel().entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, Object> next = iterator.next();
			log.info(next.getKey() + ":" + next.getValue());
		}
		log.debug("index end.............................................................");
		return mav;
	}
	
	
	
	@RequestMapping("/createCard")
	public ModelAndView cardCreate() throws ParseException, JSONException{
		
		ModelAndView mav = new ModelAndView();
		String tokenStr = CacheManager.cacheMap.get("accesstoken");
		
		String url = String.format(cardCreate_url,tokenStr);
		
		WxCardGroupon card = new WxCardGroupon();
		card.setDealDetail("团购详情啊啊啊啊啊！！！");
		WxCardBaseInfo baseInfo = card.getBaseInfo();
		baseInfo.setLogoUrl(
				"http://mmbiz.qpic.cn/mmbiz/iaL1LJM1mF9aRKPZJkmG8xXhiaHqkKSVMMWeN3hLut7X7hicFNjakmxibMLGWpXrEXB33367o7zHN0CwngnQY7zb7g/0");
		baseInfo.setBrandName("海底捞测试账号");
		baseInfo.setCodeType(0);
		baseInfo.setTitle("380元测试账号");
		baseInfo.setSubTitle("周末狂欢必备");
		baseInfo.setColor("Color010");
		baseInfo.setNotice("使用时向服务员出示此券");
		baseInfo.setServicePhone("020-88888888");
		baseInfo.setDescription("不可与其他优惠同享\n如需团购券发票，请在消费时向商户提出\n店内均可使用，仅限堂食");
		
		 * DateInfo dateInfo = new DateInfo();
		 * dateInfo.setType("DATE_TYPE_FIX_TERM"); dateInfo.setFixed_term(15);
		 * dateInfo.setFixed_begin_term(0); baseInfo.setDate_info(dateInfo);
		 
		//baseInfo.setDateInfoFixTerm(15, 0);
		Date begin = formatter.parse("2016-05-11 00:00:00");
		Date end = formatter.parse("2016-06-21 23:59:59"); 
		baseInfo.setDateInfoTimeRange(begin, end);
		
		 * List<Integer> list = new ArrayList<Integer>();
		 * baseInfo.setQuantity(500000); list.add(baseInfo.getQuantity());
		 * baseInfo.setSku(list);
		 

		baseInfo.setQuantity(500000);
		baseInfo.setGetLimit(3);
		//baseInfo.setUseCustomCode(true);
		//baseInfo.setBindOpenid(true);
		baseInfo.setCanShare(true);
		baseInfo.setCanGiveFriend(true);
		List<Integer> locationList = new ArrayList<Integer>();
		locationList.add(123);
		locationList.add(234);
		locationList.add(345);
		baseInfo.setLocationIdList(locationList);
		
		String result = HttpClientHelper.postString(url, card.toJsonString());

		log.info("cardCreate:" + result);

		resp = JsonHelper.str2bean(result, WxCardResponse.class);
		
		
		mav.addObject("card", card);
		ModelMap map = new ModelMap();
		map.put("description", card.getBaseInfo().getDescription());
		map.put("brand_name", card.getBaseInfo().GetBrandName());
		map.put("title", card.getBaseInfo().getTitle());
		map.put("sub_title", card.getBaseInfo().getSubTitle());
		map.put("notice", card.getBaseInfo().getNotice());
		map.put("service_phone", card.getBaseInfo().getServicePhone());
		map.put("location_id_list", card.getBaseInfo().getLocationIdList());
		map.put("type", card.getBaseInfo().getDateInfo().get("type"));
		map.put("beginTime", formatter.format(card.getBaseInfo().getDateInfo().get("begin_timestamp")));
		map.put("endTime", formatter.format(card.getBaseInfo().getDateInfo().get("end_timestamp")));
		mav.addAllObjects(map);
		mav.setViewName("card/createCard");
		mav.addObject("resp", resp);
		return mav;
	}
	
	@RequestMapping("/qrcodeCreate")
	public void qrcodeCreate(String code,String openid){
		WxQrcode qr = new WxQrcode();
		qr.setAction_name("QR_CARD");
		qr.setExpire_seconds(1800);
		ActionInfo actionInfo = new ActionInfo();
		Card card = new Card();
		card.setCard_id(resp.getCard_id());
		card.setCode(code);
		card.setIs_unique_code(true);
		card.setOpenid(openid);
		actionInfo.setCard(card);
		qr.setAction_info(actionInfo);
		
		String url = String.format(qrcode_url,CacheManager.cacheMap.get("accesstoken"));
		
		String result = HttpClientHelper.postString(url, new Gson().toJson(qr));
		
		log.info("qrcodeCreate:" + result);
		
		WxQrcodeResponse qrcodeResp = new WxQrcodeResponse();
		qrcodeResp = JsonHelper.str2bean(result, WxQrcodeResponse.class);
	}
	
*/}
