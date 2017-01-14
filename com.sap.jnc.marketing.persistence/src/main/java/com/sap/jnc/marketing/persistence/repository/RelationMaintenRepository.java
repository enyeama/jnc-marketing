package com.sap.jnc.marketing.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.PositionView;

/**
 * @author Maggie Liu
 */
@NoRepositoryBean
public interface RelationMaintenRepository extends JpaRepository<PositionView, String> {
	List<PositionView> findLeaderByDepartment(String id);

	PositionView findPositionViewById(String id);

	Page<PositionView> findAllDLPRecords(PageRequest pageRequest);

	List<PositionView> findAllDLPRecords();
	
	PositionView findLeaderPositionViewByEmployeeId(String leaderEmployeeId);
	
	PositionView findCitymanagerByDepartment(String departmentId);
	
	List<PositionView> findsalesmenByterminal(String terminalId);
}
