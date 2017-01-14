package com.sap.jnc.marketing.persistence.repository;

import java.util.Collection;
import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.enumeration.MovementType;
import com.sap.jnc.marketing.persistence.model.Logistic;

@NoRepositoryBean
public interface LogisticRepository extends JpaRepository<Logistic, Long> {

	List<Logistic> findForDealerInCase(List<String> caseIds);

	List<Logistic> findForDealerOutCase(List<String> caseIds);

	List<Logistic> findByErpOrderIDAndDmsOrderID(String erpOrderID, String dmsOrderID);

	Logistic findPreLogisticByProductCapInnerCode(String capInnerCode);

	List<Tuple> listDealerLogisticStatus(Long dealId);

	List<Logistic> findByCaseID(String caseID);

	List<Logistic> findByCapInnerCodeAndMovementType(String capInnerCode, MovementType movementType);

	List<Logistic> findByCapInnerCodesAndMovementType(Collection<String> capinnercodes, MovementType movementType);

	List<Logistic> findByCaseIdsAndMovementType(Collection<String> caseIds, MovementType movementType);
}
