package com.sap.jnc.marketing.api.wechat.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sap.jnc.marketing.dto.response.wechat.JSSDKConfigResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sap.jnc.marketing.service.wechat.WechatService;

/**
 * @author I071053 Diouf Du
 */

@RestController
@RequestMapping("/wechat")
public class WechatController extends GeneralController {

	@Autowired
	private WechatService wechatService;

	@RequestMapping("/push")
	public void handlewechatPush() throws IOException {
		final HttpServletRequest request = super.getHttpServletRequest();
		final HttpServletResponse response = super.getHttpServletResponse();
		final String signature = request.getParameter("signature");// 微信加密签名
		final String timestamp = request.getParameter("timestamp");// 时间戳
		final String nonce = request.getParameter("nonce");// 随机数
		final String echostr = request.getParameter("echostr");// 随机字符串
		this.LOGGER.debug("###############echostr: " + echostr);
		if (StringUtils.isNotBlank(echostr)) {
			// 验证URL真实性
			if (this.wechatService.getWxMpService().checkSignature(timestamp, nonce, signature)) {
				this.LOGGER.info("echo {} to wechat server", echostr);
				response.getWriter().write(echostr);
			}
		}
		else {
			this.LOGGER.debug("wechat event or message comes ...........");
			this.wechatService.messageHandle(request, response, timestamp, nonce);
		}
	}

	// 创建微信菜单
	@RequestMapping("createMenu")
	public void createMenu() {
		wechatService.createMenu();
	}

	// js获取微信基本配置
	@RequestMapping("js_config")
	public JSSDKConfigResponse jsWxConfig(@RequestParam("url") String url) {
		return wechatService.jsWxConfig(url);
	}
}
