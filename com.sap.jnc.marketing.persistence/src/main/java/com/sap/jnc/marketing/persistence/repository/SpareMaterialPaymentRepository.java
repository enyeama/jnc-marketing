package com.sap.jnc.marketing.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.criteria.sparematerial.SpareMaterialPaymentSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.SpareMaterialPayment;

@NoRepositoryBean
public interface SpareMaterialPaymentRepository extends JpaRepository<SpareMaterialPayment, Long> {
	public List<SpareMaterialPayment> findByIdAndPositionId(String id, String positionId);

	public List<SpareMaterialPayment> findById(String id);

	public List<SpareMaterialPayment> findByPositionId(String positionId);

	public Page<SpareMaterialPayment> pagedQuery(SpareMaterialPaymentSearchKeywordNode keywords, PageRequest pageRequest);

}
