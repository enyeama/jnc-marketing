package com.sap.jnc.marketing.infrastructure.shared.constant;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.function.Supplier;

/**
 * Created by dy on 16/6/21.
 */
public class ApiResult {

	public static final ApiResult SUCCESS = new ApiResult(MessageId.SUCCESS, "成功");

	public static final ApiResult NOT_FOUND = new ApiResult(MessageId.NORMAL_PHONE_ALREADY_EXISTED, "not found");

	private String code;

	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	private String message;

	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	private Object result;

	public ApiResult() {
	}

	public ApiResult(String code) {
		super();
		this.code = code;
	}

	public ApiResult(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public ApiResult(String code, String message, Object result) {
		super();
		this.code = code;
		this.message = message;
		this.result = result;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public static ApiResultBuilder successBuilder() {
		return new ApiResultBuilder(MessageId.SUCCESS);
	}

	public static class ApiResultBuilder implements Supplier<ApiResult> {

		private final String code;
		private String message;
		private Object result;

		private ApiResultBuilder(String code) {
			this.code = code;
		}

		public ApiResultBuilder message(String message) {
			this.message = message;
			return this;
		}

		public ApiResultBuilder result(Object result) {
			this.result = result;
			return this;
		}

		@Override
		public ApiResult get() {
			return new ApiResult(code, message, result);
		}

	}
}
