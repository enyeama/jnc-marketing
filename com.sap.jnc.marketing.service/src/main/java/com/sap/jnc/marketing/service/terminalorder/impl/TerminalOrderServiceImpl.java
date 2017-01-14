package com.sap.jnc.marketing.service.terminalorder.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.request.terminalorder.DealerLeaderSearchRequest;
import com.sap.jnc.marketing.dto.response.kaorder.DeliveryNodeResponse;
import com.sap.jnc.marketing.dto.shared.kaorder.DealerInfo;
import com.sap.jnc.marketing.dto.shared.kaorder.PositionInfo;
import com.sap.jnc.marketing.dto.shared.kaorder.ProductInfo;
import com.sap.jnc.marketing.dto.shared.kaorder.TerminalInfo;
import com.sap.jnc.marketing.dto.shared.kaorder.TerminalOrderInfo;
import com.sap.jnc.marketing.persistence.criteria.kaorder.KAOrderAdvancedSearchKeywordNode;
import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderStatus;
import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderType;
import com.sap.jnc.marketing.persistence.model.Contract;
import com.sap.jnc.marketing.persistence.model.Dealer;
import com.sap.jnc.marketing.persistence.model.DepartmentView;
import com.sap.jnc.marketing.persistence.model.Employee;
import com.sap.jnc.marketing.persistence.model.EmployeeView;
// import com.sap.jnc.marketing.persistence.model.IntegratingMarketing;
import com.sap.jnc.marketing.persistence.model.Position;
import com.sap.jnc.marketing.persistence.model.PositionView;
import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.model.Terminal;
import com.sap.jnc.marketing.persistence.model.TerminalOrder;
import com.sap.jnc.marketing.persistence.repository.ContractRepository;
import com.sap.jnc.marketing.persistence.repository.DealerRepository;
import com.sap.jnc.marketing.persistence.repository.DepartmentViewRepository;
import com.sap.jnc.marketing.persistence.repository.EmployeeRepository;
import com.sap.jnc.marketing.persistence.repository.EmployeeViewRepository;
// import com.sap.jnc.marketing.persistence.repository.IntegratingMarketingRepository;
import com.sap.jnc.marketing.persistence.repository.PositionRepository;
import com.sap.jnc.marketing.persistence.repository.PositionViewRepository;
import com.sap.jnc.marketing.persistence.repository.ProductRepository;
import com.sap.jnc.marketing.persistence.repository.TerminalOrderRepository;
import com.sap.jnc.marketing.persistence.repository.TerminalRepository;
import com.sap.jnc.marketing.service.exception.validation.SpecifiedEntityNotFoundException;
import com.sap.jnc.marketing.service.terminalorder.TerminalOrderService;

import me.chanjar.weixin.common.util.StringUtils;

/**
 * @author Quansheng Liu I075496
 */
@Service
@Transactional
public class TerminalOrderServiceImpl implements TerminalOrderService {

	private final static String KA_SPECIALIST_JOB_EXTERNAL_ID = "90";
	private final static String KA_CITY_MANAGER_JOB_EXTERNAL_ID = "30";

	@Autowired
	private TerminalOrderRepository teriminalOrderRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private TerminalRepository terminalRepository;

	@Autowired
	private DealerRepository dealerRepository;

	@Autowired
	private ContractRepository contractRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeViewRepository employeeViewRepository;

	@Autowired
	private PositionRepository positionRepository;

	@Autowired
	private PositionViewRepository positionViewRepository;

	@Autowired
	private DepartmentViewRepository departmentViewRepository;

	@Override
	public Page<TerminalOrder> advanceKAOrderSearch(KAOrderAdvancedSearchKeywordNode searchCriteria, PageRequest pageRequest) {
		if (searchCriteria.getKaSpecialistPositionId() == null) {
			PositionView kaSpecialistPositionView = this.employeeViewRepository.findPositionByEmployeeIdAndJobType(searchCriteria
				.getKaSpecialistEmployeeId(), KA_SPECIALIST_JOB_EXTERNAL_ID);
			if (kaSpecialistPositionView != null) {
				searchCriteria.setKaSpecialistPositionId(kaSpecialistPositionView.getId());
			}
		}
		return this.teriminalOrderRepository.advanceKAOrderSearch(searchCriteria, pageRequest);
	}

	@Override
	@Transactional
	public TerminalOrderInfo createKAOrder(TerminalOrderInfo terminalOrderRequest) {
		TerminalOrder terminalOrder = new TerminalOrder();
		terminalOrder.setType(TerminalOrderType.KAORDER);
		this.mergeKAOrder(terminalOrder, terminalOrderRequest);

		terminalOrder.setId(null);

		terminalOrder = this.teriminalOrderRepository.saveAndFlush(terminalOrder);
		return new TerminalOrderInfo(terminalOrder);
	}

