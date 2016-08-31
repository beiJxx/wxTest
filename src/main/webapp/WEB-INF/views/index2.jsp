<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="http://demo.open.weixin.qq.com/jssdk/css/style.css?ts=1420774989">
<title>Insert title here</title>
</head>
<body>
	<button class="btn btn_primary" id="addCard">addCard</button>
	<button class="btn btn_primary" id="chooseCard">chooseCard</button>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script>
		wx.config({
			debug : false,
			appId : '${wxAppid}',
			timestamp : '${timestamp}',
			nonceStr : '${nonceStr}',
			signature : '${js_signature}',
			jsApiList : [ 'addCard' ]
		});
		wx.ready(function() {
		//添加卡券
		document.querySelector('#addCard').onclick = function() {
			wx.addCard({
				cardList :[{
						cardId : '${cardId}',
						cardExt : '{"nonce_str":"${nonceStr}","timestamp":"${timestamp}","signature":"${card_signature}"}'
						},
						{
							cardId : '${cardId}',
							cardExt : '{"nonce_str":"${nonceStr}","timestamp":"${timestamp}","signature":"${card_signature}"}'
							}],
						success : function(res) {
							var cardList= res.cardList;
									}
								});
					};
		document.querySelector('#chooseCard').onclick = function() {		
			wx.chooseCard({
			    timestamp: '${timestamp}', // 卡券签名时间戳
			    nonceStr: '${nonceStr}', // 卡券签名随机串
			    signType: 'SHA1', // 签名方式，默认'SHA1'
			    cardSign: '${card_signature}', // 卡券签名
			    success: function (res) {
			        var cardList= res.cardList; // 用户选中的卡券列表信息
			    }
			});
		}			
				});
	</script>
</body>
</html>