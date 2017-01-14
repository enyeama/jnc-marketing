package com.sap.jnc.marketing.service.relationmainten.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.respose.mainten.MaintenSalesmanResponse;
import com.sap.jnc.marketing.persistence.model.DepartmentView;
import com.sap.jnc.marketing.persistence.model.EmployeeView;
import com.sap.jnc.marketing.persistence.model.JobView;
import com.sap.jnc.marketing.persistence.model.PositionView;
import com.sap.jnc.marketing.persistence.model.Region;
import com.sap.jnc.marketing.persistence.model.Terminal;
import com.sap.jnc.marketing.persistence.repository.DepartmentViewRepository;
import com.sap.jnc.marketing.persistence.repository.EmployeeViewRepository;
import com.sap.jnc.marketing.persistence.repository.PositionViewRepository;
import com.sap.jnc.marketing.persistence.repository.RegionRepository;
import com.sap.jnc.marketing.persistence.repository.RelationMaintenRepository;
import com.sap.jnc.marketing.persistence.repository.TerminalRepository;
import com.sap.jnc.marketing.service.relationmainten.LeaderTerminalService;

/**
 * @author Maggie Liu
 */
@Service
@Transactional
public class LeaderTernimalServiceImpl implements LeaderTerminalService {

	private final static String LEADER_ID = "576";

	@Autowired
	private EmployeeViewRepository employeeViewRepository;

	@Autowired
	@Qualifier("departmentViewRepositoryImpl")
	private DepartmentViewRepository departmentRepository;

	@Autowired
	private TerminalRepository terminalRepository;

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	RelationMaintenRepository relationMaintenRepository;

	@Autowired
	@Qualifier("positionViewRepositoryImpl")
	PositionViewRepository positionViewRepository;

	@Override
	@Transactional(readOnly = true)
	public Collection<MaintenSalesmanResponse> findAllSalesmansByLeaderEmployeeId(String leaderEmployeeId) {

		final List<MaintenSalesmanResponse> maintenSalesmanResponses = new ArrayList<MaintenSalesmanResponse>();

		// 根据id找到employee
		final EmployeeView employeeView = this.employeeViewRepository.findOneByExternalId(leaderEmployeeId);
		if (employeeView == null) {
			return Collections.emptyList();
		}
		// 根据employee匹配到position
		final List<PositionView> positionViewList = employeeView.getPositions();
		final PositionView leaderPositionView = this.getLeaderPosition(positionViewList);

		if (leaderPositionView != null) {
			// 找到组长department
			final DepartmentView department = leaderPositionView.getDepartment();
			if (department != null) {
				// 找到department下所有position
				List<PositionView> salesmans = new ArrayList<PositionView>();
				salesmans = department.getPositions();

				for (PositionView salsmanposition : salesmans) {
					EmployeeView tmpEmployee = this.getSalesmanEmployeeView(salsmanposition);
					if (tmpEmployee != null) {
						MaintenSalesmanResponse maintenSalesmanResponse = new MaintenSalesmanResponse();
						maintenSalesmanResponse.setJobId(salsmanposition.getExternalId());
						maintenSalesmanResponse.setName(tmpEmployee.getName());
						maintenSalesmanResponse.setPhone(tmpEmployee.getPhone());

						maintenSalesmanResponses.add(maintenSalesmanResponse);
					}

				}
				return maintenSalesmanResponses;
			}
		}
		return Collections.emptyList();
	}

	private PositionView getLeaderPosition(List<PositionView> positionViewList) {
		if (CollectionUtils.isEmpty(positionViewList)) {
			return null;
		}
		for (final PositionView positionView : positionViewList) {
			final JobView jobView = positionView.getJob();
			if ((jobView != null) && LEADER_ID.equals(jobView.getExternalId())) {
				return positionView;
			}
		}
		return null;
	}