	@Override
	@Transactional
	public TerminalOrderInfo updateKAOrder(TerminalOrderInfo terminalOrderRequest) {

		if (terminalOrderRequest == null || terminalOrderRequest.getId() < 1) {
			return null;
		}
		TerminalOrder terminalOrder = this.teriminalOrderRepository.findOne(terminalOrderRequest.getId());
		if (terminalOrder == null || terminalOrder.getType() != TerminalOrderType.KAORDER) {
			return null;
		}

		this.mergeKAOrder(terminalOrder, terminalOrderRequest);

		terminalOrder = this.teriminalOrderRepository.saveAndFlush(terminalOrder);

		return new TerminalOrderInfo(terminalOrder);

	}

	/**
	 * @author Joel.Cheng I310645
	 */
	@Override
	@Transactional
	public void cancelKAOrder(Long id) {
		TerminalOrder terminalOrder = this.teriminalOrderRepository.findOne(id);
		if (terminalOrder == null || terminalOrder.getType() != TerminalOrderType.KAORDER || terminalOrder
			.getStatus() == TerminalOrderStatus.CANCEL) {
			throw new SpecifiedEntityNotFoundException(TerminalOrder.class, id);
		}
		terminalOrder.setStatus(TerminalOrderStatus.CANCEL);
		this.teriminalOrderRepository.saveAndFlush(terminalOrder);
	}

	/**
	 * @author Joel.Cheng I310645
	 */
	@Override
	@Transactional(readOnly = true)
	public TerminalOrderInfo getKAOrder(Long id) {
		TerminalOrder terminalOrder = this.teriminalOrderRepository.findOne(id);
		if (terminalOrder == null) {
			return null;
		}
		return new TerminalOrderInfo(terminalOrder);
	}

	@Override
	public Collection<DeliveryNodeResponse> findCityManagersByKaOfficeExternalId(String kaOfficeExternalId) {
		return this.findSubordinateHeadsByDepartmentExternalId(kaOfficeExternalId);
	}

	@Override
	public Collection<DeliveryNodeResponse> findMaintainersByCityManagerPositionExternalId(String cityManagerPositionExternalId) {
		if (StringUtils.isEmpty(cityManagerPositionExternalId)) {
			return Collections.emptyList();
		}
		PositionView cityManagerPosition = this.positionViewRepository.findOne(cityManagerPositionExternalId);
		if (cityManagerPosition == null || cityManagerPosition.getDepartment() == null) {
			return Collections.emptyList();
		}
		return this.findSubordinateNotHeadsByDepartmentExternalId(cityManagerPosition.getDepartment().getExternalId());
	}

	@Override
	public Collection<DeliveryNodeResponse> findLeadersByCityManagerPositionExternalId(DealerLeaderSearchRequest dealerLeaderRequest) {
		String cityManagerPositionExternalId = dealerLeaderRequest.getCityManagerPositionExternalId();
		List<DeliveryNodeResponse> leaders = new ArrayList<>();
		if (!StringUtils.isEmpty(cityManagerPositionExternalId)) {
			PositionView positionView = this.positionViewRepository.findOne(cityManagerPositionExternalId);
			if (positionView != null) {
				DepartmentView departmentView = positionView.getDepartment();
				if (departmentView != null) {
					leaders = this.findSubordinateHeadsByDepartmentExternalId(departmentView.getExternalId());
				}
			}
		}
		if (CollectionUtils.isEmpty(leaders)) {
			return this.findNonPlatformDealers(dealerLeaderRequest);
		}
		else {
			List<DeliveryNodeResponse> nonPlatformDealers = this.findNonPlatformDealers(dealerLeaderRequest);
			leaders.addAll(nonPlatformDealers);
		}
		return leaders;
	}

