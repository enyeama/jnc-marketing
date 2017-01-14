package com.sap.jnc.marketing.infrastructure.shared;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

/**
 * Created by ying.dong on 3/18/16.
 */
@Component
@PropertySource("classpath:META-INF/activity.properties")
public class ActivityConfig {

	@Value("${wechat.activity.name}")
	private String activityName;

	@Value("${wechat.activity.shop.name}")
	private String activityShopName;

	@Value("${wechat.activity.remark}")
	private String activityRemark;

	@Value("${wechat.activity.wish}")
	private String activityWish;

	@Value("${wechat.activity.attention.msg.desc}")
	private String activityAttentionMsgDesc;

	@Value("${wechat.activity.attention.msg.title}")
	private String activityAttentionMsgTitle;

	@Value("${wechat.activity.attention.msg.img.url}")
	private String activityAttentionMsgImgUrl;

	@Value("${wechat.activity.attention.msg.redirect.url}")
	private String activityAttentionMsgRedirectUrl;


	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityShopName() {
		return activityShopName;
	}

	public void setActivityShopName(String activityShopName) {
		this.activityShopName = activityShopName;
	}

	public String getActivityRemark() {
		return activityRemark;
	}

	public void setActivityRemark(String activityRemark) {
		this.activityRemark = activityRemark;
	}

	public String getActivityWish() {
		return activityWish;
	}

	public void setActivityWish(String activityWish) {
		this.activityWish = activityWish;
	}

	public String getActivityAttentionMsgDesc() {
		return activityAttentionMsgDesc;
	}

	public void setActivityAttentionMsgDesc(String activityAttentionMsgDesc) {
		this.activityAttentionMsgDesc = activityAttentionMsgDesc;
	}

	public String getActivityAttentionMsgTitle() {
		return activityAttentionMsgTitle;
	}

	public void setActivityAttentionMsgTitle(String activityAttentionMsgTitle) {
		this.activityAttentionMsgTitle = activityAttentionMsgTitle;
	}

	public String getActivityAttentionMsgImgUrl() {
		return activityAttentionMsgImgUrl;
	}

	public void setActivityAttentionMsgImgUrl(String activityAttentionMsgImgUrl) {
		this.activityAttentionMsgImgUrl = activityAttentionMsgImgUrl;
	}

	public String getActivityAttentionMsgRedirectUrl() {
		return activityAttentionMsgRedirectUrl;
	}

	public void setActivityAttentionMsgRedirectUrl(String activityAttentionMsgRedirectUrl) {
		this.activityAttentionMsgRedirectUrl = activityAttentionMsgRedirectUrl;
	}
}
