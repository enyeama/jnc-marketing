package com.sap.jnc.marketing.service.wechat;

import java.util.List;

import com.sap.jnc.marketing.dto.request.terminal.TerminalRequest;
import com.sap.jnc.marketing.dto.request.wechat.ka.KaOrderRequest;
import com.sap.jnc.marketing.dto.request.wechat.ka.KaOrdersRequest;
import com.sap.jnc.marketing.dto.request.wechat.ka.KaTerminalsRequest;
import com.sap.jnc.marketing.persistence.model.Terminal;
import com.sap.jnc.marketing.persistence.model.TerminalOrder;

/**
 * @author C5245167 Xiao Qi
 */
public interface WechatKaService {

	List<Terminal> getTerminals(KaTerminalsRequest request);

	List<TerminalOrder> getOrders(KaOrdersRequest request);

	TerminalOrder getOrder(KaOrderRequest request);

	void saveKaInfo(TerminalRequest request);

	Terminal findTerminal(Long kaId);
}
