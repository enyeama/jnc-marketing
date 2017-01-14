package com.sap.jnc.marketing.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.OldIndividualProduct;

@NoRepositoryBean
public interface OldIndividualProductRepository extends JpaRepository<OldIndividualProduct, Long> {
	public List<OldIndividualProduct> findAllByCaseId(String scanCode);
	public OldIndividualProduct findByBoxId(String scanCode);
	public List<OldIndividualProduct> findAllByBoxId(List<String> boxIdList);

}
