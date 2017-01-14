package com.sap.jnc.marketing.service.contract;

import java.util.List;

import com.sap.jnc.marketing.dto.response.contract.ContractImportResponse;
import com.sap.jnc.marketing.dto.shared.contract.ContractRecord;

public interface ContractImportDeleteService {
	public void ContractDelete(List<ContractRecord> requestList, List<ContractImportResponse> responseList);
}
