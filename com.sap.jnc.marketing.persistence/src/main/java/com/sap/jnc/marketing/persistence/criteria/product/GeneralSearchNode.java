package com.sap.jnc.marketing.persistence.criteria.product;

import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.persistence.criteria.SearchKeywordNode;

public class GeneralSearchNode implements SearchKeywordNode {

	private static final long serialVersionUID = 1814141034699653939L;

	private Map<String, Object> filters;

	private Map<String, String> sorts;

	private Map<String, Object> pagination;

	public Map<String, Object> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}

	public Map<String, String> getSorts() {
		return sorts;
	}

	public void setSorts(Map<String, String> sorts) {
		this.sorts = sorts;
	}

	public Map<String, Object> getPagination() {
		return pagination;
	}

	public void setPagination(Map<String, Object> pagination) {
		this.pagination = pagination;
	}

	/**
	 * 获得page request
	 * 
	 * @return
	 */
	public PageRequest getPageRequest() {
		if (pagination == null) {
			return new PageRequest(1, 10);
		}
		int page = MapUtils.getIntValue(pagination, "page");
		int size = MapUtils.getIntValue(pagination, "size");
		page = page < 0 ? 0 : page;
		size = size < 0 ? 10 : size;
		PageRequest pageRequest = new PageRequest(page, size);
		return pageRequest;
	}
}
