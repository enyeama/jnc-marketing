package com.sap.jnc.marketing.dto.response.contract;

import java.io.Serializable;
import java.util.Collection;

public class ContractImportResponseBody implements Serializable {

	private static final long serialVersionUID = 2468951044920539177L;

	private long totalCount = 0;
	private long errorCount = 0;

	private Collection<ContractImportResponse> contractImportResponse;

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(long errorCount) {
		this.errorCount = errorCount;
	}

	public Collection<ContractImportResponse> getContractImportResponse() {
		return contractImportResponse;
	}

	public void setContractImportResponse(Collection<ContractImportResponse> contractImportResponse) {
		this.contractImportResponse = contractImportResponse;
	}

}
