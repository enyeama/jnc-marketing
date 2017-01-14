package com.sap.jnc.marketing.dto.enumeration.wechat;

public enum WechatMessageType {

	EVENT("事件", "event"), TEXT("文本", "text"), IMAGE("图片", "image"), LOCATION("位置", "location"), link("链接", "link"), voice("声音", "voice"), shortvideo(
		"小视频", "shortvideo"), music("音乐", "music"), transfer_customer_service("客服系统", "event");

	private String value;

	private String label;

	WechatMessageType(String value, String label) {
		this.value = value;
		this.label = label;
	}

	public static String getLabel(String value) {
		for (WechatMessageType c : WechatMessageType.values()) {
			if (c.getValue().equals(value)) {
				return c.label;
			}
		}
		return null;
	}

	public String getValue() {

		return value;
	}

	public String getLabel() {
		return label;
	}

}
