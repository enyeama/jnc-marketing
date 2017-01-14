/**
 * 
 */
package com.sap.jnc.marketing.service.ka;

import java.io.InputStream;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.sap.jnc.marketing.dto.response.ka.KAImportResponse;

/**
 * @author Quansheng Liu I075496
 */
@Service
public interface KAImportService {
	Collection<KAImportResponse> importKARecords(InputStream inputStream, String fileName);
}
