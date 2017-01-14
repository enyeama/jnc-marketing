package com.sap.jnc.marketing.dto.request.banquet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.Transformer;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sap.jnc.marketing.persistence.criteria.SearchKeywordNode;

public class GeneralSearchRequest<TSearchKeywordNode extends SearchKeywordNode> implements Serializable, SearchRequest<TSearchKeywordNode> {

	private static final long serialVersionUID = 2140903818462535400L;

	protected Page page;

	public static class Page {

		protected static final int DEFAULT_PAGE_INDEX = 0;
		protected static final int DEFAULT_PAGE_SIZE = 10;

		@Min(0)
		protected int index;

		@Min(1)
		@Max(300)
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

	protected Map<String, Direction> sort = new LinkedHashMap<String, Direction>();

	protected TSearchKeywordNode criteria;

	@JsonIgnore
	protected PageRequest pageRequest;

	@Override
	public PageRequest getPageRequest() {
		if (this.getPage() == null) {
			this.setPage(new Page());
		}
		if (MapUtils.isEmpty(this.getSort())) {
			return new PageRequest(this.getPage().getIndex(), this.getPage().getSize());

		}
		else {
			return new PageRequest(this.getPage().getIndex(), this.getPage().getSize(), new Sort(new ArrayList<Order>(CollectionUtils.collect(this
				.getSort().entrySet(), (Transformer<Entry<String, Direction>, Order>) orderProperty -> new Order(orderProperty.getValue(), orderProperty.getKey())))));
		}
	}

	@Override
	public TSearchKeywordNode getSearchKeyword() {
		return this.criteria;
	}

	public void setCriteria(TSearchKeywordNode criteria) {
		this.criteria = criteria;
	}

	public Page getPage() {
		return this.page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Map<String, Direction> getSort() {
		return this.sort;
	}

	public void setSorts(Map<String, Direction> sort) {
		this.sort = sort;
	}

}
