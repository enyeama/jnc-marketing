package com.sap.jnc.marketing.dto.response.migration;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author Kay, Du I326950
 */
public class DealerMigrationResponse implements Serializable {
	private static final long serialVersionUID = 156559223899097302L;

	private long totalCount = 0;
	private long errorCount = 0;

	private Collection<DealerMigrationImportErrorResponse> dealerMigrationImportResponse;

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

	public Collection<DealerMigrationImportErrorResponse> getDealerMigrationImportResponse() {
		return dealerMigrationImportResponse;
	}

	public void setDealerMigrationImportResponse(Collection<DealerMigrationImportErrorResponse> dealerMigrationImportResponse) {
		this.dealerMigrationImportResponse = dealerMigrationImportResponse;
	}

}
