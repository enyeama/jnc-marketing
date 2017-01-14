package com.sap.jnc.marketing.service.dealer;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.dto.request.dealer.DealerOrderRequest;
import com.sap.jnc.marketing.persistence.criteria.dealer.DealerAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.criteria.dealer.DealerOrdersAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.Dealer;
import com.sap.jnc.marketing.persistence.model.EmployeeView;
import com.sap.jnc.marketing.persistence.model.TerminalOrder;

/**
 * @author
 */
public interface DealerService {

	List<EmployeeView> findAllDealerLeaders(String dealerId);

	Page<Dealer> advanceSearch(DealerAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest);

	Page<TerminalOrder> getOrders(DealerOrdersAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest);

	TerminalOrder cancelOrder(DealerOrderRequest request);
}
