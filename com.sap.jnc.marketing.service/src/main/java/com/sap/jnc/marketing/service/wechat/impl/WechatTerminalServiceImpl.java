package com.sap.jnc.marketing.service.wechat.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.request.wechat.terminal.TerminalOrdersRequest;
import com.sap.jnc.marketing.persistence.model.TerminalOrder;
import com.sap.jnc.marketing.persistence.repository.TerminalOrderRepository;
import com.sap.jnc.marketing.service.wechat.WechatTerminalService;

/**
 * @author C5245167 Xiao Qi
 */
@Service
@Transactional
public class WechatTerminalServiceImpl implements WechatTerminalService {
	
	@Autowired
	private TerminalOrderRepository terminalOrderRepository;

	@Override
	public List<TerminalOrder> getOrders(TerminalOrdersRequest request) {
		return terminalOrderRepository.findOrdersByTerminal(request.getTerminalId(), request.getStatus());
	}

	@Override
	public TerminalOrder viewOrder(TerminalOrdersRequest request) {
		return terminalOrderRepository.findOne(Long.valueOf(request.getOrderId()));
	}

}
