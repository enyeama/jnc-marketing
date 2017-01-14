/*
 * This implementation is used to process the restful api call from JNC outbound logistic
 * @author James Jiang
 */
package com.sap.jnc.marketing.service.outbound.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.request.integration.JNCOuntboundInfo;
import com.sap.jnc.marketing.dto.request.integration.JNCOuntboundInfo.JNCOuntboundInfoItem;
import com.sap.jnc.marketing.dto.response.FieldErrorResponse;
import com.sap.jnc.marketing.persistence.enumeration.LogisticOperatorType;
import com.sap.jnc.marketing.persistence.enumeration.MovementType;
import com.sap.jnc.marketing.persistence.enumeration.OrderType;
import com.sap.jnc.marketing.persistence.model.IndividualProduct;
import com.sap.jnc.marketing.persistence.model.Logistic;
import com.sap.jnc.marketing.persistence.model.LogisticJncOut;
import com.sap.jnc.marketing.persistence.repository.IndividualProductRepository;
import com.sap.jnc.marketing.persistence.repository.LogisticJncOutRepository;
import com.sap.jnc.marketing.persistence.repository.LogisticRepository;
import com.sap.jnc.marketing.service.exception.migration.ResponseBodyDBException;
import com.sap.jnc.marketing.service.outbound.JNCOutboundService;

@Service
@Transactional
public class JNCOutboundServiceImpl implements JNCOutboundService {

	@Autowired
	private IndividualProductRepository individualProductRepository;

	@Autowired
	private LogisticRepository logisticRepository;

	@Autowired
	private LogisticJncOutRepository logisticJncOutRepository;

	@Override
	@Transactional
	public JNCOuntboundInfo dispatchOutboundInfo(JNCOuntboundInfo outboundInfo) {
		final List<JNCOuntboundInfoItem> items = outboundInfo.getItems();
		String operation = items.get(0).getCud();
		if ("C".equalsIgnoreCase(operation))
			this.create(outboundInfo);
		if ("U".equalsIgnoreCase(operation))
			this.update(outboundInfo);
		if ("D".equalsIgnoreCase(operation))
			this.delete(outboundInfo);
		return outboundInfo;
	}

	@Override
	@Transactional
	public JNCOuntboundInfo create(JNCOuntboundInfo outboundInfo) {
		final List<JNCOuntboundInfoItem> items = outboundInfo.getItems();
		Set<String> caseIdSet = new HashSet<>();
		Map<String, List<IndividualProduct>> IndividualProductMap = new HashMap<>();
		for (JNCOuntboundInfoItem item : items) {
			caseIdSet.add(item.getCaseID());
		}
		final List<IndividualProduct> individualProductsList = individualProductRepository.findByCaseIds(caseIdSet);
		for (IndividualProduct individualProduct : individualProductsList) {
			if (IndividualProductMap.containsKey(individualProduct.getCaseId())) {
				List<IndividualProduct> producCasetList = IndividualProductMap.get(individualProduct.getCaseId());
				producCasetList.add(individualProduct);
				IndividualProductMap.put(individualProduct.getCaseId(), producCasetList);
			}
			else {
				List<IndividualProduct> producCasetList = new ArrayList<>();
				producCasetList.add(individualProduct);
				IndividualProductMap.put(individualProduct.getCaseId(), producCasetList);
			}
		}

		List<Logistic> logisticList = new ArrayList<>();
		for (int i = 0; i < items.size(); i++) {
			JNCOuntboundInfoItem item = items.get(i);
			List<IndividualProduct> producCasetList = IndividualProductMap.get(item.getCaseID());
			if (CollectionUtils.isEmpty(producCasetList)) {
				throw this.getExceptionEntity(new RuntimeException(), "items[" + i + "].caseID", item.getCaseID(), "箱码不存在", i);
			}
			for (IndividualProduct individualProduct : producCasetList) {
				LogisticJncOut logistic = new LogisticJncOut();
				logistic.setIndividualProduct(individualProduct);
				logistic.setOrderExternalERPId(item.getErpOrderID());
				try {
					logistic.setOrderItemId(Long.parseLong(item.getOrderItemID()));//
					logistic.setOrderId(Long.parseLong(item.getDmsOrderID()));//
					logistic.setOperatorId(Long.parseLong(item.getDealerID()));
				}
				catch (final NumberFormatException e) {
				}
				logistic.setMonth(individualProduct.getYear());
				logistic.setYear(individualProduct.getMonth());
				// logistic.setMovementType(MovementType.JNC_OUT);
				logistic.setOperatorType(LogisticOperatorType.DEALER);
				logistic.setOrderType(OrderType.DEALER_ORDER);
				logisticList.add(logistic);
				logisticRepository.save(logistic);
			}
		}
		try {
			// this.logisticRepository.save(logisticList);
		}
		catch (final RuntimeException e) {
			throw this.getExceptionEntity(e, "items[" + 0 + "].caseID", null, "数据库操作出错", 0);
		}

		return outboundInfo;
	}

