package com.sap.jnc.marketing.service.migration;

import java.util.List;

import com.sap.jnc.marketing.persistence.model.ProvinceManagerOfficeAssignment;

/**
 * 
 * @author Zero
 * @anthor Vodka
 */
public interface ProvinceManagerOfficeMigrateService {

	List<ProvinceManagerOfficeAssignment> parseFile(String file, byte[] content);

	List<ProvinceManagerOfficeAssignment> findAll();
}