	//
	private EmployeeView getSalesmanEmployeeView(PositionView salesman) {
		// 过滤得到业务员positionview
		if (!salesman.getIsHead()) {
			// 从position转到employee
			EmployeeView e = salesman.getEmployee();
			if (e != null) {
				return e;
			}
		}

		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Terminal> findAllTerminalsByLeaderEmployeeId(String leaderEmployeeId) {
		// // 1.根据id找到employee
		// final EmployeeView employeeView = this.employeeViewRepository.findOneByExternalId(leaderEmployeeId);
		// if (employeeView == null) {
		// return Collections.emptyList();
		// }
		// // 2.根据employee匹配到position
		// final List<PositionView> positionViewList = employeeView.getPositions();
		// final PositionView leaderPositionView = this.getLeaderPosition(positionViewList);

		PositionView leaderPositionView = relationMaintenRepository.findLeaderPositionViewByEmployeeId(leaderEmployeeId);
		if (leaderPositionView == null) {
			return Collections.emptyList();
		}
		// 3.根据position找到上级citymanager
		// 3.1 找组长的department
		// DepartmentView leaderDepartment = leaderPositionView.getDepartment();
		DepartmentView leaderDepartment = departmentRepository.findByPositionId(leaderPositionView.getExternalId());
		if (leaderDepartment == null) {
			return Collections.emptyList();
		}
		// 3.2 找department上级department
		final DepartmentView superLeaderDepartment = leaderDepartment.getSuperior();
		if (superLeaderDepartment == null) {
			return Collections.emptyList();
		}
		// // 3.3找department的所有职位，并剔出岗位是城市经理的
		// final List<PositionView> superPositionViews = superLeaderDepartment.getPositions();
		// if (CollectionUtils.isEmpty(superPositionViews)) {
		// return Collections.emptyList();
		// }
		// PositionView citymanagerPositionView = null;
		// for (final PositionView p : superPositionViews) {
		// final JobView jobView = p.getJob();
		// if ((jobView != null) && CITY_MANAGER_ID.equals(jobView.getExternalId())) {
		// citymanagerPositionView = p;
		// break;
		// }
		// }
		PositionView citymanagerPositionView = relationMaintenRepository.findCitymanagerByDepartment(superLeaderDepartment.getExternalId());

		// 4.根据citymanager找terminal
		// 利用citymanager.region.cityId=terminal.region.cityId
		if (citymanagerPositionView == null) {
			return Collections.emptyList();
		}
		List<Region> citymanageRegions = regionRepository.findAllByPositionId(citymanagerPositionView.getId()); // citymanagerPositionView.getRegions();
		if (CollectionUtils.isEmpty(citymanageRegions)) {
			return Collections.emptyList();
		}
		final List<Terminal> terminals = new ArrayList<Terminal>();
		for (Region region : citymanageRegions) {
			if (region != null) {
				List<Terminal> tmpTerminals = terminalRepository.findTerminalsByCityManager(region.getCityId());
				if (CollectionUtils.isEmpty(tmpTerminals)) {
					continue;
				}
				terminals.addAll(tmpTerminals);
			}
		}
		if (!CollectionUtils.isEmpty(terminals)) {
			return terminals;
		}
		return Collections.emptyList();
	}

	@Override
	public boolean createSalesmanTerminalRelation(String salesmanId, Long terminalId) {
		final PositionView salesman = this.relationMaintenRepository.findPositionViewById(salesmanId);
		final Terminal terminal = this.terminalRepository.findTerminalById(terminalId);
		if ((salesman != null) && (terminal != null)) {
			if (!this.isRelationExsist(terminal, salesman)) {
				salesman.getTerminals().add(terminal);
				terminal.getSalesmen().add(salesman);
				this.relationMaintenRepository.saveAndFlush(salesman);
				this.terminalRepository.saveAndFlush(terminal);

				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<MaintenSalesmanResponse> findAllSalesmenByTerminalId(Long terminalId) {
		final Terminal terminal = this.terminalRepository.findTerminalById(terminalId);
		final Collection<MaintenSalesmanResponse> salesmenResponses = new ArrayList<MaintenSalesmanResponse>();
		if (terminal != null) {
			Collection<PositionView> salesmen = relationMaintenRepository.findsalesmenByterminal(terminal.getExternalId());
			if (!CollectionUtils.isEmpty(salesmen)) {
				for (PositionView s : salesmen) {
					EmployeeView e = s.getEmployee();
					if (e != null) {
						MaintenSalesmanResponse maintenSalesmanResponse = new MaintenSalesmanResponse();
						maintenSalesmanResponse.setJobId(s.getExternalId());
						maintenSalesmanResponse.setName(e.getName());
						maintenSalesmanResponse.setPhone(e.getPhone());
						salesmenResponses.add(maintenSalesmanResponse);
					}
					continue;
				}

				return salesmenResponses;
			}
		}

		return Collections.emptyList();
	}

	@Override
	public boolean deleteSalesmanTerminalRelation(String salesmanId, Long terminalId) {
		final PositionView salesman = this.relationMaintenRepository.findPositionViewById(salesmanId);
		final Terminal terminal = this.terminalRepository.findTerminalById(terminalId);
		if ((salesman != null) && (terminal != null)) {
			// is exsist
			if (!CollectionUtils.isEmpty(salesman.getTerminals()) && !CollectionUtils.isEmpty(terminal.getSalesmen())) {
				if (this.isRelationExsist(terminal, salesman)) {
					terminal.getSalesmen().remove(this.terminalHasSalesman(terminal, salesman));
					salesman.getTerminals().remove(this.salesmanHasTerminal(terminal, salesman));

					this.terminalRepository.saveAndFlush(terminal);
					this.positionViewRepository.saveAndFlush(salesman);

					return true;
				}

			}

		}
		return false;
	}

	boolean isRelationExsist(Terminal terminal, PositionView salesman) {
		if ((this.terminalHasSalesman(terminal, salesman) < 0) || (this.salesmanHasTerminal(terminal, salesman) < 0)) {
			return false;
		}
		return true;
	}

	int terminalHasSalesman(Terminal terminal, PositionView salesman) {
		final List<String> salesmenIds = new ArrayList<String>();

		for (final PositionView p : terminal.getSalesmen()) {
			salesmenIds.add(p.getExternalId());
		}

		for (int i = 0; i < salesmenIds.size(); i++) {
			if (salesmenIds.get(i).equals(salesman.getExternalId())) {
				return i;
			}
		}
		return -1;
	}

	int salesmanHasTerminal(Terminal terminal, PositionView salesman) {
		final List<Long> terminalIds = new ArrayList<Long>();
		for (final Terminal t : salesman.getTerminals()) {
			terminalIds.add(t.getId());
		}

		for (int i = 0; i < terminalIds.size(); i++) {
			if (terminalIds.get(i).equals(terminal.getId())) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public Terminal findOneTerminalByTerminalId(Long terminalId) {
		final Terminal terminal = this.terminalRepository.findTerminalById(terminalId);
		if (terminal != null) {
			return terminal;
		}
		return terminal;
	}

	@Override
	public Collection<MaintenSalesmanResponse> findAllSalesmansByLeaderEmployeeId(long leaderEmployeeId) {
		// TODO Auto-generated method stub
		return null;
	}

}
