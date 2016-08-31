package nic.web.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientHelper {

	private static Logger log = LoggerFactory.getLogger(HttpClientHelper.class);
	
	public static String get(String url) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet request = new HttpGet(url);
		
		try {
			CloseableHttpResponse resp = client.execute(request);
			int statusCode = resp.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = resp.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, "UTF-8");
			}
		} catch (ClientProtocolException e) {
			log.error("[get]", e.fillInStackTrace());
		} catch (IOException e) {
			log.error("[get]", e.fillInStackTrace());
		} catch (Exception e) {
			log.error("[get]", e.fillInStackTrace());
		}
		return null;
	}
	
	public static String postForm(String url, List<NameValuePair> params) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost request = new HttpPost(url);
		try {
			request.setEntity(new UrlEncodedFormEntity(params,  "UTF-8"));
			CloseableHttpResponse resp = client.execute(request);
			int statusCode = resp.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = resp.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			log.error("[postForm]", e.fillInStackTrace());
		} catch (ClientProtocolException e) {
			log.error("[postForm]", e.fillInStackTrace());
		} catch (IOException e) {
			log.error("[postForm]", e.fillInStackTrace());
		} catch (Exception e) {
			log.error("[postForm]", e.fillInStackTrace());
		}
		return null;
	}
	
	
	public static String postString(String url, String data) {
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
			httpPost.setEntity(new StringEntity(data, ContentType.create("application/json", "UTF-8")));
			HttpResponse resp = httpclient.execute(httpPost);
			int responseCode = resp.getStatusLine().getStatusCode();
			if (responseCode == HttpStatus.SC_OK) {
				return EntityUtils.toString(resp.getEntity(), "utf-8");
			}
		} catch (ParseException e) {
			log.error("[postString]", e.fillInStackTrace());
		} catch (IOException e) {
			log.error("[postString]", e.fillInStackTrace());
		}
		return null;
	}
	
	
}
