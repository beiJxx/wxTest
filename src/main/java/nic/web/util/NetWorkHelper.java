/*
 * 文 件 名:  NetWorkHelper.java
 * 版    权:  Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  james
 * 创建时间:  2016年8月25日
 */
package nic.web.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * <一句话功能简述>
 *  
 * @author  james
 * @version  [V1.00, 2016年8月25日]
 * @see  [相关类/方法]
 * @since V1.00
 */
public class NetWorkHelper {

	public String getHttpsResponse(String reqUrl,String requestMethod){
		URL url;
		InputStream is;
		String resultData = "";
		try {
			url = new URL(reqUrl);
			HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
			TrustManager[] tm = {xtm};
			
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, tm, null);
			
			con.setSSLSocketFactory(ctx.getSocketFactory());
			con.setHostnameVerifier(new HostnameVerifier() {
				
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			});
			
			con.setDoInput(true);
			
			con.setDoOutput(false);
			con.setUseCaches(false);
			if(null != requestMethod && !requestMethod.equals("")){
				con.setRequestMethod(requestMethod);
			}else{
				con.setRequestMethod("GET");
			}
			is = con.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader bufferReader = new BufferedReader(isr);
			String inputLine;
			while((inputLine = bufferReader.readLine()) != null){
				resultData += inputLine + "\n";
			}
			System.out.println(resultData);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return resultData;
	}
	
	X509TrustManager xtm = new X509TrustManager() {
		
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
		
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			
		}
		
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			
		}
	};
	
}