	@Override
	@Transactional
	public JNCOuntboundInfo update(JNCOuntboundInfo outboundInfo) {
		final List<JNCOuntboundInfoItem> items = outboundInfo.getItems();
		Set<String> caseIdSet = new HashSet<>();
		Map<String, List<Logistic>> logisticCaseIdMap = new HashMap<>();
		for (JNCOuntboundInfoItem item : items) {
			caseIdSet.add(item.getCaseID());
		}

		final List<Logistic> logisticsList = this.logisticRepository.findByCaseIdsAndMovementType(caseIdSet, MovementType.JNC_OUT);
		for (Logistic logistic : logisticsList) {
			String caseId = logistic.getIndividualProduct().getCaseId();
			if (logisticCaseIdMap.containsKey(caseId)) {
				List<Logistic> logisticCaseidList = logisticCaseIdMap.get(caseId);
				logisticCaseidList.add(logistic);
				logisticCaseIdMap.put(caseId, logisticCaseidList);
			}
			else {
				List<Logistic> logisticCaseidList = new ArrayList<>();
				logisticCaseidList.add(logistic);
				logisticCaseIdMap.put(caseId, logisticCaseidList);
			}
		}

		List<Logistic> logisticList = new ArrayList<>();
		for (int i = 0; i < items.size(); i++) {
			JNCOuntboundInfoItem item = items.get(i);
			String caseId = item.getCaseID();
			List<Logistic> logisticCaseidList = logisticCaseIdMap.get(caseId);
			if (CollectionUtils.isEmpty(logisticCaseidList)) {
				throw this.getExceptionEntity(new RuntimeException(), "items[" + i + "].caseID", item.getCaseID(), "箱码不存在", i);
			}
			for (Logistic logistic : logisticCaseidList) {
				logistic.setOrderExternalERPId(item.getErpOrderID());
				try {
					logistic.setOrderItemId(Long.parseLong(item.getOrderItemID()));
					logistic.setOrderId(Long.parseLong(item.getDmsOrderID()));
					logistic.setOperatorId(Long.parseLong(item.getDealerID()));
				}
				catch (final NumberFormatException e) {
				}
				logisticList.add(logistic);
			}
		}
		try {
			this.logisticRepository.save(logisticList);
		}
		catch (final RuntimeException e) {
			throw this.getExceptionEntity(e, "items[" + 0 + "].caseID", null, "数据库操作出错", 0);
		}

		return outboundInfo;
	}

	@Override
	@Transactional
	public JNCOuntboundInfo delete(JNCOuntboundInfo outboundInfo) {
		Set<String> caseIdSet = new HashSet<>();
		final List<JNCOuntboundInfoItem> items = outboundInfo.getItems();
		for (JNCOuntboundInfoItem item : items) {
			caseIdSet.add(item.getCaseID());
		}
		final List<Logistic> logistics = logisticRepository.findByCaseIdsAndMovementType(caseIdSet, MovementType.JNC_OUT);
		try {
			this.logisticRepository.delete(logistics);
		}
		catch (final RuntimeException e) {
			throw this.getExceptionEntity(e, "caseID", null, "数据库操作出错", 0);
		}
		return outboundInfo;
	}

	/**
	 * This method is used to construct the error format of DB operation. The error will be returned to client via the exception handler in Controller
	 *
	 * @param e
	 * @param field
	 * @param rejectValue
	 * @param message
	 * @param itemId
	 * @return
	 */
	private ResponseBodyDBException getExceptionEntity(RuntimeException e, String field, String rejectValue, String message, int itemId) {
		final FieldErrorResponse errors = new FieldErrorResponse();
		final List<FieldErrorResponse.FieldErrorBodyItems> itemsBody = new ArrayList<FieldErrorResponse.FieldErrorBodyItems>(1);
		final FieldErrorResponse.FieldErrorBodyItems itemBody = errors.new FieldErrorBodyItems(field, rejectValue, message, itemId);
		itemsBody.add(itemBody);
		errors.setError(itemsBody);
		return new ResponseBodyDBException(e, errors);
	}
}
