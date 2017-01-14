package com.sap.jnc.marketing.dto.response.banquet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.persistence.model.BanquetVerifyApplication;

/**
 * @author I332242 Zhu Qiang
 */
public class BanquetVfyApplicationPageResponse extends PageImpl<BanquetVerifyApplicationResponse> implements Serializable {

	private static final long serialVersionUID = 6824889108470865103L;

	private static final List<BanquetVerifyApplicationResponse> EMPTY_LIST = new ArrayList<>(0);

	public BanquetVfyApplicationPageResponse(PageRequest pageRequest) {
		super(
			// Content
			EMPTY_LIST,
			// Page Request
			pageRequest,
			// Total
			0);
	}

	public BanquetVfyApplicationPageResponse(Page<BanquetVerifyApplication> pages, PageRequest pageRequest) {
		super(
			// Content
			pages.getContent().stream().map(banquetVfyApplication -> new BanquetVerifyApplicationResponse(banquetVfyApplication)).collect(Collectors.toList()),
			// Page Request
			pageRequest,
			// Total
			pages.getTotalElements());
	}
}