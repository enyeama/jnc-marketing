/**
 * 
 */
package com.sap.jnc.marketing.service.position.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.response.terminal.TerminalResponse;
import com.sap.jnc.marketing.persistence.model.DepartmentView;
import com.sap.jnc.marketing.persistence.model.EmployeeView;
import com.sap.jnc.marketing.persistence.model.JobView;
import com.sap.jnc.marketing.persistence.model.PositionView;
import com.sap.jnc.marketing.persistence.model.Terminal;
import com.sap.jnc.marketing.persistence.repository.EmployeeViewRepository;
import com.sap.jnc.marketing.persistence.repository.PositionViewRepository;
import com.sap.jnc.marketing.service.position.PositionService;

/**
 * @author Quansheng Liu I075496
 */
@Service
@Transactional
public class PositionServiceImpl implements PositionService {

	private final static String LEADER_ID = "40";

	@Autowired
	private EmployeeViewRepository employeeViewRepository;

	@Autowired
	private PositionViewRepository positionViewRepository;

	@Override
	@Transactional(readOnly = true)
	public Collection<TerminalResponse> findAllTerminalsByLeaderEmployeeId(long leaderEmployeeId) {
		if (leaderEmployeeId < 1) {
			return Collections.emptyList();
		}
		EmployeeView employeeView = this.employeeViewRepository.findOneById(leaderEmployeeId);
		if (employeeView == null) {
			return Collections.emptyList();
		}
		List<PositionView> positionViewList = employeeView.getPositions();
		PositionView leaderPositionView = getLeaderPosition(positionViewList);
		if (leaderPositionView == null) {
			return Collections.emptyList();
		}
		DepartmentView departmentView = leaderPositionView.getDepartment();
		if (departmentView == null) {
			return Collections.emptyList();
		}
		// PositionView positionView = this.positionViewRepository.findOne(901 + "");
		// return Collections.emptyList();
		List<PositionView> salesmanPositionViewList = this.positionViewRepository.findAllSalesManByDepartmentId(departmentView.getId());
		return listTerminalFromSalesmanPosition(salesmanPositionViewList);
	}

	private List<TerminalResponse> listTerminalFromSalesmanPosition(List<PositionView> salesmanPositionViewList) {
		if (CollectionUtils.isEmpty(salesmanPositionViewList)) {
			return Collections.emptyList();
		}
		List<TerminalResponse> terminalResponseList = new ArrayList<>();
		Set<Long> terminalIdSet = new HashSet<>();
		for (PositionView positionView : salesmanPositionViewList) {
			List<Terminal> terminals = positionView.getTerminals();
			if (CollectionUtils.isEmpty(terminals)) {
				continue;
			}
			for (Terminal terminal : terminals) {
				if (!terminalIdSet.contains(terminal.getId())) {
					terminalIdSet.add(terminal.getId());
					TerminalResponse terminalResponse = new TerminalResponse(terminal);
					terminalResponseList.add(terminalResponse);
				}
			}
		}
		return terminalResponseList;
	}

	private PositionView getLeaderPosition(List<PositionView> positionViewList) {
		if (CollectionUtils.isEmpty(positionViewList)) {
			return null;
		}
		for (PositionView positionView : positionViewList) {
			JobView jobView = positionView.getJob();
			if (jobView != null && LEADER_ID.equals(jobView.getExternalId())) {
				return positionView;
			}
		}
		return null;
	}

	@Override
	public Collection<PositionView> listAllCityManagers() {
		return this.positionViewRepository.findAllCityManagers();
	}

}
