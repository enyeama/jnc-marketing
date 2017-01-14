package com.sap.jnc.marketing.service.contract;

import java.io.InputStream;

import com.sap.jnc.marketing.dto.response.contract.ContractImportResponseBody;

public interface ContractImportService {
	public ContractImportResponseBody importContractRecords(InputStream inputStream, String fileName);
}
