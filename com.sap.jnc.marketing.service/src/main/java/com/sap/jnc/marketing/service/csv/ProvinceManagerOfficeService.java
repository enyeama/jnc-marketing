package com.sap.jnc.marketing.service.csv;

import java.util.List;

import com.sap.jnc.marketing.persistence.model.ProvinceManagerOfficeAssignment;

/**
 * 
 * @author Zero
 * @anthor Vodka
 */
public interface ProvinceManagerOfficeService {

	List<ProvinceManagerOfficeAssignment> parseFile(String file, byte[] content);

	List<ProvinceManagerOfficeAssignment> findAll();
}
