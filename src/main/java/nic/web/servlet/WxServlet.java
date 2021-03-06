package nic.web.servlet;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nic.web.util.MessageHandlerUtil;

/**
 * Servlet implementation class WxServlet
 */
@WebServlet(urlPatterns="/myWxServlet")
public class WxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private final String TOKEN = "nic";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WxServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("开始校验签名");
	       /**
	        * 接收微信服务器发送请求时传递过来的4个参数
	        */
	       String signature = request.getParameter("signature");//微信加密签名signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
	       String timestamp = request.getParameter("timestamp");//时间戳
	       String nonce = request.getParameter("nonce");//随机数
	       String echostr = request.getParameter("echostr");//随机字符串
	       //排序
	       String sortString = sort(TOKEN, timestamp, nonce);
	       //加密
	       String mySignature = sha1(sortString);
	       //校验签名
	       if (mySignature != null && mySignature != "" && mySignature.equals(signature)) {
	           System.out.println("签名校验通过。");
	           //如果检验成功输出echostr，微信服务器接收到此输出，才会确认检验完成。
	           //response.getWriter().println(echostr);
	           response.getWriter().write(echostr);
	       } else {
	           System.out.println("签名校验失败.");
	       }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		System.out.println("请求进入");
		String result = "";
		try {
			Map<String,String> map = MessageHandlerUtil.parseXml(request);
			System.out.println("开始构造消息");
			result = MessageHandlerUtil.buildResponseMessage(map);
			System.out.println("result: " + result);
			if(result.equals(""))
				result = "未正确响应";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("exception");
		}
		response.getWriter().println(result);
	}

	
	/**
     * 排序方法
     *
     * @param token
     * @param timestamp
     * @param nonce
     * @return
     */
	public String sort(String token, String timestamp, String nonce) {
		String[] strArray = { token, timestamp, nonce };
		Arrays.sort(strArray);
		StringBuilder sb = new StringBuilder();
		for (String str : strArray) {
			sb.append(str);
		}

		return sb.toString();
	}
	
	/**
     * 将字符串进行sha1加密
     *
     * @param str 需要加密的字符串
     * @return 加密后的内容
     */
	public String sha1(String str) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(str.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
