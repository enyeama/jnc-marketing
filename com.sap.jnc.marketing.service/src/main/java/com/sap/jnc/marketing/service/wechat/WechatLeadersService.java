package com.sap.jnc.marketing.service.wechat;

import java.util.List;

import com.sap.jnc.marketing.dto.request.wechat.leaders.LeaderOrdersRequest;
import com.sap.jnc.marketing.persistence.model.TerminalOrder;

/**
 * 
 * @author C5245167 Xiao Qi
 *
 */
public interface WechatLeadersService {

	List<TerminalOrder> getOrders(LeaderOrdersRequest request);
	
	TerminalOrder getOrderDetail(LeaderOrdersRequest request);
}
