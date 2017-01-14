package com.sap.jnc.marketing.api.wechat.web.controller;

import java.io.IOException;
import com.sap.jnc.marketing.infrastructure.shared.SystemConfig;
import com.sap.jnc.marketing.infrastructure.shared.constant.Constants;
import com.sap.jnc.marketing.infrastructure.shared.utils.sms.SmsEntity;
import com.sap.jnc.marketing.infrastructure.shared.utils.sms.SmsUtil;
import com.sap.jnc.marketing.service.globalcache.GlobalCacheService;
import com.sap.jnc.marketing.service.security.portal.PortalUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GuoKai
 */
@RestController
@RequestMapping("sms")
public class SmsController {

	private Logger LOGGER = LoggerFactory.getLogger(SmsController.class);

	@Autowired
	SystemConfig config;

	@Autowired
	GlobalCacheService globalCacheService;

	@RequestMapping("send_code")
	public void sendCode(@RequestParam("phone") String phone, @RequestParam("type") Integer type, @AuthenticationPrincipal PortalUser portalUser) {
		try {
			String code = SmsUtil.sendSms(config.getSmsApiKey(), phone);
			SmsEntity entity = new SmsEntity(type, phone, code);
			globalCacheService.save(entity.getEntityKey(), entity, Constants.CACHE_EXPIRE_TIME);
		}
		catch (IOException | RuntimeException e) {
			LOGGER.error("send sms error,{}", e);
			// TODO
		}
	}
}
