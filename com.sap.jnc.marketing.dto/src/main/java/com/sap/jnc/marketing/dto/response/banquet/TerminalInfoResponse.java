package com.sap.jnc.marketing.dto.response.banquet;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.sap.jnc.marketing.persistence.model.Terminal;

/**
 * @author I332242 Zhu Qiang
 */
public class TerminalInfoResponse implements Serializable {
	
	private static final long serialVersionUID = 7057802810999781010L;

	private Long id;

	private String terminalType;
	
	private String title;
	
	public TerminalInfoResponse() {
		
	}
	
	public TerminalInfoResponse(Terminal terminal) {
		if (null == terminal) {
			return;
		}
		if (null != terminal.getId()) {
			this.setId(terminal.getId());
		}
		if ( null != terminal.getType() && StringUtils.isNotBlank(terminal.getType().getLabel())) {
			this.setTerminalType(terminal.getType().getLabel());;
		}
		if (StringUtils.isNotBlank(terminal.getTitle())) {
			this.setTitle(terminal.getTitle());
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}
}
