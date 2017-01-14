package com.sap.jnc.marketing.service.consumption.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.response.consumption.DFQueryResponse;
import com.sap.jnc.marketing.service.consumption.ConsumptionExportService;

@Service
@Transactional(readOnly = true)
public class ConsumptionExportServiceImpl implements ConsumptionExportService {

	@Override
	public HSSFWorkbook export(List<DFQueryResponse> list) {
		String[] jxsExcelHeader = { "兑付单号", "核销状态", "兑付日期", "核销日期", "调差日期", "岗位编号", "岗位名称", "员工姓名", "物料编号", "物料名称", "兑付数量", "核销数量", "调差数量", "差异数量",
			"兑付备注", "核销备注", "调差备注" };
		int[] excelHeaderWidth = { 300, 280, 260, 260, 260, 260, 260, 360 };
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("兑付核销报表");
		HSSFRow row = sheet.createRow((int) 0);

		HSSFCellStyle style = wb.createCellStyle();
		// 设置水平居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置垂直居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 设置列宽度（像素）
		for (int i = 0; i < jxsExcelHeader.length; i++) {
			sheet.setColumnWidth(i, 20 * 256);
		}

		// 创建Excel头部
		for (int i = 0; i < jxsExcelHeader.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(jxsExcelHeader[i]);
			cell.setCellStyle(style);
			// 自动调整列宽度
			// sheet.autoSizeColumn(i);
		}

		// 遍历数据，创建单元格
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (int rowIndex = 0; rowIndex < list.size(); rowIndex++) {
			row = sheet.createRow(rowIndex + 1);
			DFQueryResponse d = list.get(rowIndex);
			row.createCell(0).setCellValue(d.getId());
			row.createCell(1).setCellValue(d.getVerificationStatus());
			if (d.getPaymentDate() != null) {
				row.createCell(2).setCellValue(dataFormat.format(d.getPaymentDate().getTime()));
			}
			else {
				row.createCell(2).setCellValue("");
			}
			if (d.getVerificationDate() != null)
				row.createCell(3).setCellValue(dataFormat.format(d.getVerificationDate().getTime()));
			else
				row.createCell(3).setCellValue("");

			if (d.getManualAdjustmentDate() != null)
				row.createCell(4).setCellValue(dataFormat.format(d.getManualAdjustmentDate().getTime()));
			else
				row.createCell(4).setCellValue("");
			row.createCell(5).setCellValue(d.getPositionId());
			row.createCell(6).setCellValue(d.getPositionDescription());
			row.createCell(7).setCellValue(d.getEmployeeName());
			row.createCell(8).setCellValue(d.getMaterialID());
			row.createCell(9).setCellValue(d.getMaterialName());
			row.createCell(10).setCellValue(d.getPaidQuantity().toString());
			row.createCell(11).setCellValue(d.getVerifiedQuantity().toString());
			row.createCell(12).setCellValue(d.getManualAdjustmentQuantity().toString());
			row.createCell(13).setCellValue(d.getDifferenceQuantity().toString());
			if (d.getPaymentComment() != null)
				row.createCell(14).setCellValue(d.getPaymentComment());
			else
				row.createCell(14).setCellValue("");

			if (d.getVerificationComment() != null)
				row.createCell(15).setCellValue(d.getVerificationComment());
			else
				row.createCell(15).setCellValue("");

			if (d.getManualAdjustmentComment() != null)
				row.createCell(16).setCellValue(d.getManualAdjustmentComment());
			else
				row.createCell(16).setCellValue("");

		}
		return wb;
	}
}
