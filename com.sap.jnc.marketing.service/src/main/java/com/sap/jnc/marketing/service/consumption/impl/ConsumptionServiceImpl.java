package com.sap.jnc.marketing.service.consumption.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.request.consumption.DFActionRequest;
import com.sap.jnc.marketing.dto.response.consumption.DFQueryResponse;
import com.sap.jnc.marketing.infrastructure.shared.utils.DecimalUtils;
import com.sap.jnc.marketing.persistence.enumeration.SpareMaterialVerificationStatus;
import com.sap.jnc.marketing.persistence.model.DifferenceQuantity;
import com.sap.jnc.marketing.persistence.model.SpareMaterialPayment;
import com.sap.jnc.marketing.persistence.model.VerifiedQuantity;
import com.sap.jnc.marketing.persistence.repository.SpareMaterialPaymentRepository;
import com.sap.jnc.marketing.service.consumption.ConsumptionService;
import com.sap.jnc.marketing.service.exception.CommonServiceException;

@Service
@Transactional
public class ConsumptionServiceImpl implements ConsumptionService {

	@Autowired
	private SpareMaterialPaymentRepository spareMaterialPaymentRepository;

	@Override
	@Transactional(readOnly = true)
	public List<DFQueryResponse> findAllDF(String id, String positionId) {
		List<SpareMaterialPayment> spareMaterialPayments;

		// Map different parameter to different DB request
		if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(positionId)) {
			spareMaterialPayments = this.spareMaterialPaymentRepository.findByIdAndPositionId(id, positionId);
		}
		else if (StringUtils.isNotBlank(id)) {
			spareMaterialPayments = this.spareMaterialPaymentRepository.findById(id);
		}
		else if (StringUtils.isNotBlank(positionId)) {
			spareMaterialPayments = this.spareMaterialPaymentRepository.findByPositionId(positionId);
		}
		else {
			spareMaterialPayments = this.spareMaterialPaymentRepository.findAll();
		}

