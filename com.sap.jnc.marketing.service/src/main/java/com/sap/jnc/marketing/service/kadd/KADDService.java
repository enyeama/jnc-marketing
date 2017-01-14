package com.sap.jnc.marketing.service.kadd;

import java.util.List;

import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.model.ProductDmsCategory;
import com.sap.jnc.marketing.persistence.model.Terminal;

public interface KADDService {
	List<Terminal> findKAByKASpecialExternalId(String externakId);
	List<ProductDmsCategory> findAllDMSCategorys();
	List<Terminal> findKAByKAName(String kaSpecialExternalId,String kaName);
	List<Product> findProductsByDmsName(String dmsName);
}
