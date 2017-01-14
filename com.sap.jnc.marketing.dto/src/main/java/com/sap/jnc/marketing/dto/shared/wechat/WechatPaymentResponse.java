package com.sap.jnc.marketing.dto.shared.wechat;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("WechatPaymentResponse")
public class WechatPaymentResponse extends WechatPaymentNotifyResponse {

	@XStreamAlias("trade_state")
	private String tradeState;

	@XStreamAlias("trade_state_desc")
	private String tradeStateDesc;

	public String getTradeState() {
		return tradeState;
	}

	public void setTradeState(String tradeState) {
		this.tradeState = tradeState;
	}

	public String getTradeStateDesc() {
		return tradeStateDesc;
	}

	public void setTradeStateDesc(String tradeStateDesc) {
		this.tradeStateDesc = tradeStateDesc;
	}

	@Override
	public String toString() {
		return "WechatPaymentResponse{" +
				"tradeState='" + tradeState + '\'' +
				", tradeStateDesc='" + tradeStateDesc + '\'' +
				'}';
	}
}
