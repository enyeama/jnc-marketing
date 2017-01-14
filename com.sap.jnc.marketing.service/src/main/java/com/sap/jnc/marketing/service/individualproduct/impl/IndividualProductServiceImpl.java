/**
 * 
 */
package com.sap.jnc.marketing.service.individualproduct.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.model.IndividualProduct;
import com.sap.jnc.marketing.persistence.repository.IndividualProductRepository;
import com.sap.jnc.marketing.service.individualproduct.IndividualProductService;

/**
 * @author Quansheng Liu I075496
 */
@Service
@Transactional
public class IndividualProductServiceImpl implements IndividualProductService {

	@Autowired
	IndividualProductRepository individualProductRepository;


}
