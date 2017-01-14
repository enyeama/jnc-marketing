package com.sap.jnc.marketing.dto.enumeration.wechat;

public enum WechatEventType {

	SUBSCRIBE("SUBSCRIBE", "订阅"), UNSUBSCRIBE("UNSUBSCRIBE", "取消订阅"), SCAN("SCAN", "扫码"), LOCATION("LOCATION", "上报位置"), VIEW("VIEW", "菜单跳转");

	private String value;

	private String label;

	WechatEventType(String value, String label) {
		this.value = value;
		this.label = label;
	}

	public static String getLabel(String value) {
		for (WechatEventType c : WechatEventType.values()) {
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
