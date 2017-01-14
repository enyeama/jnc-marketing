package com.sap.jnc.marketing.service.ka;

import com.sap.jnc.marketing.dto.shared.terminal.TerminalNode;

public interface KAService {

	void disableKA(Long id);

	TerminalNode update(TerminalNode request);
}