		// DB Operation result resolve
		final List<DFQueryResponse> dFQueryResponses = new ArrayList<DFQueryResponse>();
		if ((spareMaterialPayments != null) && (spareMaterialPayments.size() > 0)) {
			for (final SpareMaterialPayment spareMaterialPayment : spareMaterialPayments) {
				final DFQueryResponse dFQueryResponse = new DFQueryResponse();
				dFQueryResponse.setId(spareMaterialPayment.getId().toString());
				dFQueryResponse.setVerificationStatus(spareMaterialPayment.getVerificationStatus().getLabel());
				dFQueryResponse.setVerificationDate(spareMaterialPayment.getVerificationDate());
				dFQueryResponse.setPositionId(spareMaterialPayment.getPosition().getExternalId());
				dFQueryResponse.setPositionDescription(spareMaterialPayment.getPosition().getName());
				dFQueryResponse.setEmployeeName(spareMaterialPayment.getEmployee().getName());
				dFQueryResponse.setMaterialID(spareMaterialPayment.getProduct().getId());
				dFQueryResponse.setMaterialName(spareMaterialPayment.getProduct().getName());
				dFQueryResponse.setPaidQuantity(spareMaterialPayment.getPaidQuantity().getValue());
				dFQueryResponse.setPaymentComment(spareMaterialPayment.getPaymentComment());
				dFQueryResponse.setPaymentDate(spareMaterialPayment.getPaymentDate());
				dFQueryResponse.setVerifiedQuantity(spareMaterialPayment.getVerifiedQuantity().getValue());
				dFQueryResponse.setDifferenceQuantity(spareMaterialPayment.getDifferenceQuantity().getValue());
				dFQueryResponse.setVerificationComment(spareMaterialPayment.getVerificationComment());
				dFQueryResponse.setManualAdjustmentDate(spareMaterialPayment.getManualAdjustmentDate());
				dFQueryResponse.setManualAdjustmentQuantity(spareMaterialPayment.getManualAdjustmentQuantity());
				dFQueryResponse.setManualAdjustmentComment(spareMaterialPayment.getManualAdjustmentComment());

				dFQueryResponses.add(dFQueryResponse);
			}
		}
		return dFQueryResponses;
	}

	@Override
	@Transactional
	public DFQueryResponse update(String id, DFActionRequest dFActionRequest) {
		final List<SpareMaterialPayment> spareMaterialPayments = this.spareMaterialPaymentRepository.findById(id);
		if ((spareMaterialPayments != null) && (spareMaterialPayments.size() > 0)) {
			final SpareMaterialPayment spareMaterialPayment = spareMaterialPayments.get(0);
			boolean retureValue = true;
			if ((dFActionRequest.getVerifiedQuantity() != null) && (DecimalUtils.IsZero(dFActionRequest.getVerifiedQuantity()) || DecimalUtils
				.MoreThanZero(dFActionRequest.getVerifiedQuantity()))) {
				if (DecimalUtils.MoreThanZero(dFActionRequest.getVerifiedQuantity())) {
					// calculate verified quantity
					final VerifiedQuantity verifiedQuantity = new VerifiedQuantity();
					verifiedQuantity.setValue(dFActionRequest.getVerifiedQuantity());

					spareMaterialPayment.setVerifiedQuantity(verifiedQuantity);

					// calculate different quantity
					final BigDecimal paidQuantity = spareMaterialPayment.getPaidQuantity().getValue();
					final DifferenceQuantity differenceQuantity = new DifferenceQuantity();
					final BigDecimal differetncyTmp = DecimalUtils.Subtract(paidQuantity, verifiedQuantity.getValue());

					differenceQuantity.setValue(DecimalUtils.Subtract(differetncyTmp, spareMaterialPayment.getManualAdjustmentQuantity()));

					if (DecimalUtils.IsZero(differenceQuantity.getValue()) || DecimalUtils.MoreThanZero(differenceQuantity.getValue())) {
						spareMaterialPayment.setDifferenceQuantity(differenceQuantity);
						spareMaterialPayment.setVerificationStatus(SpareMaterialVerificationStatus.VERIFIED);
						// set calendar
						spareMaterialPayment.setVerificationDate(Calendar.getInstance());
					}
					else {
						retureValue = false;
					}

					// set comment
					if (dFActionRequest.getVerificationComment() != null) {
						spareMaterialPayment.setVerificationComment(dFActionRequest.getVerificationComment());
					}
				}
				else {
					retureValue = false;
				}
			}

			// Set Difference Quantity
			if ((dFActionRequest.getManualAdjustmentQuantity() != null) && (DecimalUtils.IsZero(dFActionRequest.getManualAdjustmentQuantity())
				|| DecimalUtils.MoreThanZero(dFActionRequest.getManualAdjustmentQuantity()))) {
				BigDecimal differentValue = DecimalUtils.Subtract(spareMaterialPayment.getPaidQuantity().getValue(), spareMaterialPayment
					.getVerifiedQuantity().getValue());
				differentValue = DecimalUtils.Subtract(differentValue, dFActionRequest.getManualAdjustmentQuantity());
				final DifferenceQuantity differenceQuantity = new DifferenceQuantity();
				differenceQuantity.setValue(differentValue);
				spareMaterialPayment.setDifferenceQuantity(differenceQuantity);
				spareMaterialPayment.setManualAdjustmentQuantity(dFActionRequest.getManualAdjustmentQuantity());
				if (dFActionRequest.getManualAdjustmentComment() != null) {
					spareMaterialPayment.setManualAdjustmentComment(dFActionRequest.getManualAdjustmentComment());
					spareMaterialPayment.setManualAdjustmentDate(Calendar.getInstance());
				}
			}

			if (retureValue == true) {
				this.spareMaterialPaymentRepository.save(spareMaterialPayment);
			}
			else {
				throw new CommonServiceException("数据出错");
			}

			// TODO if save ok, return the DFQueryResponse with all informations
			return null;
		}
		else {
			throw new CommonServiceException("兑付单号不存在");
		}
	}

}
