package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.CriteriaBuilder.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import com.sap.jnc.marketing.persistence.model.OldIndividualProduct;
import com.sap.jnc.marketing.persistence.model.OldIndividualProduct_;
import com.sap.jnc.marketing.persistence.repository.OldIndividualProductRepository;
/**
 * 
 * @author I324442 Sha Liu
 *
 */
@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OldIndividualProductRepositoryImpl extends SimpleJpaRepository<OldIndividualProduct, Long> implements OldIndividualProductRepository {

	@Autowired
	public OldIndividualProductRepositoryImpl(EntityManager em) {
		super(OldIndividualProduct.class, em);
	}
	
	public List<OldIndividualProduct> findAllByCaseId(String scanCode){
		return super.findAll((root,query,cb)->{
			root.fetch(OldIndividualProduct_.banquetItem, JoinType.LEFT);
			root.fetch(OldIndividualProduct_.product,JoinType.LEFT);
			return cb.equal(root.get(OldIndividualProduct_.caseId), scanCode);
		});	
	}
	
	public OldIndividualProduct findByBoxId(String scanCode){
		return super.findOne((root,query,cb) -> {
			root.fetch(OldIndividualProduct_.banquetItem, JoinType.LEFT);
			root.fetch(OldIndividualProduct_.product,JoinType.LEFT);
			return cb.equal(root.get(OldIndividualProduct_.boxId),scanCode);
		});
	}
	
	public List<OldIndividualProduct> findAllByBoxId(List<String> boxIdList){
		return super.findAll((root, query, cb) -> {
			root.fetch(OldIndividualProduct_.banquetItem, JoinType.LEFT);
			root.fetch(OldIndividualProduct_.product,JoinType.LEFT);
			In<String> inParams = cb.in(root.get(OldIndividualProduct_.boxId));
			for(String boxId : boxIdList){
				inParams.value(boxId);
			}
			return inParams;
		});
	}
}
