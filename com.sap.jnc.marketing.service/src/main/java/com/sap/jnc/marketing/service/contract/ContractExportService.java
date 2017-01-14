package com.sap.jnc.marketing.service.contract;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.sap.jnc.marketing.dto.response.contract.DealerContractResponse;

public interface ContractExportService {
	public HSSFWorkbook export(List<DealerContractResponse.Item> list);
}
