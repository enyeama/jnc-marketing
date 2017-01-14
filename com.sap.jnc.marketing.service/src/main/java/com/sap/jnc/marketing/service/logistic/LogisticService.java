/**
 * 
 */
package com.sap.jnc.marketing.service.logistic;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.sap.jnc.marketing.dto.request.logistic.QrCodeScanLogisticRequest;
import com.sap.jnc.marketing.dto.response.dealer.DealerLogisticStasticsResponse;
import com.sap.jnc.marketing.dto.response.logistic.QrCodeScanLogisticResponse;
import com.sap.jnc.marketing.dto.response.logistic.QrCodeScanLogisticValidateResponse;

/**
 * @author Quansheng Liu I075496
 */
@Service
public interface LogisticService {

	QrCodeScanLogisticResponse createLogisticByQrCodeScan(QrCodeScanLogisticRequest request);

	QrCodeScanLogisticValidateResponse validateLogisticByQrCodeScan(QrCodeScanLogisticRequest request);

	Collection<DealerLogisticStasticsResponse> findAllDealerLogisticStatus(Long dealerId);
}
