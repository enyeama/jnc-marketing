package com.sap.jnc.marketing.infrastructure.shared.utils.wechat;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by dy on 16/6/27.
 */
public class Signature {

	private static final Logger LOGGER = LoggerFactory.getLogger(Signature.class);

	/**
	 * @param o
	 * @return
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("rawtypes")
	public static String getSign(Object o, String shopKey) throws IllegalAccessException {
		ArrayList<String> list = new ArrayList<String>();
		Class cls = o.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field f : fields) {
			f.setAccessible(true);
			XStreamAlias xStreamAliasAnnotation = f.getDeclaredAnnotation(XStreamAlias.class);
			if (f.get(o) != null && f.get(o) != "" && null != xStreamAliasAnnotation && null != xStreamAliasAnnotation.value()) {
				String annotationValue = xStreamAliasAnnotation.value();
				if (StringUtils.equals("", annotationValue) || StringUtils.equals("sign", annotationValue)) {
					continue;
				}
				list.add(annotationValue + "=" + f.get(o) + "&");
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		result += "key=" + shopKey;
		LOGGER.debug("Sign Before MD5:" + result);
		result = MD5.MD5Encode(result).toUpperCase();
		LOGGER.debug("Sign Result:" + result);
		return result;
	}

	public static String getSign(Map<String, Object> map, String shopKey) {
		ArrayList<String> list = new ArrayList<String>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() != "") {
				list.add(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		result += "key=" + shopKey;
		LOGGER.debug("Sign Before MD5:" + result);
		result = MD5.MD5Encode(result).toUpperCase();
		LOGGER.debug("Sign Result:" + result);
		return result;
	}

	/**
	 * @param responseString
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public static String getSignFromResponseString(String responseString, String shopKey) throws IOException, SAXException,
		ParserConfigurationException {
		Map<String, Object> map = XMLParser.getMapFromXML(responseString);
		map.put("sign", "");
		return Signature.getSign(map, shopKey);
	}

	/**
	 * @param responseString
	 * @return
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static boolean checkIsSignValidFromResponseString(String responseString, String shopKey) throws ParserConfigurationException, IOException,
		SAXException {

		Map<String, Object> map = XMLParser.getMapFromXML(responseString);
		LOGGER.debug(map.toString());

		String signFromAPIResponse = map.get("sign").toString();
		if (signFromAPIResponse == "" || signFromAPIResponse == null) {
			LOGGER.debug("cannot find signature reply from wechat server!");
			return false;
		}
		LOGGER.debug("signature response from wechat server:" + signFromAPIResponse);
		map.put("sign", "");
		String signForAPIResponse = Signature.getSign(map, shopKey);

		if (!signForAPIResponse.equals(signFromAPIResponse)) {
			LOGGER.debug("signature not matched!");
			return false;
		}
		return true;
	}

	/**
	 * js sdk sign
	 * 
	 * @param noncestr
	 * @param jsapi_ticket
	 * @param timestamp
	 * @param url
	 * @return
	 * @throws IllegalAccessException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getJSSDKSign(String noncestr, String jsapi_ticket, String timestamp, String url) throws IllegalAccessException,
		NoSuchAlgorithmException {
		ArrayList<String> list = new ArrayList<>();
		list.add("&noncestr=" + noncestr);
		list.add("&jsapi_ticket=" + jsapi_ticket);
		list.add("&timestamp=" + timestamp);
		list.add("&url=" + url);
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		// String result = sb.toString();
		sb.delete(0, 1);
		String result = sb.toString();
		LOGGER.debug("Sign Before SHA1:" + result);
		result = SHA1.SHA1Encode(result);
		LOGGER.debug("Sign Result:" + result);
		return result;
	}

}
