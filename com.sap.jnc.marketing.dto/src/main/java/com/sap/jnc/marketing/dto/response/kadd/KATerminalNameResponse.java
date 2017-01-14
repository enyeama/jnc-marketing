package com.sap.jnc.marketing.dto.response.kadd;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.Terminal;

/**
 * @author Maggie Liu
 */

public class KATerminalNameResponse implements Serializable {
	private static final long serialVersionUID = 9125745983956761070L;
	private String kaTerminalName="";
	
	public KATerminalNameResponse(Terminal terminal){
		if(terminal==null){
			return ;
		}
		if(terminal.getBranchName()!=null){
			this.kaTerminalName=terminal.getBranchName();
		}
		
	}

	public String getKaTerminalName() {
		return kaTerminalName;
	}

	public void setKaTerminalName(String kaTerminalName) {
		this.kaTerminalName = kaTerminalName;
	}
}
