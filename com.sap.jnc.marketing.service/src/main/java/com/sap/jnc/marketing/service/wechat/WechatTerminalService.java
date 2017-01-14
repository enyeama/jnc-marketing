package com.sap.jnc.marketing.service.wechat;

import java.util.List;

import com.sap.jnc.marketing.dto.request.wechat.terminal.TerminalOrdersRequest;
import com.sap.jnc.marketing.persistence.model.TerminalOrder;

/**
 * @author C5245167 Xiao Qi
 */
public interface WechatTerminalService {

	List<TerminalOrder> getOrders(TerminalOrdersRequest request);
	
	TerminalOrder viewOrder(TerminalOrdersRequest request);
}
