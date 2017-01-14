package com.sap.jnc.marketing.api.admin.web.controller;

import java.io.IOException;
import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sap.jnc.marketing.dto.response.csv.ProvinceManagerOfficeResponse;
import com.sap.jnc.marketing.persistence.model.ProvinceManagerOfficeAssignment;
import com.sap.jnc.marketing.service.migration.ProvinceManagerOfficeMigrateService;

/**
 * @author Zero
 * @anthor Vodka
 */
@RestController
public class ProvinceManagerOfficeController extends GeneralController {

	@Autowired
	private ProvinceManagerOfficeMigrateService provinceManagerOfficeService;

	private Transformer<ProvinceManagerOfficeAssignment, ProvinceManagerOfficeResponse> DETAIL_RESPONSE_TRANSFORMER = new Transformer<ProvinceManagerOfficeAssignment, ProvinceManagerOfficeResponse>() {

		@Override
		public ProvinceManagerOfficeResponse transform(ProvinceManagerOfficeAssignment input) {
			return new ProvinceManagerOfficeResponse(input);
		}
	};

	@RequestMapping(value = "/provincemanageroffice", method = RequestMethod.POST, headers = "content-type=multipart/*")
	public Collection<ProvinceManagerOfficeResponse> uploadFiles(@RequestParam("file") MultipartFile file) throws IOException {
		return CollectionUtils.collect(provinceManagerOfficeService.parseFile(file.getName(), file.getBytes()), DETAIL_RESPONSE_TRANSFORMER);
	}

	@RequestMapping(value = "/provincemanageroffice", method = RequestMethod.GET)
	public Collection<ProvinceManagerOfficeResponse> findAll() {
		return CollectionUtils.collect(provinceManagerOfficeService.findAll(), DETAIL_RESPONSE_TRANSFORMER);
	}
}
