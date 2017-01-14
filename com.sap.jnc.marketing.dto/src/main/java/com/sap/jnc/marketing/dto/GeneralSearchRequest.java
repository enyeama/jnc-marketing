package com.sap.jnc.marketing.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sap.jnc.marketing.persistence.criteria.SearchKeywordNode;

public class GeneralSearchRequest<TSearchKeywordNode extends SearchKeywordNode> implements Serializable, SearchRequest<TSearchKeywordNode> {

	private static final long serialVersionUID = 2140903818462535400L;

	protected Paging paging;

	protected Map<String, Direction> sort = new LinkedHashMap<String, Direction>();

	protected TSearchKeywordNode keywords;

	@JsonIgnore
	protected PageRequest pageRequest;

	public static class Paging {

		protected static final int DEFAULT_PAGE_INDEX = 0;
		protected static final int DEFAULT_PAGE_SIZE = 60;

		@Min(0)
		protected int index;

		@Min(0)
		@Max(999)
		protected int size;

		public int getIndex() {
			return this.index;
		}

		public void setIndex(int index) {
			if (index < 0) {
				this.index = DEFAULT_PAGE_INDEX;
			}
			else {
				this.index = index;
			}
		}

		public int getSize() {
			return this.size;
		}

		public void setSize(int size) {
			if (size < 1) {
				this.size = DEFAULT_PAGE_SIZE;
			}
			else {
				this.size = size;
			}
		}
	}

	@Override
	public PageRequest getPageRequest() {
		if (this.getPaging() == null) {
			this.setPaging(new Paging());
		}
		if (MapUtils.isEmpty(this.getSort())) {
			return new PageRequest(this.getPaging().getIndex(), this.getPaging().getSize());
		}
		else {
			return new PageRequest(
				// Index
				this.getPaging().getIndex(),
				// Size
				this.getPaging().getSize(),
				// Sort
				new Sort(new ArrayList<Order>(CollectionUtils.collect(CollectionUtils.select(this.getSort().entrySet(),
					// Sort - Not Null
					(Predicate<Entry<String, Direction>>) orderProperty
					// Sort - Not Null
					-> StringUtils.isNotBlank(orderProperty.getKey()) && (orderProperty.getValue() != null)),
					// Sort - Order
					(Transformer<Entry<String, Direction>, Order>) orderProperty
					// Sort - Order
					-> new Order(orderProperty.getValue(), orderProperty.getKey())))));
		}
	}

	@Override
	public TSearchKeywordNode getKeywords() {
		return this.keywords;
	}

	public void setKeywords(TSearchKeywordNode keywords) {
		this.keywords = keywords;
	}

	public Paging getPaging() {
		return this.paging;
	}

	public void setPaging(Paging paging) {
		this.paging = paging;
	}

	public Map<String, Direction> getSort() {
		return this.sort;
	}

	public void setSorts(Map<String, Direction> sort) {
		this.sort = sort;
	}
}
