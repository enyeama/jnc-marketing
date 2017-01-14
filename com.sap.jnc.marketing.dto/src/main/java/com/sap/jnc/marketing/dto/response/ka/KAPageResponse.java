/**
 * 
 */
package com.sap.jnc.marketing.dto.response.ka;

import java.io.Serializable;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.persistence.model.Terminal;

/**
 * @author Quansheng Liu I075496
 */
public class KAPageResponse extends PageImpl<KAResponse> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7081095165603753702L;

	public KAPageResponse(Page<Terminal> pages, PageRequest pageRequest) {
		super(pages.getContent().stream().map(kaTerminal -> new KAResponse(kaTerminal)).collect(Collectors.toList()), pageRequest, pages
			.getTotalElements());
	}
}
