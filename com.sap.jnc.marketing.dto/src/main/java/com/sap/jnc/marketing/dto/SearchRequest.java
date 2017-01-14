package com.sap.jnc.marketing.dto;

import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.persistence.criteria.SearchKeywordNode;

/**
 * @author I071053 Diouf Du
 * @param <TSearchCriteria>
 */
public interface SearchRequest<TSearchCriteria extends SearchKeywordNode> {

	TSearchCriteria getKeywords();

	PageRequest getPageRequest();

}