/**
 * 
 */
package com.sap.jnc.marketing.service.ka.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.shared.ka.KAImportRecord;
import com.sap.jnc.marketing.persistence.criteria.ka.KAExportCriteriaRequest;
import com.sap.jnc.marketing.persistence.model.Terminal;
import com.sap.jnc.marketing.persistence.repository.TerminalRepository;
import com.sap.jnc.marketing.service.ka.KAExportService;

/**
 * @author Quansheng Liu I075496
 */
@Service
@Transactional
public class KAExportServiceImpl implements KAExportService {

	private static final int CONTENT_ROW_INDEX = 3;
	private final static String SHEET_TITLE = "KA终端数据";
	private final static String ID_ROW_NAME = "网点编号";
	private final static String KA_OFFICE_DESCRIPTION_ROW_NAME = "办事处描述";
	private final static String BRANCH_NAME_ROW_NAME = "网点名称";
	private final static String ADDRESS_ROW_NAME = "地址";
	private final static String STROE_NUMBER_ROW_NAME = "店号";
	private final static String KA_SYSTEM_ROW_NAME = "商超系统描述";
	private final static String KA_PRODUCTS_ROW_NAME = "产品（描述）";
	private final static String ACCOUNT_MANAGER_POSITION_ID_ROW_NAME = "KA客户经理";
	private final static String ACCOUNT_MANAGER_EMPLOYEE_NAME_ROW_NAME = "KA客户经理姓名";
	private final static String SPECIALIST_POSITION_ID_ROW_NAME = "KA专员";
	private final static String SPECIALIST_EMPLOYEE_NAME_ROW_NAME = "KA专员姓名";
	private final static String MAINTAINER_POSITION_ID_ROW_NAME = "维护人";
	private final static String MAINTAINER_EMPLOYEE_NAME_ROW_NAME = "维护人姓名";
	private final static String PROVINCE_ROW_NAME = "省";
	private final static String CITY_ROW_NAME = "市";
	private final static String COUNTY_ROW_NAME = "区县";
	private final static String COUNTY_ID_ROW_NAME = "区县编号";
	private final static String BUSINESS_STATUS_ROW_NAME = "门店业态";
	private final static String BRANCH_LEVEL_STR_ROW_NAME = "网点等级";
	private final static String SYSTEM_PROPERTY_NUMBER_ROW_NAME = "商超系统性质";
	private final static String SYSTEM_PROPERTY_NAME_ROW_NAME = "商超系统性质描述";
	private final static String KEY_USER_CONTACT_NAME_ROW_NAME = "网点关键人姓名";
	private final static String KEY_USER_CONTACT_PHONE_ROW_NAME = "网点关键人电话";
	private final static String KEY_USER_CONTACT_WECHAT_ROW_NAME = "网点关键人微信";
	private final static String PURCHASE_CONTACT_NAME_ROW_NAME = "采购人姓名";
	private final static String PURCHASE_CONTACT_PHONE_ROW_NAME = "采购人电话";
	private final static String PURCHASE_CONTACT_WECHAT_ROW_NAME = "采购人微信";
	private final static String KA_PURCHASE_CONTACT_NAME_ROW_NAME = "单店采购人姓名";
	private final static String KA_PURCHASE_CONTACT_PHONE_ROW_NAME = "单店采购人电话";
	private final static String KA_PURCHASE_CONTACT_WECHAT_ROW_NAME = "单店采购人微信";
	private final static String PROMOTER_CONTACT_NAME_ROW_NAME = "促销人姓名";
	private final static String PROMOTER_TYPE_STR_ROW_NAME = "促销类型";
	private final static String PROMOTER_CONTACT_PHONE_ROW_NAME = "促销人电话";
	private final static String PROMOTER_CONTACT_WECHAT_ROW_NAME = "促销人微信";

	private final static String[] rowNames = { ID_ROW_NAME, KA_OFFICE_DESCRIPTION_ROW_NAME, BRANCH_NAME_ROW_NAME, ADDRESS_ROW_NAME,
		STROE_NUMBER_ROW_NAME, KA_SYSTEM_ROW_NAME, KA_PRODUCTS_ROW_NAME, ACCOUNT_MANAGER_POSITION_ID_ROW_NAME, ACCOUNT_MANAGER_EMPLOYEE_NAME_ROW_NAME,
		SPECIALIST_POSITION_ID_ROW_NAME, SPECIALIST_EMPLOYEE_NAME_ROW_NAME, MAINTAINER_POSITION_ID_ROW_NAME, MAINTAINER_EMPLOYEE_NAME_ROW_NAME,
		PROVINCE_ROW_NAME, CITY_ROW_NAME, COUNTY_ROW_NAME, COUNTY_ID_ROW_NAME, BUSINESS_STATUS_ROW_NAME, BRANCH_LEVEL_STR_ROW_NAME,
		SYSTEM_PROPERTY_NUMBER_ROW_NAME, SYSTEM_PROPERTY_NAME_ROW_NAME, KEY_USER_CONTACT_NAME_ROW_NAME, KEY_USER_CONTACT_PHONE_ROW_NAME,
		KEY_USER_CONTACT_WECHAT_ROW_NAME, PURCHASE_CONTACT_NAME_ROW_NAME, PURCHASE_CONTACT_PHONE_ROW_NAME, PURCHASE_CONTACT_WECHAT_ROW_NAME,
		KA_PURCHASE_CONTACT_NAME_ROW_NAME, KA_PURCHASE_CONTACT_PHONE_ROW_NAME, KA_PURCHASE_CONTACT_WECHAT_ROW_NAME, PROMOTER_CONTACT_NAME_ROW_NAME,
		PROMOTER_TYPE_STR_ROW_NAME, PROMOTER_CONTACT_PHONE_ROW_NAME, PROMOTER_CONTACT_WECHAT_ROW_NAME };

