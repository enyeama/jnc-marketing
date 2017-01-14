/**
 *
 */
package com.sap.jnc.marketing.service.position;

import java.util.Collection;

import com.sap.jnc.marketing.dto.response.terminal.TerminalResponse;
import com.sap.jnc.marketing.persistence.model.PositionView;

/**
 * @author Quansheng Liu I075496
 */
public interface PositionService {

	Collection<TerminalResponse> findAllTerminalsByLeaderEmployeeId(long leaderEmployeeId);

	Collection<PositionView> listAllCityManagers();
}
