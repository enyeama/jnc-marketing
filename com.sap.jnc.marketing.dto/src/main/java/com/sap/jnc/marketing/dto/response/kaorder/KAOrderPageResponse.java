/**
 * 
 */
package com.sap.jnc.marketing.dto.response.kaorder;

import java.io.Serializable;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.persistence.model.TerminalOrder;

/**
 * @author Quansheng Liu I075496
 */
public class KAOrderPageResponse extends PageImpl<KAOrderListResponsePage> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7081095165603753702L;

	public KAOrderPageResponse(Page<TerminalOrder> pages, PageRequest pageRequest) {
		super(pages.getContent().stream().map(kaOrder -> new KAOrderListResponsePage(kaOrder)).collect(Collectors.toList()), pageRequest, pages
			.getTotalElements());
	}
}
