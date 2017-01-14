package com.sap.jnc.marketing.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.criteria.sparematerial.SpareMaterialDeliverySearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.SpareMaterialDelivery;

@NoRepositoryBean
public interface SpareMaterialDeliveryRepository extends JpaRepository<SpareMaterialDelivery, Long> {
	public Page<SpareMaterialDelivery> pagedQuery(SpareMaterialDeliverySearchKeywordNode spareMaterialDeliverySearchKeywordNode, PageRequest pageRequest);

	public List<SpareMaterialDelivery> findPositionIds();
}
