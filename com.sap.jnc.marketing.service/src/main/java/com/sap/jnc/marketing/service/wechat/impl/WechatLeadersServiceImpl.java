package com.sap.jnc.marketing.service.wechat.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.request.wechat.leaders.LeaderOrdersRequest;
import com.sap.jnc.marketing.persistence.model.Position;
import com.sap.jnc.marketing.persistence.model.TerminalOrder;
import com.sap.jnc.marketing.persistence.repository.EmployeeViewRepository;
import com.sap.jnc.marketing.persistence.repository.TerminalOrderRepository;
import com.sap.jnc.marketing.service.wechat.WechatLeadersService;

/**
 * 
 * @author C5245167 Xiao Qi
 *
 */
@Service
@Transactional
public class WechatLeadersServiceImpl implements WechatLeadersService {
	
	@Autowired
	private TerminalOrderRepository terminalOrderRepository;
	
	@Autowired
	private EmployeeViewRepository employeeViewRepository;

	@Override
	@Transactional(readOnly = true)
	public List<TerminalOrder> getOrders(LeaderOrdersRequest request) {
		// get leader position
		Position leaderPosition = employeeViewRepository.findOneByExternalId(request.getLeaderId()).getPosition().getPosition();
		return terminalOrderRepository.findOrdersByLeader(leaderPosition.getExternalId(), request.getStatus());
	}

	@Override
	public TerminalOrder getOrderDetail(LeaderOrdersRequest request) {
		return terminalOrderRepository.findOne(Long.valueOf(request.getOrderId()));
	}
}
