package com.sap.jnc.marketing.service.terminalorder;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sap.jnc.marketing.dto.request.terminalorder.DealerLeaderSearchRequest;
import com.sap.jnc.marketing.dto.response.kaorder.DeliveryNodeResponse;
import com.sap.jnc.marketing.dto.shared.kaorder.TerminalOrderInfo;
import com.sap.jnc.marketing.persistence.criteria.kaorder.KAOrderAdvancedSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.TerminalOrder;

/**
 * @author Joel.Cheng I310645
 */
@Service
public interface TerminalOrderService {
	Page<TerminalOrder> advanceKAOrderSearch(KAOrderAdvancedSearchKeywordNode searchCriteria, PageRequest pageRequest);

	TerminalOrderInfo createKAOrder(TerminalOrderInfo terminalOrderRequest);

	TerminalOrderInfo updateKAOrder(TerminalOrderInfo terminalOrderRequest);

	void cancelKAOrder(Long id);

	TerminalOrderInfo getKAOrder(Long id);

	Collection<DeliveryNodeResponse> findCityManagersByKaOfficeExternalId(String kaOfficeExternalId);

	Collection<DeliveryNodeResponse> findMaintainersByCityManagerPositionExternalId(String cityManagerPositionExternalId);

	Collection<DeliveryNodeResponse> findLeadersByCityManagerPositionExternalId(DealerLeaderSearchRequest dealerLeaderRequest);
}
