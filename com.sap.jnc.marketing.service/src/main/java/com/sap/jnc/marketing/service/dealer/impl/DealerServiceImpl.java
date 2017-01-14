package com.sap.jnc.marketing.service.dealer.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.request.dealer.DealerOrderRequest;
import com.sap.jnc.marketing.persistence.criteria.dealer.DealerAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.criteria.dealer.DealerOrdersAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderStatus;
import com.sap.jnc.marketing.persistence.model.Dealer;
import com.sap.jnc.marketing.persistence.model.EmployeeView;
import com.sap.jnc.marketing.persistence.model.PositionView;
import com.sap.jnc.marketing.persistence.model.TerminalOrder;
import com.sap.jnc.marketing.persistence.repository.DealerRepository;
import com.sap.jnc.marketing.persistence.repository.TerminalOrderRepository;
import com.sap.jnc.marketing.service.dealer.DealerService;

/**
 * @author I071053 Diouf Du
 */
@Service
@Transactional
public class DealerServiceImpl implements DealerService {

	@Autowired
	private DealerRepository dealerRepository;
	
	@Autowired
	private TerminalOrderRepository terminalOrderRepository;

	@Override
	@Transactional(readOnly = true)
	public Page<Dealer> advanceSearch(DealerAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest) {
		return this.dealerRepository.advanceSearch(searchCriteria, pageRequest);
	}

	@Override
	@Transactional(readOnly = true)
	public List<EmployeeView> findAllDealerLeaders(String dealerId) {
		List<EmployeeView> employees = new ArrayList<EmployeeView>();
		Dealer dealer = dealerRepository.findDealerWithLeaders(dealerId);
		if (dealer != null) {
			List<PositionView> positions = dealer.getLeaders();
			if (positions != null) {
				for (PositionView position : positions) {
					employees.addAll(position.getEmployees());
				}
			}
		}
		return employees;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TerminalOrder> getOrders(DealerOrdersAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest) {
		return terminalOrderRepository.findOrdersByDealer(searchCriteria, pageRequest);
	}

	@Override
	@Transactional
	public TerminalOrder cancelOrder(DealerOrderRequest request) {
		TerminalOrder entity = terminalOrderRepository.findOne(Long.valueOf(request.getOrderId()));
		entity.setStatus(TerminalOrderStatus.CANCEL);
		return terminalOrderRepository.saveAndFlush(entity);
	}
}
