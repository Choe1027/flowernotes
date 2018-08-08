package com.flowernotes.common.utils;


import com.flowernotes.common.exception.CommonException;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/** URL请求工具 */
public class UrlConnectUtil {

	/***
	 * 请求一个url，获取返回值
	 * @param webUrl     url路径
	 * @param method     请求方式(POST/GET)
	 * @param property   请求头
	 * @param paramMap   请求参数(主要POST请求场景所需)
	 * @return 返回该url的响应结果
	 */
	public static String doUrl(String webUrl, String method, Map<String, String> property, Map<String, String> paramMap) {
		String paramStr = "";
		if(paramMap != null) {
			for (String key : paramMap.keySet()) {
				paramStr += key + "=";
				paramStr += paramMap.get(key) + "&";
			}
			paramStr = paramStr.substring(0, paramStr.length() - 1);
		}
		return doUrl(webUrl, method, property, paramStr);
	}
	
	/***
	 * 请求一个url，获取返回值
	 * @param webUrl     url路径
	 * @param method     请求方式(POST/GET)
	 * @param property   请求头
	 * @param message   请求参数(主要POST请求场景所需)
	 * @return 返回该url的响应结果
	 */
	public static String doUrl(String webUrl, String method, Map<String, String> property, String message) {
		try {
			URL url = new URL(webUrl);
			URLConnection connect = url.openConnection();
			connect.setUseCaches(false);
			connect.setDoOutput(true);
			// 请求头
			if (property != null) {
				for (String key : property.keySet()) {
					connect.setRequestProperty(key, property.get(key));
				}
			}

			// 请求方式
			if (method != null) {
				((HttpURLConnection) connect).setRequestMethod(method);
				if (!StringUtil.isEmpty(message)) {
					connect.getOutputStream().write(message.getBytes());
					connect.getOutputStream().close();
				}
			}

			// 获取响应结果
			connect.connect();
			// HttpURLConnection httpUrlConnection = (HttpURLConnection)
			// connect;
			// int code = httpUrlConnection.getResponseCode();
			// if (code == 200) {
			InputStream in = connect.getInputStream();
			byte[] bts = new byte[1024];
			int length = 0;
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			while ((length = in.read(bts)) > 0) {
				bout.write(bts, 0, length);
				bout.flush();
			}
			return new String(bout.toByteArray());
			// } else {
			// return null;
			// }
		} catch (Exception e) {
			throw new CommonException(e);
		}
	}
	
	/***
	 * 获取远程url的input流
	 * @param webUrl	远程url
	 * @param method	提交方式（POST/GET）
	 * @return	返回流
	 */
	public static InputStream getInputStream(String webUrl, String method) {
		try {
			URL url = new URL(webUrl);
			URLConnection connect = url.openConnection();
			connect.setUseCaches(false);

			// 请求方式
			if (method != null) {
				((HttpURLConnection) connect).setRequestMethod(method);
			}

			// 获取响应结果
			return connect.getInputStream();
		} catch (Exception e) {
			throw new CommonException(e);
		}
	}

	/***
	 * 请求一个url，获取返回值
	 * @param webUrl   url路径
	 * @param method   请求方式(POST/GET)
	 * @param property 请求头
	 * @param key      单个参数key
	 * @param val  单个参数val
	 * @return 返回该url的响应结果
	 */
	public static String doUrl(String webUrl, String method, Map<String, String> property, String key, String val) {
		Map<String, String> param = new HashMap<String, String>();
		param.put(key, val);
		return doUrl(webUrl, method, property, param);
	}

	/***
	 * 请求一个url，获取返回值
	 * @param webUrl url路径
	 * @param method 请求方式(POST/GET)
	 * @return 返回该url的响应结果
	 */
	public static String doUrl(String webUrl, String method) {
		return doUrl(webUrl, method, null, "");
	}

	/***
	 * 请求一个url，GET方式,获取返回值
	 * 
	 * @param webUrl url路径
	 * @return 返回该url的响应结果
	 */
	public static String doUrl(String webUrl) {
		return doUrl(webUrl, "GET", null, "");
	}
}
