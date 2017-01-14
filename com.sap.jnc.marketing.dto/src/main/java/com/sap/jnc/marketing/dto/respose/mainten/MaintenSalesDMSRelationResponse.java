package com.sap.jnc.marketing.dto.respose.mainten;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.persistence.model.ProductDmsCategory;

/**
 * @author Maggie Liu
 */

public class MaintenSalesDMSRelationResponse extends PageImpl<MaintenSalesDMSRelationResponse.Item> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final List<Item> EMPTY_LIST = new ArrayList<>(0);
	public MaintenSalesDMSRelationResponse(Page<ProductDmsCategory> pages, PageRequest pageRequest) {
		super(// Content
			pages.getContent().stream().map(dms -> new Item(dms)).collect(Collectors.toList()),
			// Page Request
			pageRequest,
			// Total
			pages.getTotalElements());

	}
	public MaintenSalesDMSRelationResponse(PageRequest pageRequest) {
		super(
			// Content
			EMPTY_LIST,
			// Page Request
			pageRequest,
			// Total
			0);
	}
	
	public static class Item implements Serializable {
		private static final long serialVersionUID = 698881806897304110L;
		String salesCategoryId;
		String salesCategoryName;
		String dmsId;
		String dmsName;

		public Item(ProductDmsCategory productDmsCategory) {
			if(productDmsCategory==null){
				return;
			}
			if(productDmsCategory.getProductSalesCategory()==null){
				return;
			}
			if(productDmsCategory.getProductSalesCategory().getExternalId()!=null){
				this.salesCategoryId = productDmsCategory.getProductSalesCategory().getExternalId();
			}
			if(productDmsCategory.getProductSalesCategory().getName()!=null){
				this.salesCategoryName = productDmsCategory.getProductSalesCategory().getName();
			}
			if(productDmsCategory.getId()!=null){
				this.dmsId = productDmsCategory.getId();
			}
			if(productDmsCategory.getName()!=null){
				this.dmsName = productDmsCategory.getName();
			}
		}

		public String getSalesCategoryId() {
			return salesCategoryId;
		}

		public void setSalesCategoryId(String salesCategoryId) {
			this.salesCategoryId = salesCategoryId;
		}

		public String getSalesCategoryName() {
			return salesCategoryName;
		}

		public void setSalesCategoryName(String salesCategoryName) {
			this.salesCategoryName = salesCategoryName;
		}

		public String getDmsId() {
			return dmsId;
		}

		public void setDmsId(String dmsId) {
			this.dmsId = dmsId;
		}

		public String getDmsName() {
			return dmsName;
		}

		public void setDmsName(String dmsName) {
			this.dmsName = dmsName;
		}

	}
}
