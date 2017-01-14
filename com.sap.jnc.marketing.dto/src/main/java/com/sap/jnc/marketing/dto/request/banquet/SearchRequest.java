package com.sap.jnc.marketing.dto.request.banquet;

import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.persistence.criteria.SearchKeywordNode;

/**
 * @author I071053 Diouf Du
 * @param <TSearchKeywordNode>
 */
public interface SearchRequest<TSearchKeywordNode extends SearchKeywordNode> {

	TSearchKeywordNode getSearchKeyword();

	PageRequest getPageRequest();

}