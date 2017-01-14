package com.sap.jnc.marketing.dto.response.ka;

import java.util.ArrayList;
import java.util.List;

public class KAErrorMessageResponse {
	private List<KAErrorMessage> kaErrorMessages=new ArrayList<>();

	public List<KAErrorMessage> getKaErrorMessages() {
		return kaErrorMessages;
	}

	public void setKaErrorMessages(List<KAErrorMessage> kaErrorMessages) {
		this.kaErrorMessages = kaErrorMessages;
	}
	
	
}
