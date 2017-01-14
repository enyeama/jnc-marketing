/*
 * @author James Jiang
 */
package com.sap.jnc.marketing.service.contract.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.response.contract.DealerContractResponse.Item;
import com.sap.jnc.marketing.service.contract.ContractExportService;

@Service
@Transactional(readOnly = true)
public class ContractExportServiceImpl implements ContractExportService {

	@Override
	public HSSFWorkbook export(List<Item> list) {
		String[] jxsExcelHeader = { "合同ID", "合同模板", "合同状态", "经销商ID", "城市经理岗位", "财年", "合同开始日期", "合同截止日期", "物料三大类", "渠道", "区域" };
		int[] excelHeaderWidth = { 300, 280, 260, 260, 260, 260, 260, 360 };
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("合同报表");
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
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMdd");
		for (int rowIndex = 0; rowIndex < list.size(); rowIndex++) {
			row = sheet.createRow(rowIndex + 1);
			Item d = list.get(rowIndex);
			row.createCell(0).setCellValue(d.getExternalId());
			row.createCell(1).setCellValue(d.getContractTemplate());
			row.createCell(2).setCellValue(d.getStatus());
			row.createCell(3).setCellValue(d.getDealerId());
			row.createCell(4).setCellValue(d.getPositionId());
			row.createCell(5).setCellValue(d.getFinacialYear());
			if (d.getValidFrom() != null) {
				row.createCell(6).setCellValue(dataFormat.format(d.getValidFrom().getTime()));
			}
			else {
				row.createCell(6).setCellValue("");
			}
			if (d.getValidTo() != null) {
				row.createCell(7).setCellValue(dataFormat.format(d.getValidTo().getTime()));
			}
			else {
				row.createCell(7).setCellValue("");
			}

			row.createCell(8).setCellValue(d.getProductDmsCategoryId());
			row.createCell(9).setCellValue(d.getChannelId());

			if (d.getRegionId() != null) {
				row.createCell(10).setCellValue(d.getRegionId());
			}

		}
		return wb;
	}

}
