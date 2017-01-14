package com.sap.jnc.marketing.service.consumption;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.sap.jnc.marketing.dto.response.consumption.DFQueryResponse;

public interface ConsumptionExportService {

	public HSSFWorkbook export(List<DFQueryResponse> list);
}
