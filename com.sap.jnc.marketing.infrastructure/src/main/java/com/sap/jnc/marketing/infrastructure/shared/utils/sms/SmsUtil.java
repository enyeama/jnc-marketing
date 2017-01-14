package com.sap.jnc.marketing.infrastructure.shared.utils.sms;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.sap.jnc.marketing.infrastructure.shared.constant.Constants;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmsUtil {

	public static Logger LOGGER = LoggerFactory.getLogger(SmsUtil.class);

	/**
	 * 智能匹配模版接口发短信
	 *
	 * @param apikey
	 *            apikey
	 * @param text
	 *            短信内容
	 * @param mobile
	 *            接受的手机号
	 * @return json格式字符串
	 * @throws IOException
	 */

	public static String sendSms(String apikey, String mobile, String... text) throws IOException {

		String code = String.valueOf(Math.round((Math.random() * 9 + 1) * 1000));
		LOGGER.debug("code : " + code);
		String message = "【剑南春】您的验证码是" + code;
		if (text != null && text.length > 0) {
			message = text[0];
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", apikey);
		params.put("text", message);
		params.put("mobile", mobile);
		// 发送短信，并接收返回参数
		Map<String, Object> json = (Map<String, Object>) JSONArray.parse(post(Constants.URI_SEND_SMS, params));
		LOGGER.debug(json.toString());
		if (!Constants.SMS_SUCCESS_CODE.equals(json.get("code").toString())) {
			// throw new BusinessException(__.addError(MessageId.E_EX_ALERT_0001));
		}
		return code;
	}

	/**
	 * 取账户信息
	 *
	 * @return json格式字符串
	 * @throws IOException
	 */

	public static String getUserInfo(String apikey) throws IOException, URISyntaxException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", apikey);
		return post(Constants.URI_GET_USER_INFO, params);
	}

	/**
	 * 通过模板发送短信(不推荐)
	 * 
	 * @param apikey
	 *            apikey
	 * @param tpl_id
	 *            模板id
	 * @param tpl_value
	 *            模板变量值
	 * @param mobile
	 *            接受的手机号
	 * @return json格式字符串
	 * @throws IOException
	 */

	public static String tplSendSms(String apikey, long tpl_id, String tpl_value, String mobile) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", apikey);
		params.put("tpl_id", String.valueOf(tpl_id));
		params.put("tpl_value", tpl_value);
		params.put("mobile", mobile);
		return post(Constants.URI_TPL_SEND_SMS, params);
	}

	/**
	 * 通过接口发送语音验证码
	 * 
	 * @param apikey
	 *            apikey
	 * @param mobile
	 *            接收的手机号
	 * @param code
	 *            验证码
	 * @return
	 */

	public static String sendVoice(String apikey, String mobile, String code) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", apikey);
		params.put("mobile", mobile);
		params.put("code", code);
		return post(Constants.URI_SEND_VOICE, params);
	}

	/**
	 * 基于HttpClient 4.3的通用POST方法
	 *
	 * @param url
	 *            提交的URL
	 * @param paramsMap
	 *            提交<参数，值>Map
	 * @return 提交响应
	 */

	public static String post(String url, Map<String, String> paramsMap) {
		CloseableHttpClient client = HttpClients.createDefault();
		String responseText = "";
		CloseableHttpResponse response = null;
		try {
			HttpPost method = new HttpPost(url);
			if (paramsMap != null) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> param : paramsMap.entrySet()) {
					NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
					paramList.add(pair);
				}
				method.setEntity(new UrlEncodedFormEntity(paramList, Constants.ENCODING));
			}
			response = client.execute(method);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseText = EntityUtils.toString(entity);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				response.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return responseText;
	}
}