	private void mergeKAOrder(TerminalOrder terminalOrder, TerminalOrderInfo terminalOrderInfo) {
		terminalOrder.setId(terminalOrderInfo.getId());
		terminalOrder.setStatus(terminalOrderInfo.getStatus());
		terminalOrder.setCreaterType(terminalOrderInfo.getCreaterType());
		terminalOrder.setCreaterId(terminalOrderInfo.getCreaterId());
		terminalOrder.setComplaint(terminalOrderInfo.getComplaint());

		ProductInfo productInfo = terminalOrderInfo.getProductInfo();
		Product originProduct = terminalOrder.getProduct();
		if (productInfo == null || originProduct == null || !productInfo.getId().equals(originProduct.getId())) {
			if (productInfo == null || StringUtils.isEmpty(productInfo.getId())) {
				terminalOrder.setProduct(null);
			}
			else {
				Product product = productRepository.findOne(productInfo.getId());
				terminalOrder.setProduct(product);
			}
		}

		terminalOrder.setQuantity(terminalOrderInfo.getQuantity());

		TerminalInfo terminalInfo = terminalOrderInfo.getTerminalInfo();
		Terminal originTerminal = terminalOrder.getTerminal();
		if (terminalInfo == null || originTerminal == null || terminalInfo.getId() != originTerminal.getId()) {
			if (terminalInfo == null || terminalInfo.getId() < 1) {
				terminalOrder.setTerminal(null);
			}
			else {
				Terminal terminal = terminalRepository.findOne(terminalInfo.getId());
				terminalOrder.setTerminal(terminal);
			}
		}

		DealerInfo dealerInfo = terminalOrderInfo.getDealerInfo();
		Dealer originDealer = terminalOrder.getDealer();
		if (dealerInfo == null || originDealer == null || dealerInfo.getId() != originDealer.getId()) {
			if (dealerInfo == null || dealerInfo.getId() == null || dealerInfo.getId().longValue() < 1) {
				terminalOrder.setDealer(null);
			}
			else {
				Dealer dealer = dealerRepository.findOne(dealerInfo.getId());
				terminalOrder.setDealer(dealer);
			}
		}

		PositionInfo responsibleLeaderPositionInfo = terminalOrderInfo.getResponsibleLeaderPositionInfo();
		Position originResponsibleLeaderPosition = terminalOrder.getResponsibleLeaderPosition();
		if (originResponsibleLeaderPosition == null || responsibleLeaderPositionInfo == null || originResponsibleLeaderPosition
			.getId() != responsibleLeaderPositionInfo.getId()) {
			if (responsibleLeaderPositionInfo == null || responsibleLeaderPositionInfo.getId() == null || responsibleLeaderPositionInfo.getId()
				.longValue() < 1) {
				terminalOrder.setResponsibleLeaderPosition(null);
				terminalOrder.setResponsibleLeader(null);
			}
			else {
				Position responsibleLeaderPosition = positionRepository.findOne(responsibleLeaderPositionInfo.getId());
				PositionView responsibleLeaderPositionView = null;
				if (responsibleLeaderPosition != null) {
					responsibleLeaderPositionView = this.positionViewRepository.findOne(responsibleLeaderPosition.getExternalId());
				}
				terminalOrder.setResponsibleLeaderPosition(responsibleLeaderPosition);
				EmployeeView employeeView = null;
				if (responsibleLeaderPositionView != null) {
					employeeView = responsibleLeaderPositionView.getEmployee();
				}
				if (employeeView != null) {
					Employee employee = this.employeeRepository.findOne(employeeView.getId());
					terminalOrder.setResponsibleLeader(employee);
				}
				else {
					terminalOrder.setResponsibleLeader(null);
				}
			}
		}

		terminalOrder.setComment(terminalOrderInfo.getComment());

		PositionInfo cityManagerPositionInfo = terminalOrderInfo.getCityManagerPositionInfo();
		Position originCityManagerPosition = terminalOrder.getCityManagerPosition();
		if (originCityManagerPosition == null || cityManagerPositionInfo == null || originCityManagerPosition.getId() != cityManagerPositionInfo
			.getId()) {
			if (cityManagerPositionInfo == null || cityManagerPositionInfo.getId() == null || cityManagerPositionInfo.getId().longValue() < 1) {
				terminalOrder.setCityManagerPosition(null);
				terminalOrder.setCityManagerEmployee(null);
			}
			else {
				Position cityManagerPosition = positionRepository.findOne(cityManagerPositionInfo.getId());
				PositionView cityManagerPositionView = null;
				if (cityManagerPosition != null) {
					cityManagerPositionView = this.positionViewRepository.findOne(cityManagerPosition.getExternalId());
				}
				terminalOrder.setCityManagerPosition(cityManagerPosition);
				EmployeeView employeeView = null;
				if (cityManagerPositionView != null) {
					employeeView = cityManagerPositionView.getEmployee();
				}
				if (employeeView != null) {
					Employee employee = this.employeeRepository.findOne(employeeView.getId());
					terminalOrder.setCityManagerEmployee(employee);
				}
				else {
					terminalOrder.setCityManagerEmployee(null);
				}
			}
		}

		PositionInfo createrPositionInfo = terminalOrderInfo.getCreaterPositionInfo();
		Position originCreaterPosition = terminalOrder.getCreaterPosition();
		if (originCreaterPosition == null || createrPositionInfo == null || originCreaterPosition.getId() != createrPositionInfo.getId()) {
			if (createrPositionInfo == null || createrPositionInfo.getId() == null || createrPositionInfo.getId().longValue() < 1) {
				terminalOrder.setCreaterPosition(null);
				terminalOrder.setCreaterEmployee(null);
			}
			else {
				Position createrPosition = positionRepository.findOne(createrPositionInfo.getId());
				PositionView createrPositionView = null;
				if (createrPosition != null) {
					createrPositionView = this.positionViewRepository.findOne(createrPosition.getExternalId());
				}
				terminalOrder.setCreaterPosition(createrPosition);
				EmployeeView employeeView = null;
				if (createrPositionView != null) {
					employeeView = createrPositionView.getEmployee();
				}
				if (employeeView != null) {
					Employee employee = this.employeeRepository.findOne(employeeView.getId());
					terminalOrder.setCreaterEmployee(employee);
				}
				else {
					terminalOrder.setCreaterEmployee(null);
				}
			}
		}
	}

