package com.sap.jnc.marketing.api.admin.web.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sap.jnc.marketing.dto.response.kadd.DmsNameResponse;
import com.sap.jnc.marketing.dto.response.kadd.KATerminalDetailResponse;
import com.sap.jnc.marketing.dto.response.kadd.KATerminalNameResponse;
import com.sap.jnc.marketing.dto.response.kadd.ProductNameResponse;
import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.model.ProductDmsCategory;
import com.sap.jnc.marketing.persistence.model.Terminal;
import com.sap.jnc.marketing.service.kadd.KADDService;
/*
 * @author Maggie Liu
 * */

@RestController
public class KADDController extends GeneralController{
	@Autowired
	private KADDService kaddService;
	
	@RequestMapping(value = "/kadd/katerminalnames", method = RequestMethod.GET)
	public @ResponseBody Collection<KATerminalNameResponse> findTerminalNames(@RequestParam String externalId){
		List<Terminal> terminals=kaddService.findKAByKASpecialExternalId(externalId);
		if(!CollectionUtils.isEmpty(terminals)){
			return terminals.stream().map(terminal -> new KATerminalNameResponse(terminal)).collect(Collectors.toList());
		}
		return Collections.emptyList() ;
		
	}
	
	@RequestMapping(value = "/kadd/katerminaldetails", method = RequestMethod.GET)
	public @ResponseBody KATerminalDetailResponse findTerminaldetails(@RequestParam String externalId,@RequestParam String kaName){
		List<Terminal> terminals=kaddService.findKAByKAName(externalId,kaName);
		if(!CollectionUtils.isEmpty(terminals)){
			Terminal terminal=terminals.get(0);
			if(terminal!=null){
				return new KATerminalDetailResponse(terminal);
			}
		}
		
		return null;
	}
	
	@RequestMapping(value="/kadd/dmscategorys",method=RequestMethod.GET)
	public @ResponseBody Collection<DmsNameResponse> findAllDMSs(){
		List<ProductDmsCategory> productDmsCategories=kaddService.findAllDMSCategorys();
		if(!CollectionUtils.isEmpty(productDmsCategories)){
			return productDmsCategories.stream().map(dms->new DmsNameResponse(dms)).collect(Collectors.toList());
		}
		return Collections.emptyList() ;
	}
	
	@RequestMapping(value="/kadd/productnames",method=RequestMethod.GET)
	public @ResponseBody Collection<ProductNameResponse> findProductsByDmsName(@RequestParam String dmsName){
		List<Product> products=kaddService.findProductsByDmsName(dmsName);
		if(!CollectionUtils.isEmpty(products)){
			return products.stream().map(product->new ProductNameResponse(product)).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}
}
