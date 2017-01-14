package com.sap.jnc.marketing.dto.response.bonus.page;

public class PageConfiguration {

	/**
	 * 默认页面大小
	 */
	public static int DEFAULT_PAGE_SIZE = 20;

	/**
	 * 分页显示的初始化页码数
	 */
	public static int DEFAULT_PAGINATION = 5;

	/**
	 * 分页显示的最大页码数
	 */
	public static int MAX_PAGINATION = 5;

	/**
	 * 分页显示的最大页码数
	 */
	public static int MIN_PAGINATION = 3;

	public static int getDefaultPageSize() {
		return DEFAULT_PAGE_SIZE;
	}

	public static void setDefaultPageSize(int dEFAULT_PAGE_SIZE) {
		DEFAULT_PAGE_SIZE = dEFAULT_PAGE_SIZE;
	}

	public static int getDefaultPagination() {
		return DEFAULT_PAGINATION;
	}

	public static void setDefaultPagination(int dEFAULT_PAGINATION) {
		DEFAULT_PAGINATION = dEFAULT_PAGINATION;
	}

	public static int getMaxPagination() {
		return MAX_PAGINATION;
	}

	public static void setMaxPagination(int mAX_PAGINATION) {
		MAX_PAGINATION = mAX_PAGINATION;
	}

	public static int getMinPagination() {
		return MIN_PAGINATION;
	}

	public static void setMinPagination(int mIN_PAGINATION) {
		MIN_PAGINATION = mIN_PAGINATION;
	}

}
