package com.sap.jnc.marketing.service.sparematerial;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @author Marco, Huang I323691
 */
public interface SafeStockService {

	//上传安全库存报表文件
	String uploadSafeStock(String file, byte[] content) throws IOException; //RuntimeException 
	//安全库存导出功能
	HSSFWorkbook exportSafeStock(String positionId, String materialId);
	
	String querySpareMaterial(String positionId, String materialId);
	
	HSSFWorkbook exportErrorSafeStockForImport();
}
