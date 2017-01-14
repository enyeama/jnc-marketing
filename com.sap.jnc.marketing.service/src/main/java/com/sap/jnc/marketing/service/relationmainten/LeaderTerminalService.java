package com.sap.jnc.marketing.service.relationmainten;

import java.util.Collection;

import com.sap.jnc.marketing.dto.respose.mainten.MaintenSalesmanResponse;
import com.sap.jnc.marketing.persistence.model.Terminal;

/**
 * @author Maggie Liu
 */
public interface LeaderTerminalService {

	Collection<MaintenSalesmanResponse> findAllSalesmansByLeaderEmployeeId(long leaderEmployeeId);

	Terminal findOneTerminalByTerminalId(Long terminalId);

	boolean createSalesmanTerminalRelation(String salesmanId, Long terminalId);

	boolean deleteSalesmanTerminalRelation(String salesmanId, Long terminalId);

	Collection<MaintenSalesmanResponse> findAllSalesmansByLeaderEmployeeId(String leaderEmployeeId);

	Collection<Terminal> findAllTerminalsByLeaderEmployeeId(String leaderEmployeeId);

	Collection<MaintenSalesmanResponse> findAllSalesmenByTerminalId(Long terminalId);
}
