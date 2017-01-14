package com.sap.jnc.marketing.service.migration.parser;

import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.sap.jnc.marketing.service.config.migration.UploadField;
import com.sap.jnc.marketing.service.config.migration.UploadFile;
import com.sap.jnc.marketing.service.config.migration.UploadRow;
import com.sap.jnc.marketing.service.migration.cache.CacheManager;
import com.sap.jnc.marketing.service.util.JNCBeanUtils;

@Component
public class ExcelParser {

	private static Logger logger = LoggerFactory.getLogger(ExcelParser.class);

	public void close(InputStream is, XSSFWorkbook workbook) {
		try {
			if (workbook != null) {
				workbook.close();
			}
		}
		catch (Exception e1) {
		}

		try {
			if (is != null) {
				is.close();
			}
		}
		catch (Exception e2) {
		}
	}

	public XSSFWorkbook getWorkbook(InputStream is) {
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(is);
			return workbook;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public <T extends UploadRow> List<T> parse(XSSFWorkbook workbook, Class<T> clazz) {
		CacheManager cacheManager = CacheManager.getInstance();
		UploadFile uploadFile = cacheManager.getUploadConf(clazz).getFileAnnotation();
		XSSFSheet sheet = workbook.getSheet(uploadFile.sheetName());
		if (sheet == null) {
			throw new RuntimeException("Sheet[" + uploadFile.sheetName() + "]不存在");
		}
		return parse(sheet, clazz);
	}

	/**
	 * @param sheet
	 * @param clazz
	 * @return
	 */
	public <T extends UploadRow> List<T> parse(XSSFSheet sheet, Class<T> clazz) {
		try {
			logger.info("解析开始，Sheet名字为：" + sheet.getSheetName());
			List<T> list = new LinkedList<T>();

			CacheManager cacheManager = CacheManager.getInstance();
			UploadFile uploadFile = cacheManager.getUploadConf(clazz).getFileAnnotation();
			int rowIndex = uploadFile.firstDataRow();
			Map<String, UploadField> uploadFields = cacheManager.getUploadConf(clazz).getFieldsAnnotation();
			while (rowIndex <= sheet.getLastRowNum()) {
				Row row = sheet.getRow(rowIndex);
				rowIndex++;
				if (checkBlankRow(row)) {
					continue;
				}
				T t = clazz.newInstance();
				t.setRowIndex(rowIndex);

				for (Entry<String, UploadField> entry : uploadFields.entrySet()) {
					Cell cell = row.getCell(entry.getValue().column());
					String prop = entry.getKey();
					if(StringUtils.isBlank(prop)){
						continue;
					}
					Object value = (cell == null) ? null : getCellValue(cell);
					
					// TODO 得到类型
					value = getConvertValue(t, prop, entry.getValue().enumProp(), value);
					
					// TODO 需要不断完善
					JNCBeanUtils.setProperty(t, prop, value);
				}
				list.add(t);
			}
			logger.info("解析完成");
			return list;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 检查是否有空行
	 * 
	 * @param row
	 * @return
	 */
	private boolean checkBlankRow(Row row) {
		if (null == row) {
			return true;
		}
		Iterator<Cell> iterator = row.cellIterator();
		while (iterator.hasNext()) {
			Cell cell = iterator.next();
			Object value = getCellValue(cell);
			if (null != value && !StringUtils.isBlank(value.toString())) {
				return false;
			}
		}
		return true;
	}

	private String getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			return null;
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue() + "";
		case Cell.CELL_TYPE_NUMERIC:
			DecimalFormat df = new DecimalFormat("#.#");
			return df.format(cell.getNumericCellValue());
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_ERROR:
			return cell.getErrorCellValue() + "";
		default:
			return null;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object getConvertValue(Object obj, String prop, String enumProp, Object value) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if(value == null){
			return null;
		}
		PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(obj.getClass(), prop);
		Method setter = descriptor.getWriteMethod();

		Class type = setter.getParameterTypes()[0];
		if(!type.isEnum()){
			return value;
		}
		for (Object constant : type.getEnumConstants()) {
			if(StringUtils.isNoneBlank(enumProp)){
				String label = (String) JNCBeanUtils.getProperty(constant, enumProp);
				if(label.equals(value)){
					return constant;
				}
			} 
			else {
				return EnumUtils.getEnum(type, value.toString());
			}
		}
		return value;
	}
	
	public static void main(String[] args) {
	}
}
