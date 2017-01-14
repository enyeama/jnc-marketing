package com.sap.jnc.marketing.persistence.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.IndividualProduct;

@NoRepositoryBean
public interface IndividualProductRepository extends JpaRepository<IndividualProduct, String> {
	public List<IndividualProduct> findByCaseID(String caseID);

	public List<IndividualProduct> findByBoxIDorCaseID(String id);

	public List<IndividualProduct> findAllByCaseId(String scanCode);

	public IndividualProduct findByBoxId(String scanCode);

	public List<IndividualProduct> findAllByBoxId(List<String> boxIdList);

	public List<IndividualProduct> findByBoxID(String boxID);

	public List<IndividualProduct> findByBottleOID(String bottleOID);

	public List<IndividualProduct> findByCapInnerCodes(Collection<String> capInnerCodes);

	public List<IndividualProduct> findByCapOuterCodes(Collection<String> capOuterCodes);

	public List<IndividualProduct> findByBoxIds(Collection<String> boxIds);

	public List<IndividualProduct> findByCaseIds(Collection<String> CaseIds);
}