	private List<DeliveryNodeResponse> findSubordinateHeadsByDepartmentExternalId(String departmentExternalId) {
		return this.findSubordinatesByDepartmentExternalId(departmentExternalId, Boolean.TRUE);
	}

	private List<DeliveryNodeResponse> findSubordinateNotHeadsByDepartmentExternalId(String departmentExternalId) {
		return this.findSubordinatesByDepartmentExternalId(departmentExternalId, Boolean.FALSE);
	}

	private List<DeliveryNodeResponse> findSubordinatesByDepartmentExternalId(String departmentExternalId, Boolean isHead) {
		if (StringUtils.isEmpty(departmentExternalId)) {
			return Collections.emptyList();
		}
		DepartmentView departmentView = this.departmentViewRepository.findOne(departmentExternalId);
		if (departmentView == null || CollectionUtils.isEmpty(departmentView.getSubordinate())) {
			return Collections.emptyList();
		}
		List<DepartmentView> subordinates = departmentView.getSubordinate();
		List<DeliveryNodeResponse> subordinateHeads = new ArrayList<>(subordinates.size());
		Set<Long> positionIdSet = new HashSet<>();
		for (DepartmentView subordinate : subordinates) {
			List<PositionView> positionViewList = subordinate.getPositions();
			if (CollectionUtils.isEmpty(positionViewList)) {
				continue;
			}
			for (PositionView positionView : positionViewList) {
				if (positionView.getIsHead() == isHead && !positionIdSet.contains(positionView.getId())) {
					positionIdSet.add(positionView.getId());
					subordinateHeads.add(new DeliveryNodeResponse(positionView));
				}
			}
		}
		return subordinateHeads;
	}

	private List<DeliveryNodeResponse> findNonPlatformDealers(DealerLeaderSearchRequest dealerLeaderRequest) {
		Long terminalId = dealerLeaderRequest.getTerminalId();
		Long regionId = dealerLeaderRequest.getRegionId();
		Long productId = dealerLeaderRequest.getProductId();
		Long channelId = dealerLeaderRequest.getChannelId();
		if (regionId == null && channelId == null && terminalId != null) {
			Terminal terminal = this.terminalRepository.findOne(terminalId);
			if (terminal == null) {
				return Collections.emptyList();
			}
			regionId = terminal.getRegion() == null ? null : terminal.getRegion().getId();
			channelId = terminal.getChannel() == null ? null : terminal.getChannel().getId();
		}
		if (regionId == null || regionId.longValue() < 1 || productId == null || productId.longValue() < 1 || channelId == null || channelId
			.longValue() < 1) {
			return Collections.emptyList();
		}
		Collection<Contract> contracts = this.contractRepository.findContractByTerminalOrderInfo(channelId, productId, regionId);
		if (CollectionUtils.isEmpty(contracts)) {
			return Collections.emptyList();
		}
		List<DeliveryNodeResponse> responseList = new ArrayList<>();
		for (Contract contract : contracts) {
			Dealer dealer = contract.getDealer();
			if (dealer != null && dealer.getIsPlatformDealer() == Boolean.FALSE) {
				DeliveryNodeResponse dealerResponse = new DeliveryNodeResponse(dealer);
				responseList.add(dealerResponse);
			}
		}
		return responseList;
	}

}
