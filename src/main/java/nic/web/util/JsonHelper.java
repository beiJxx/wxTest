package nic.web.util;


import com.google.gson.GsonBuilder;

public class JsonHelper {

	public static <T> T str2bean(String str, Class<T> clz) {
		return new GsonBuilder().create().fromJson(str, clz);
	}
	
	/*public static String bean2str(Object obj) {
		String str = new GsonBuilder().disableHtmlEscaping().create().toJson(obj);
		return StringEscapeUtils.escapeJavaScript(escape(str));
	}*/
	
	private static String escape(String str) {
		str = str.replace("\\r\\n", "<br>");
		str = str.replace("\\r", "<br>");
		str = str.replace("\\n", "<br>");
		return str;
	}
	
}
