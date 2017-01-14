package com.sap.jnc.marketing.infrastructure.shared.constant;

/**
 * e.ex.mm.xxxx 表示一般错误(可通过公共异常处理处理异常,处理方式包括exception拦截和ajax拦截跳转到统一错误页面) e.ex.sys.xxxx 表示系统异常(统一跳转到系统异常提示界面,处理方式包括exception拦截何ajax跳转到统一错误页面)
 * e.ex.alert.xxxx 表示需要页面弹窗提示的错误
 */
public class MessageId {

	private MessageId() {
		// NOP
	}

	// e.ex.mm.xxxx 一般错误
	public static final String SUCCESS = "e.ex.mm.0000";// sucess

	public static final String E_EX_MM_1001 = "e.ex.mm.1001";// not authorized

	public static final String NORMAL_PHONE_ALREADY_EXISTED = "e.ex.mm.1002";// duplicate phone number.

	public static final String NORMAL_NOT_FOUND = "e.ex.mm.404";// not found.

	public static final String SMS_CODE_ERROR = "e.ex.mm.1002";//短信验证码不正确

	// e.ex.sys.xxxx

	// e.ex.alert.xxxx


}