package com.sap.jnc.marketing.api.admin.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sap.jnc.marketing.service.exception.migration.ExportExcelException;
import com.sap.jnc.marketing.service.exception.migration.UploadFileInvalidFormatException;
import com.sap.jnc.marketing.service.sparematerial.SafeStockService;

/**
 * @author Marco, Huang I323691
 */

@RestController
public class SafeStockController extends GeneralController {

	@Autowired
	private SafeStockService safeStockService;

	private static final String REQUIRED_EXPORT_SAFESTOCK_FILE_EXTENTION = ".xls";
	protected static final String REQUIRED_UPLOAD_FILE_FORMAT = "Invalid file format, csv format is required.";
	private static final String READ_WRITE_EXCEPTION = "Exception happened during exporting excels";
	private static final String UTF_CHARACTER_NOT_SUPPORTED = "System does not support UTF-8 character encoding";
	private static final String EXPORT_FILE_NAME = "安全库存";
	
	/**
	 * 查询安全库存
	 **/
	@RequestMapping(value = "/safestock/querysafestock", method = RequestMethod.GET)
	public String querySpareMaterial(String positionId, String materialId) {
		return safeStockService.querySpareMaterial(positionId, materialId);
	}

	/**
	 * 导入安全库存报表
	 * 
	 * @throws IOException
	 **/
	@RequestMapping(value = "/safestock/safestockupload", method = RequestMethod.POST, headers = "content-type=multipart/*")
	public String uploadSafeStock(@RequestParam("file") MultipartFile file){ // do not throw exception!
		try {
			return safeStockService.uploadSafeStock(file.getName(), file.getBytes());
		}
		catch (IOException e) {
			throw new UploadFileInvalidFormatException(REQUIRED_UPLOAD_FILE_FORMAT, e);
		}
	}

	/**
	 * 导出安全库存报表
	 **/
	@RequestMapping(value = "/safestock/exportsafestock", method = RequestMethod.GET)
	public void exportSafeStock(String positionId, String materialId){
		HSSFWorkbook wb = this.safeStockService.exportSafeStock(positionId, materialId);
		OutputStream ouputStream = this.exportOutputStream();
		try {
			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		}
		catch (IOException e) {
			throw new ExportExcelException(READ_WRITE_EXCEPTION, e);
		}finally{
			try {
				if(ouputStream!=null){
					ouputStream.close();
				}
			}
			catch (IOException e) {
				throw new ExportExcelException(READ_WRITE_EXCEPTION, e);
			}
		}
	}

	/**
	 * 导出导入时的安全库存出错信息
	 **/
	@RequestMapping(value = "/safestock/exportsafestockerror", method = RequestMethod.GET)
	public void exportErrorSafeStockForImport(){
		HSSFWorkbook wb = this.safeStockService.exportErrorSafeStockForImport();
		OutputStream ouputStream = this.exportOutputStream();
		try {
			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		}
		catch (IOException e) {
			throw new ExportExcelException(READ_WRITE_EXCEPTION, e);
		}finally{
			try {
				if(ouputStream!=null){
					ouputStream.close();
				}
			}
			catch (IOException e) {
				throw new ExportExcelException(READ_WRITE_EXCEPTION, e);
			}
		}
	}
	
	
	/**
	 * 返回OutputStream
	 **/
	private OutputStream exportOutputStream(){
		OutputStream ouputStream = null;
		this.getHttpServletResponse().setContentType("application/vnd.ms-excel");
		String agent = this.getHttpServletRequest().getHeader("USER-AGENT").toLowerCase();
		String excelFileName;
		try {
			excelFileName = java.net.URLEncoder.encode(EXPORT_FILE_NAME, "UTF-8");
			if (agent.contains("firefox")) {
				this.getHttpServletResponse().setHeader("Content-disposition", "attachment;filename=" + new String(excelFileName.getBytes(), "ISO8859-1")
					+ REQUIRED_EXPORT_SAFESTOCK_FILE_EXTENTION);
			}
			this.getHttpServletResponse().setHeader("Content-disposition", "attachment;filename=" + excelFileName + REQUIRED_EXPORT_SAFESTOCK_FILE_EXTENTION);
			ouputStream = this.getHttpServletResponse().getOutputStream();
		}catch(UnsupportedEncodingException e){
			throw new ExportExcelException(UTF_CHARACTER_NOT_SUPPORTED, e);
		}
		catch (IOException e) {
			throw new ExportExcelException(READ_WRITE_EXCEPTION, e);
	}
		return ouputStream;
	}
	
}
