package com.sap.jnc.marketing.persistence.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.PositionView;

/**
 * @author I071053 Diouf Du
 */
@NoRepositoryBean
public interface PositionViewRepository extends JpaRepository<PositionView, String> {
	List<PositionView> findByIds(Collection<Long> ids);

	List<PositionView> findByExternalIds(Collection<String> externalIds);

	List<PositionView> findAllSalesManByDepartmentId(Long departmentId);

	PositionView findOneByPositionId(String id);

	List<PositionView> findPositionViewByexternalIds(Collection<String> positionIdSet);

	Collection<PositionView> findAllCityManagers();

	PositionView findLeadersBySalesId(String saleExtId);
}
