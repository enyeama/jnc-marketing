package com.sap.jnc.marketing.service.exception.wechat;

import org.apache.commons.lang3.StringUtils;

public class WechatTemplateMessageException extends RuntimeException {

	private static final long serialVersionUID = -6803128408360342313L;

	protected String errorMessage;

	public WechatTemplateMessageException(String errorMessage) {
		this.setErrorMessage(errorMessage);
	}

	public WechatTemplateMessageException(String errorMessage, Exception ex) {
		super(ex);
		this.setErrorMessage(errorMessage);
	}

	public WechatTemplateMessageException(Exception ex) {
		super(ex);
	}

	public WechatTemplateMessageException(Throwable thr) {
		super(thr);
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public String getMessage() {
		if (StringUtils.isBlank(this.errorMessage)) {
			return super.getMessage();
		}
		else {
			return this.errorMessage;
		}
	}

	protected void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
