package com.sap.jnc.marketing.service.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;
import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.model.ProductDmsCategory;
import com.sap.jnc.marketing.persistence.repository.ProductDmsCategoryRepository;
import com.sap.jnc.marketing.persistence.repository.ProductRepository;
import com.sap.jnc.marketing.service.CommonPrint;
import com.sap.jnc.marketing.service.config.ServiceConfigTest2;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServiceConfigTest2.class, PersistenceConfig.class })
public class RepositoryTest extends CommonPrint {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductDmsCategoryRepository dmsCategoryRepository;
	
	@Test
	public void insertMapping(){
		try {
			Product product = productRepository.findOne("1001000012");
			ProductDmsCategory category = dmsCategoryRepository.findOne(10160450L);
			
			List<ProductDmsCategory> list1 = new ArrayList<ProductDmsCategory>();
			list1.add(category);
			product.setProductDmsCategories(list1);
			
			List<Product> list2 = new ArrayList<Product>();
			list2.add(product);
			category.setProducts(list2);
			
			productRepository.save(product);
			dmsCategoryRepository.save(category);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
