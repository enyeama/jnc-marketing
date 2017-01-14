package com.sap.jnc.marketing.api.admin.web.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sap.jnc.marketing.dto.request.citymanager.config.ProductCategoryRelationRequest;
import com.sap.jnc.marketing.dto.request.citymanager.config.RelationSettingRequest;
import com.sap.jnc.marketing.dto.response.ProductCategoryRelationResponse;
import com.sap.jnc.marketing.dto.response.RelationSettingResponse;
import com.sap.jnc.marketing.service.citymanager.config.RelationSettingService;

/**
 * @author I071053 Diouf Du
 */
@RestController
@RequestMapping("/citymanager")
public class CityManagerConfigController {

	private static Logger LOGGER = LoggerFactory.getLogger(CityManagerConfigController.class);

	@Autowired
	private RelationSettingService relationSettingService;

	@RequestMapping(value = { "/position/config" }, method = { RequestMethod.POST })
	public void createRelation(@RequestBody RelationSettingRequest relationRequest) {
		this.relationSettingService.createRelation(relationRequest);
	}

	@RequestMapping(value = { "/position/config" }, method = { RequestMethod.PUT })
	public void updateRelation(@RequestBody RelationSettingRequest relationRequest) {
		this.relationSettingService.updateRelation(relationRequest);
	}

	@RequestMapping(value = { "/position/config" }, method = { RequestMethod.DELETE })
	public void deleteRelation(@RequestBody RelationSettingRequest relationRequest) {
		this.relationSettingService.deleteRelation(relationRequest);
	}

	@RequestMapping(value = { "/position/config" }, method = { RequestMethod.GET })
	public Collection<RelationSettingResponse> listRelation() {
		return this.relationSettingService.findAllRelations();
	}

	@RequestMapping(value = { "/productcategory/config" }, method = { RequestMethod.POST })
	public void createProductCategoryRelation(@RequestBody ProductCategoryRelationRequest relationRequest) {
		this.relationSettingService.createProductCategoryRelation(relationRequest);
	}

	@RequestMapping(value = { "/productcategory/config" }, method = { RequestMethod.PUT })
	public void updateProductCategoryRelation(@RequestBody ProductCategoryRelationRequest relationRequest) {
		this.relationSettingService.updateProductCategoryRelation(relationRequest);
	}

	@RequestMapping(value = { "/productcategory/config" }, method = { RequestMethod.DELETE })
	public void deleteProductCategoryRelation(@RequestBody ProductCategoryRelationRequest relationRequest) {
		this.relationSettingService.deleteProductCategoryRelation(relationRequest);
	}

	@RequestMapping(value = { "/productcategory/config" }, method = { RequestMethod.GET })
	public Collection<ProductCategoryRelationResponse> listProductCategoryRelation() {
		return this.relationSettingService.findAllProductCategoryRelations();
	}
}
