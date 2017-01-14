package com.sap.jnc.marketing.api.admin.web.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sap.jnc.marketing.dto.request.GeneralSearchRequest;
import com.sap.jnc.marketing.dto.request.mainten.SalesDMSRelationRequest;
import com.sap.jnc.marketing.dto.respose.mainten.MaintenDMSthirdResponse;
import com.sap.jnc.marketing.dto.respose.mainten.MaintenSalesCategoryResponse;
import com.sap.jnc.marketing.dto.respose.mainten.MaintenSalesDMSRelationResponse;
import com.sap.jnc.marketing.persistence.model.ProductDmsCategory;
import com.sap.jnc.marketing.persistence.model.ProductSalesCategory;
import com.sap.jnc.marketing.service.relationmainten.SalesCategoryDMSService;

/**
 * @author Maggie Liu
 */

@RestController
public class SalesCategoryDMSController extends GeneralController{
	@Autowired
	SalesCategoryDMSService salesCategoryDMSService;
	
	@RequestMapping(value = "/salescategorydmsmaintenance/salescateory", method = RequestMethod.GET)
	public @ResponseBody Collection<MaintenSalesCategoryResponse>  getSalesCategory(){
		List<ProductSalesCategory> productSalesCategories=salesCategoryDMSService.findAllSalesCategoriesByCityManager();
		if(!CollectionUtils.isEmpty(productSalesCategories)){
			return productSalesCategories.stream().map(sales->new MaintenSalesCategoryResponse(sales)).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}
	
	@RequestMapping(value = "/salescategorydmsmaintenance/thirddms", method = RequestMethod.GET)
	public @ResponseBody Collection<MaintenDMSthirdResponse>  getProductDMSthirdlevel(){
		List<ProductDmsCategory> productDmsCategories=salesCategoryDMSService.findAllProductDmsCategories();
		if(!CollectionUtils.isEmpty(productDmsCategories)){
			return productDmsCategories.stream().map(dms->new MaintenDMSthirdResponse(dms)).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}
	
	@RequestMapping(value="/salescategorydmsmaintenance/relationships", method = RequestMethod.POST)
	public @ResponseBody boolean createRelation(@RequestBody SalesDMSRelationRequest salesDMSRelationRequest){
		return salesCategoryDMSService.createSaleDMSRelation(salesDMSRelationRequest.getSalesExternalId(), salesDMSRelationRequest.getDmsId());
	}
	
	@RequestMapping(value="/salescategorydmsmaintenance/relationships/page", method = RequestMethod.POST)
	public @ResponseBody MaintenSalesDMSRelationResponse findAllRelations( @Valid @RequestBody GeneralSearchRequest<?> searchRequest){
		final PageRequest pageRequest = searchRequest.getPageRequest();
		final Page<ProductDmsCategory> pages = this.salesCategoryDMSService.getSalesDMSRelations(pageRequest);
		if ((pages == null) || CollectionUtils.isEmpty(pages.getContent())) {
			return new MaintenSalesDMSRelationResponse(pageRequest);
		}
		
		return new MaintenSalesDMSRelationResponse(pages, pageRequest);
	}
	
	@RequestMapping(value="/salescategorydmsmaintenance/relationships/config", method = RequestMethod.POST)
	public @ResponseBody boolean relationConfig(@RequestBody SalesDMSRelationRequest salesDMSRelationRequest){
		return salesCategoryDMSService.deleteRelations(salesDMSRelationRequest.getSalesExternalId(), salesDMSRelationRequest.getDmsId());
		
	}
}
