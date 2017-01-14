package com.sap.jnc.marketing.service.sparematerial;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.dto.request.sparematerial.SpareMaterialPaymentWechatRequest;
import com.sap.jnc.marketing.persistence.criteria.sparematerial.SpareMaterialPaymentSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.model.SpareMaterialPayment;

/*
 * @author Kay, Du I326950
 */

public interface SpareMaterialPaymentService {

	public List<Product> findSpareMaterialsByPositionId(String positionId);

	public SpareMaterialPayment updateConsumptionQuantityByPositionIdAndMaterialId(String positionId, String materialId, SpareMaterialPaymentWechatRequest spareMaterialPaymentRequest);

	public Page<SpareMaterialPayment> pagedQuery(SpareMaterialPaymentSearchKeywordNode keywords, PageRequest pageRequest);

}