	@Autowired
	private TerminalRepository terminalRepository;

	@Override
	public Workbook exportKARecords(KAExportCriteriaRequest exportCriteriaRequest) {
		Collection<Terminal> terminals = this.terminalRepository.findAllKAs(exportCriteriaRequest);
		List<Object[]> records = new ArrayList<>(terminals.size());
		for (Terminal terminal : terminals) {
			KAImportRecord record = new KAImportRecord(terminal);
			records.add(record.getValues());
		}
		Workbook wb = createWorkBook(records);
		return wb;
	}

	private Workbook createWorkBook(List<Object[]> records) {
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("1");

		// 产生表格标题行
		XSSFRow rowm = sheet.createRow(0);
		XSSFCell cellTiltle = rowm.createCell(0);

		// sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面 - 可扩展】
		XSSFCellStyle columnTopStyle = this.getColumnTopStyle(wb);// 获取列头样式对象
		XSSFCellStyle style = this.getStyle(wb); // 单元格样式对象

		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (rowNames.length - 1)));
		cellTiltle.setCellStyle(columnTopStyle);
		cellTiltle.setCellValue(SHEET_TITLE);

		// 定义所需列数
		int columnNum = rowNames.length;
		XSSFRow rowRowName = sheet.createRow(CONTENT_ROW_INDEX - 1); // 在索引2的位置创建行(最顶端的行开始的第二行)

		// 将列头设置到sheet的单元格中
		for (int n = 0; n < columnNum; n++) {
			XSSFCell cellRowName = rowRowName.createCell(n); // 创建列头对应个数的单元格
			cellRowName.setCellType(XSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
			XSSFRichTextString text = new XSSFRichTextString(rowNames[n]);
			cellRowName.setCellValue(text); // 设置列头单元格的值
			cellRowName.setCellStyle(columnTopStyle); // 设置列头单元格样式
		}

		// 将查询出的数据设置到sheet对应的单元格中
		for (int i = 0; i < records.size(); i++) {

			Object[] values = records.get(i);// 遍历每个对象
			XSSFRow row = sheet.createRow(i + CONTENT_ROW_INDEX);// 创建所需的行数

			for (int j = 0; j < rowNames.length; j++) {
				XSSFCell cell = null; // 设置单元格的数据类型
				if (values[j] == null) {
					cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
				}
				else if (values[j] instanceof Long) {
					cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue((Long) values[j]);
				}
				else if (values[j] instanceof String) {
					cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(values[j].toString());
				}
				else {
					cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
				}
				cell.setCellStyle(style); // 设置单元格样式
			}
		}

		// 让列宽随着导出的列长自动适应
		for (int colNum = 0; colNum < columnNum; colNum++) {
			int columnWidth = sheet.getColumnWidth(colNum) / 256;
			for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
				XSSFRow currentRow;
				// 当前行未被使用过
				if (sheet.getRow(rowNum) == null) {
					currentRow = sheet.createRow(rowNum);
				}
				else {
					currentRow = sheet.getRow(rowNum);
				}
				if (currentRow.getCell(colNum) != null) {
					XSSFCell currentCell = currentRow.getCell(colNum);
					if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
						int length = currentCell.getStringCellValue().getBytes().length;
						if (columnWidth < length) {
							columnWidth = length;
						}
					}
				}
			}
			if (colNum == 0) {
				sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
			}
			else {
				sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
			}
		}

		return wb;

	}

	/*
	 * 列头单元格样式
	 */
	public XSSFCellStyle getColumnTopStyle(XSSFWorkbook workbook) {

		// 设置字体
		XSSFFont font = workbook.createFont();
		// 设置字体大小
		font.setFontHeightInPoints((short) 11);
		// 字体加粗
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边框;
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边框;
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

		return style;

	}

	/*
	 * 列数据信息单元格样式
	 */
	public XSSFCellStyle getStyle(XSSFWorkbook workbook) {
		// 设置字体
		XSSFFont font = workbook.createFont();
		// 设置字体大小
		// font.setFontHeightInPoints((short)10);
		// 字体加粗
		// font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边框;
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边框;
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

		return style;

	}
}
