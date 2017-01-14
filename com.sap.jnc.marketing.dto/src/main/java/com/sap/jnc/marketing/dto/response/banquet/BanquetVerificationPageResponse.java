package com.sap.jnc.marketing.dto.response.banquet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.sap.jnc.marketing.persistence.model.BanquetVerification;

/**
 * @author Joel.Cheng I310645
 *
 */
public class BanquetVerificationPageResponse extends PageImpl<BanquetVerificationResponse> implements Serializable {

	private static final long serialVersionUID = 1710877512634696652L;

	/**
	 * @param content
	 */
	public BanquetVerificationPageResponse(List<BanquetVerificationResponse> content) {
		super(content);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param content
	 * @param pageable
	 * @param total
	 */
	public BanquetVerificationPageResponse(List<BanquetVerificationResponse> content, Pageable pageable, long total) {
		super(content, pageable, total);
		// TODO Auto-generated constructor stub
	}
	
	private static final List<BanquetVerificationResponse> EMPTY_LIST = new ArrayList<>(0);

	public BanquetVerificationPageResponse(PageRequest pageRequest) {
		super(
			// Content
			EMPTY_LIST,
			// Page Request
			pageRequest,
			// Total
			0);
	}

	public BanquetVerificationPageResponse(Page<BanquetVerification> pages, PageRequest pageRequest) {
		super(
			// Content
			pages.getContent().stream().map(banquetVerification -> new BanquetVerificationResponse(banquetVerification)).collect(Collectors.toList()),
			// Page Request
			pageRequest,
			// Total
			pages.getTotalElements());
	}

}
