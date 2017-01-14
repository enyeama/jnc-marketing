package com.sap.jnc.marketing.service.csv.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.model.ProvinceManagerOfficeAssignment;
import com.sap.jnc.marketing.persistence.repository.DepartmentViewRepository;
import com.sap.jnc.marketing.persistence.repository.PositionViewRepository;
import com.sap.jnc.marketing.persistence.repository.ProvinceManagerOfficeAssignmentRepository;
import com.sap.jnc.marketing.service.csv.ProvinceManagerOfficeService;

/**
 * @author Zero
 * @author Vodka
 */
@Service
@Transactional
public class ProvinceManagerOfficeServiceImpl implements ProvinceManagerOfficeService {
	public static final Logger logger = LoggerFactory.getLogger(ProvinceManagerOfficeServiceImpl.class);

	@Autowired
	private ProvinceManagerOfficeAssignmentRepository provinceManagerOfficeAssignmentRepository;

	@Autowired
	private PositionViewRepository positionViewRepository;

	@Autowired
	private DepartmentViewRepository departmentViewRepository;

	@Override
	public List<ProvinceManagerOfficeAssignment> parseFile(String file, byte[] content) {
		String strContent = new String(content);
		String[] records = strContent.split("\n");
		for (int i = 0; i < records.length; i++) {
			String[] items = records[i].split(";");
			String type = items[0];
			ProvinceManagerOfficeAssignment provinceManagerOfficeAssignment = new ProvinceManagerOfficeAssignment();
			mergePropertiesByCSV(items, provinceManagerOfficeAssignment);
			switch (type) {
			case "C":
				createProvManagerOffice(provinceManagerOfficeAssignment);
				break;
			case "D":
				// provinceManagerOfficeAssignment = provinceManagerOfficeAssignmentRepository.findProvinceManagerByOfficeId(
				// provinceManagerOfficeAssignment.getOfficeId());
				// deleteProvManagerOffice(provinceManagerOfficeAssignment);
				break;
			}
		}

		return this.findAll();
	}

	@Override
	public List<ProvinceManagerOfficeAssignment> findAll() {
		return provinceManagerOfficeAssignmentRepository.findAll();
	}

	private void createProvManagerOffice(ProvinceManagerOfficeAssignment provinceManagerOfficeAssignment) {
		provinceManagerOfficeAssignmentRepository.saveAndFlush(provinceManagerOfficeAssignment);
	}

	private void deleteProvManagerOffice(ProvinceManagerOfficeAssignment provinceManagerOfficeAssignment) {
		provinceManagerOfficeAssignmentRepository.delete(provinceManagerOfficeAssignment);
	}

	private void mergePropertiesByCSV(String[] items, ProvinceManagerOfficeAssignment provinceManagerOfficeAssignment) {
		String officeID = items[1];
		String provManagerID = items[2];
		provinceManagerOfficeAssignment.setOffice(departmentViewRepository.findOne(officeID));
		provinceManagerOfficeAssignment.setProvinceManager(positionViewRepository.findOne(provManagerID));
	}

}
