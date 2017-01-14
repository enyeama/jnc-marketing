/**
 *
 */
package com.sap.jnc.marketing.service.citymanager.config;

import java.util.List;

import com.sap.jnc.marketing.dto.request.citymanager.config.ProductCategoryRelationRequest;
import com.sap.jnc.marketing.dto.request.citymanager.config.RelationSettingRequest;
import com.sap.jnc.marketing.dto.request.citymanager.config.SalesmanTerminalRelationRequest;
import com.sap.jnc.marketing.dto.response.ProductCategoryRelationResponse;
import com.sap.jnc.marketing.dto.response.RelationSettingResponse;
import com.sap.jnc.marketing.dto.response.SalesmanTerminalRelationResponse;

/**
 * @author Quansheng Liu I075496
 */
public interface RelationSettingService {

	List<RelationSettingResponse> findAllRelations();

	List<SalesmanTerminalRelationResponse> findAllPositionTerminalRelations();

	List<ProductCategoryRelationResponse> findAllProductCategoryRelations();

	void createRelation(RelationSettingRequest relationRequest);

	void updateRelation(RelationSettingRequest relationRequest);

	void deleteRelation(RelationSettingRequest relationRequest);

	void createSalesmanTerminalRelation(SalesmanTerminalRelationRequest relationRequest);

	void updateSalesmanTerminalRelation(SalesmanTerminalRelationRequest relationRequest);

	void deleteSalesmanTerminalRelation(SalesmanTerminalRelationRequest relationRequest);

	void createProductCategoryRelation(ProductCategoryRelationRequest relationRequest);

	void updateProductCategoryRelation(ProductCategoryRelationRequest relationRequest);

	void deleteProductCategoryRelation(ProductCategoryRelationRequest relationRequest);

}
