package com.sap.jnc.marketing.dto.request;

import java.io.Serializable;

import org.springframework.data.domain.PageRequest;

/**
 * @author I071053 Diouf Du
 */
public interface SearchRequest<TTSearchKeywords extends Serializable> {

	TTSearchKeywords getKeywords();

	PageRequest getPageRequest();

}