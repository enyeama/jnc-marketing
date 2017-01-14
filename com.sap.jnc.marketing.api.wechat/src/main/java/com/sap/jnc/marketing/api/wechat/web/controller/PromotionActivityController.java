package com.sap.jnc.marketing.api.wechat.web.controller;

import com.google.gson.Gson;
import com.sap.jnc.marketing.dto.request.activity.GainWineRequest;
import com.sap.jnc.marketing.infrastructure.shared.SystemConfig;
import com.sap.jnc.marketing.infrastructure.shared.constant.Constants;
import com.sap.jnc.marketing.service.activity.PromotionActivityService;
import com.sap.jnc.marketing.service.security.portal.PortalUser;
import com.sap.jnc.marketing.service.wechat.WechatService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by ddtang on 16/6/23. promotion activity
 */
@RestController
@RequestMapping("activities")
public class PromotionActivityController {

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private PromotionActivityService promotionActivityService;

	@Autowired
	private WechatService wechatService;

	private static final Logger LOGGER = LoggerFactory.getLogger(PromotionActivityController.class);

	/**
	 * @param portalUser
	 *            current user
	 * @return boolean if true the user can gain a wine else not check whether the user has gained a wine in five days;
	 */
	@RequestMapping(value = "check5", method = RequestMethod.GET)
	public boolean checkFive(@AuthenticationPrincipal PortalUser portalUser) {
		boolean result = false;
		result = promotionActivityService.checkFive(portalUser.getOpenId());
		LOGGER.info("current user: {} has gained a wine in five day: {}", portalUser, result);
		return result;
	}

	@RequestMapping(method = RequestMethod.POST)
	public void create(@AuthenticationPrincipal PortalUser portalUser, GainWineRequest request) {
		request.setWxOpenId(portalUser.getOpenId());
		WxMpUser user = null;
		try {
			user = wechatService.getWxMpService().userInfo(portalUser.getOpenId(), Constants.LANG);
		}
		catch (Exception e) {
			LOGGER.error("convert to wxmpuser error: {}", e.getMessage());
		}
		request.setWxUserName(user == null ? "" : user.getNickname());
		String requestJson = new Gson().toJson(request);
		LOGGER.info("###################request data: " + requestJson);
		promotionActivityService.create(request);
	}

	@RequestMapping(value = "location", method = RequestMethod.GET)
	public Object tencentLocation(@Param("latitude") BigDecimal latitude, @Param("longitude") BigDecimal longitude) {
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet get = new HttpGet(new URIBuilder(systemConfig.getTencentApi()).addParameter("location", latitude + "," + longitude).addParameter(
				"key", systemConfig.getTencentKey()).build());
			CloseableHttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity);
			return new Gson().fromJson(result, Map.class);
		}
		catch (Exception e) {
			LOGGER.error("failed to get tencent location : {}", e.getMessage());
		}
		return null;
	}
}
