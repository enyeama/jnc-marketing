package com.sap.jnc.marketing.service.wechat.impl;

import java.util.ArrayList;
import java.util.List;

import com.sap.jnc.marketing.dto.request.terminal.TerminalRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.request.wechat.ka.KaOrderRequest;
import com.sap.jnc.marketing.dto.request.wechat.ka.KaOrdersRequest;
import com.sap.jnc.marketing.dto.request.wechat.ka.KaTerminalsRequest;
import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderType;
import com.sap.jnc.marketing.persistence.model.Terminal;
import com.sap.jnc.marketing.persistence.model.TerminalOrder;
import com.sap.jnc.marketing.persistence.repository.TerminalOrderRepository;
import com.sap.jnc.marketing.persistence.repository.TerminalRepository;
import com.sap.jnc.marketing.service.wechat.WechatKaService;

/**
 * @author C5245167 Xiao Qi
 */
@Service
@Transactional
public class WechatKaServiceImpl implements WechatKaService {

	@Autowired
	private TerminalRepository terminalRepository;

	@Autowired
	private TerminalOrderRepository terminalOrderRepository;

	@Override
	public List<Terminal> getTerminals(KaTerminalsRequest request) {
		return terminalRepository.findByKaSales(request.getKaSalesId(), request.getTitleKey());
	}

	@Override
	public List<TerminalOrder> getOrders(KaOrdersRequest request) {
		List<TerminalOrderType> orderType = new ArrayList<TerminalOrderType>();
		orderType.add(TerminalOrderType.KAORDER);
		return terminalOrderRepository.findOrders(request.getTerminalId(), request.getSalesmanId(), orderType);
	}

	@Override
	public TerminalOrder getOrder(KaOrderRequest request) {
		return terminalOrderRepository.findOne(Long.valueOf(request.getOrderId()));
	}

	@Override
	public void saveKaInfo(TerminalRequest request) {
		Terminal terminal = terminalRepository.findOne(request.getId());
		terminal = request.toTerminal(terminal);
		terminalRepository.saveAndFlush(terminal);
	}

	@Override
	public Terminal findTerminal(Long kaId) {
		return terminalRepository.findOne(kaId);
	}

}
