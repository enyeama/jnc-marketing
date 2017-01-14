package com.sap.jnc.marketing.service.relationmainten;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.persistence.model.Dealer;
import com.sap.jnc.marketing.persistence.model.PositionView;
import com.sap.jnc.marketing.persistence.model.Product;

/**
 * @author Maggie Liu
 */
public interface RelationMaintenService {

	List<Product> findAllProductsByManager(String id);

	List<Dealer> findAllDealerByManager(String id);

	List<PositionView> findAllLeaders(String id);

	Page<PositionView> findAllDLPs(PageRequest pageRequest);

	boolean createRelation(Long dealerId, String leaderId, String productId);

	boolean deleteRelation(Long dealerId, String leaderId, String productId);

}
