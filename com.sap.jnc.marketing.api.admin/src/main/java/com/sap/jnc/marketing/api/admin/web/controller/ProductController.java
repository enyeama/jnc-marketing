package com.sap.jnc.marketing.api.admin.web.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.sap.jnc.marketing.dto.response.channel.ChannelResponse;
import com.sap.jnc.marketing.dto.response.product.ProductDmsCategoryResponse;
import com.sap.jnc.marketing.dto.response.product.ProductErpCategoryResponse;
import com.sap.jnc.marketing.dto.response.product.ProductGroupResponse;
import com.sap.jnc.marketing.dto.response.product.ProductResponse;
import com.sap.jnc.marketing.dto.response.product.ProductTypeResponse;
import com.sap.jnc.marketing.persistence.criteria.product.GeneralSearchNode;
import com.sap.jnc.marketing.service.config.migration.UploadRow;
import com.sap.jnc.marketing.service.exception.migration.ValidationRecordException;
import com.sap.jnc.marketing.service.migration.ProductService;

/**
 * 物料主数据
 * 
 * @author I322359
 */
@RestController
public class ProductController extends GeneralController {

	private static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/migration/product/imports", method = RequestMethod.POST, headers = "content-type=multipart/*")
	public Map<String, List<? extends UploadRow>> imports(@RequestParam("file") MultipartFile file) throws Exception {
		Map<String, List<? extends UploadRow>> map = null;
		try {
			productService.imports(file.getInputStream());
		}catch(ValidationRecordException e){
			return e.getMap();
		}
		catch (Exception e) {
			throw e;
		}
		return map;
	}

	@RequestMapping(value = "/migration/product/type", method = RequestMethod.GET)
	public List<ProductTypeResponse> getProductTypes() {
		return productService.getProductTypes().stream().map(productType -> new ProductTypeResponse(productType)).collect(Collectors.toList());
	}

	@RequestMapping(value = "/migration/product/group", method = RequestMethod.GET)
	public List<ProductGroupResponse> getProductGroups() {
		return productService.getProductGroups().stream().map(productGroup -> new ProductGroupResponse(productGroup)).collect(Collectors.toList());
	}

	@RequestMapping(value = "/migration/productchannel", method = RequestMethod.GET)
	public List<ChannelResponse> getChannels() {
		return productService.getChannels().stream().map(channel -> new ChannelResponse(channel)).collect(Collectors.toList());
	}

	@RequestMapping(value = "/migration/product/dmscategory", method = RequestMethod.GET)
	public List<ProductDmsCategoryResponse> getProductDmsCategories() {
		return productService.getProductDmsCategories().stream().map(category -> new ProductDmsCategoryResponse(category)).collect(Collectors
			.toList());
	}

	@RequestMapping(value = "/migration/product/erpcategory", method = RequestMethod.GET)
	public List<ProductErpCategoryResponse> getProductErpCategories() {
		return productService.getProductErpCategories().stream().map(category -> new ProductErpCategoryResponse(category)).collect(Collectors
			.toList());
	}

	@RequestMapping(value = "/migration/product", method = RequestMethod.POST)
	public Page<ProductResponse> getProducts(@RequestBody GeneralSearchNode searchNode) {
		LOGGER.info("获得物料主数据信息");
		LOGGER.debug(JSONObject.toJSONString(searchNode));
		return productService.advanceSearch(searchNode);
	}
}
