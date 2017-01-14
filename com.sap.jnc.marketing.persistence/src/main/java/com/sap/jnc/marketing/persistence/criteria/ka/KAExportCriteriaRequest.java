/**
 * 
 */
package com.sap.jnc.marketing.persistence.criteria.ka;

import java.io.Serializable;
import java.util.List;

/**
 * @author Quansheng Liu I075496
 */
public class KAExportCriteriaRequest implements Serializable {

	private static final long serialVersionUID = 6187525636619590634L;
	private List<Long> terminalIds;

	public List<Long> getTerminalIds() {
		return terminalIds;
	}

	public void setTerminalIds(List<Long> terminalIds) {
		this.terminalIds = terminalIds;
	}

}
