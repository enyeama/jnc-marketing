package com.sap.jnc.marketing.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.enumeration.BanquetApplicationType;
import com.sap.jnc.marketing.persistence.model.Banquet;
import com.sap.jnc.marketing.persistence.model.BanquetRebateConfig;

/**
 * @author C5205383 Kevin Ren
 *         I332242  Zhu Qiang
 */
@NoRepositoryBean
public interface BanquetRebateConfigRepository extends JpaRepository<BanquetRebateConfig, Long> {

	public List<BanquetRebateConfig> queryByScanedProduct(Banquet banquet,
			String productId);
	
	public List<BanquetRebateConfig> findByScanedProduct(BanquetApplicationType banquetApplicationType);

}
