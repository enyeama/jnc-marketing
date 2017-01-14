package com.sap.jnc.marketing.infrastructure.shared;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DeployMode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

/**
 * Created by ying.dong on 3/18/16.
 */
@Component
@PropertySource("classpath:META-INF/wechat.properties")
public class SystemConfig {

	@Value("${wechat.appid}")
	private String appId;

	@Value("${wechat.redirect_uri}")
	private String redirectURI;

	@Value("${wechat.secret}")
	private String secret;

	@Value("${wechat.auth_token}")
	private String auth_token;

	@Value("${wechat.encodingaeskey}")
	private String encodingaeskey;

	// @Value("${wechat.payment.notify_url}")
	private String wechatPaymentNotifyUrl;

	// @Value("${storage.server}")
	private String storageServer;

	@Value("${sms.api.key}")
	private String smsApiKey;

	@Value("${wechat.shop.id}")
	private String shopId;

	@Value("${wechat.shop.key}")
	private String shopKey;

	// @Value("${image.server}")
	private String imgServer;

	@Value("${wechat.cert.file}")
	private String certLocalPath;

	@Value("${deploy.mode}")
	private String deployMode;

	@Value("${tencent.api}")
	private String tencentApi;

	@Value("${tencent.key}")
	private String tencentKey;

	@Value("${wechat.attention.url}")
	private String attentionUrl;

	public String getTencentApi() {
		return tencentApi;
	}

	public void setTencentApi(String tencentApi) {
		this.tencentApi = tencentApi;
	}

	public String getTencentKey() {
		return tencentKey;
	}

	public void setTencentKey(String tencentKey) {
		this.tencentKey = tencentKey;
	}


	public DeployMode getDeployMode() {
		if ("dev".equals(deployMode)) {
			return DeployMode.DEV;
		}
		if ("local".equals(deployMode)) {
			return DeployMode.LOCAL;
		}
		return DeployMode.PROD;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	public String getImgServer() {
		return imgServer;
	}

	public String getAppid() {
		return appId;
	}

	public void setAppid(String appId) {
		this.appId = appId;
	}

	public String getRedirectURI() {
		return redirectURI;
	}

	public void setRedirectURI(String redirectURI) {
		this.redirectURI = redirectURI;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getAuth_token() {
		return auth_token;
	}

	public void setAuth_token(String auth_token) {
		this.auth_token = auth_token;
	}

	public String getEncodingaeskey() {
		return encodingaeskey;
	}

	public void setEncodingaeskey(String encodingaeskey) {
		this.encodingaeskey = encodingaeskey;
	}

	public String getWechatPaymentNotifyUrl() {
		return wechatPaymentNotifyUrl;
	}

	public void setWechatPaymentNotifyUrl(String wechatPaymentNotifyUrl) {
		this.wechatPaymentNotifyUrl = wechatPaymentNotifyUrl;
	}

	public String getStorageServer() {
		return storageServer;
	}

	public void setStorageServer(String storageServer) {
		this.storageServer = storageServer;
	}

	public String getSmsApiKey() {
		return smsApiKey;
	}

	public void setSmsApiKey(String smsApiKey) {
		this.smsApiKey = smsApiKey;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getShopKey() {
		return shopKey;
	}

	public void setShopKey(String shopKey) {
		this.shopKey = shopKey;
	}

	public void setImgServer(String imgServer) {
		this.imgServer = imgServer;
	}

	public String getCertLocalPath() {
		return certLocalPath;
	}

	public void setCertLocalPath(String certLocalPath) {
		this.certLocalPath = certLocalPath;
	}

	public String getAttentionUrl() {
		return attentionUrl;
	}

	public void setAttentionUrl(String attentionUrl) {
		this.attentionUrl = attentionUrl;
	}
}
