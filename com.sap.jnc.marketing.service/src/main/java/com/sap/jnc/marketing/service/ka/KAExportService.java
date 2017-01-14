/**
 * 
 */
package com.sap.jnc.marketing.service.ka;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import com.sap.jnc.marketing.persistence.criteria.ka.KAExportCriteriaRequest;

/**
 * @author Quansheng Liu I075496
 */
@Service
public interface KAExportService {
	Workbook exportKARecords(KAExportCriteriaRequest exportCriteriaRequest);
}
