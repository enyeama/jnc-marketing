package com.sap.jnc.marketing.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.criteria.dealer.DealerOrdersAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.criteria.kaorder.KAOrderAdvancedSearchKeywordNode;
import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderStatus;
import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderType;
import com.sap.jnc.marketing.persistence.model.TerminalOrder;

@NoRepositoryBean
public interface TerminalOrderRepository extends JpaRepository<TerminalOrder, Long> {

	List<TerminalOrder> findOrders(String terminalId, String salePosExtId, List<TerminalOrderType> orderType);

	List<TerminalOrder> findOrdersByStatus(
		String terminalId, String salePosExtId, TerminalOrderStatus status, List<TerminalOrderType> orderType);

	List<TerminalOrder> findOrdersByLeader(String leaderPosExtId, TerminalOrderStatus status);

	TerminalOrder findOneWithCategory(Long orderId);

	Page<TerminalOrder> advanceKAOrderSearch(KAOrderAdvancedSearchKeywordNode searchCriteria, PageRequest pageRequest);

	Page<TerminalOrder> findOrdersByDealer(DealerOrdersAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest);
	
	List<TerminalOrder> findOrdersByTerminal(String terminalId, TerminalOrderStatus status);
}
