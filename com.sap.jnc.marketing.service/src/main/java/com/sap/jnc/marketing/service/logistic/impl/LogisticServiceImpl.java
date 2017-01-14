/**
 * 
 */
package com.sap.jnc.marketing.service.logistic.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Tuple;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.request.logistic.QrCodeScanLogisticRequest;
import com.sap.jnc.marketing.dto.response.dealer.DealerLogisticStasticsResponse;
import com.sap.jnc.marketing.dto.response.logistic.QrCodeScanLogisticFailedRecord;
import com.sap.jnc.marketing.dto.response.logistic.QrCodeScanLogisticResponse;
import com.sap.jnc.marketing.dto.response.logistic.QrCodeScanLogisticValidateResponse;
import com.sap.jnc.marketing.persistence.enumeration.LogisticOperatorType;
import com.sap.jnc.marketing.persistence.enumeration.MovementType;
import com.sap.jnc.marketing.persistence.enumeration.OrderType;
import com.sap.jnc.marketing.persistence.model.Dealer;
import com.sap.jnc.marketing.persistence.model.DealerOrder;
import com.sap.jnc.marketing.persistence.model.Employee;
import com.sap.jnc.marketing.persistence.model.EmployeeView;
import com.sap.jnc.marketing.persistence.model.IndividualProduct;
import com.sap.jnc.marketing.persistence.model.Logistic;
import com.sap.jnc.marketing.persistence.model.LogisticDealerIn;
import com.sap.jnc.marketing.persistence.model.LogisticDealerOutToLeader;
import com.sap.jnc.marketing.persistence.model.LogisticDealerOutToTerminal;
import com.sap.jnc.marketing.persistence.model.LogisticJncOut;
import com.sap.jnc.marketing.persistence.model.LogisticLeaderIn;
import com.sap.jnc.marketing.persistence.model.LogisticLeaderOut;
import com.sap.jnc.marketing.persistence.model.LogisticTerminalInFromDealer;
import com.sap.jnc.marketing.persistence.model.LogisticTerminalInFromLeader;
import com.sap.jnc.marketing.persistence.model.Position;
import com.sap.jnc.marketing.persistence.model.Terminal;
import com.sap.jnc.marketing.persistence.model.TerminalOrder;
import com.sap.jnc.marketing.persistence.repository.DealerOrderRepository;
import com.sap.jnc.marketing.persistence.repository.DealerRepository;
import com.sap.jnc.marketing.persistence.repository.EmployeeRepository;
import com.sap.jnc.marketing.persistence.repository.EmployeeViewRepository;
import com.sap.jnc.marketing.persistence.repository.IndividualProductRepository;
import com.sap.jnc.marketing.persistence.repository.LogisticDealerInRepository;
import com.sap.jnc.marketing.persistence.repository.LogisticDealerOutToLeaderRepository;
import com.sap.jnc.marketing.persistence.repository.LogisticDealerOutToTerminalRepository;
import com.sap.jnc.marketing.persistence.repository.LogisticJncOutRepository;
import com.sap.jnc.marketing.persistence.repository.LogisticLeaderInRepository;
import com.sap.jnc.marketing.persistence.repository.LogisticLeaderOutRepository;
import com.sap.jnc.marketing.persistence.repository.LogisticRepository;
import com.sap.jnc.marketing.persistence.repository.LogisticTerminalInFromDealerRepository;
import com.sap.jnc.marketing.persistence.repository.LogisticTerminalInFromLeaderRepository;
import com.sap.jnc.marketing.persistence.repository.PositionRepository;
import com.sap.jnc.marketing.persistence.repository.PositionViewRepository;
import com.sap.jnc.marketing.persistence.repository.TerminalOrderRepository;
import com.sap.jnc.marketing.persistence.repository.TerminalRepository;
import com.sap.jnc.marketing.service.logistic.LogisticService;

/**
 * @author Quansheng Liu I075496
 */
@Service
@Transactional
public class LogisticServiceImpl implements LogisticService {

	@Autowired
	private LogisticRepository logisticRepository;

	@Autowired
	private IndividualProductRepository individualProductRepository;

	@Autowired
	private DealerOrderRepository dealerOrderRepository;

	@Autowired
	private TerminalOrderRepository terminalOrderRepository;

	@Autowired
	private DealerRepository dealerRepository;

	@Autowired
	private TerminalRepository terminalRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeViewRepository employeeViewRepository;

	@Autowired
	private PositionViewRepository positionViewRepository;

	@Autowired
	private PositionRepository positionRepository;

	@Autowired
	private LogisticJncOutRepository logisticJncOutRepository;

	@Autowired
	private LogisticDealerInRepository logisticDealerInRepository;

	@Autowired
	private LogisticDealerOutToLeaderRepository logisticDealerOutToLeaderRepository;

	@Autowired
	private LogisticDealerOutToTerminalRepository logisticDealerOutToTerminalRepository;

	@Autowired
	private LogisticLeaderInRepository logisticLeaderInRepository;

	@Autowired
	private LogisticLeaderOutRepository logisticLeaderOutRepository;

	@Autowired
	private LogisticTerminalInFromLeaderRepository logisticTerminalInFromLeaderRepository;

	@Autowired
	private LogisticTerminalInFromDealerRepository logisticTerminalInFromDealerRepository;

	private final static String SPECIFIED_QRCODE_NOT_EXIST = "Specified code not exists in the system.";
	private final static String INVALID_PROCESS = "Invalid process.";
	private final static String INVALID_INPUT = "Invalid input.";

	@Override
	@Transactional(readOnly = true)
	public Collection<DealerLogisticStasticsResponse> findAllDealerLogisticStatus(Long dealerId) {
		List<Tuple> tupleList = this.logisticRepository.listDealerLogisticStatus(dealerId);
		Map<Long, DealerLogisticStasticsResponse> resultMap = new HashMap<>();
		for (Tuple tuple : tupleList) {
			Long operatorId = (Long) tuple.get(1);
			Long receiverId = (Long) tuple.get(2);
			MovementType movementType = (MovementType) tuple.get(3);
			if (movementType == MovementType.DEALER_OUT) {
				if (!resultMap.containsKey(receiverId)) {
					DealerLogisticStasticsResponse response = new DealerLogisticStasticsResponse();
					response.setLeadId(receiverId);
					resultMap.put(receiverId, response);
				}
				DealerLogisticStasticsResponse response = resultMap.get(receiverId);
				response.setDealerOutCount(response.getDealerOutCount() + 1);
			}
			else if (movementType == MovementType.LEADER_IN) {
				if (!resultMap.containsKey(operatorId)) {
					DealerLogisticStasticsResponse response = new DealerLogisticStasticsResponse();
					response.setLeadId(operatorId);
					resultMap.put(operatorId, response);
				}
				DealerLogisticStasticsResponse response = resultMap.get(operatorId);
				response.setLeadInCount(response.getLeadInCount() + 1);
			}
		}
		Collection<DealerLogisticStasticsResponse> responses = resultMap.values();
		Set<Long> leaderIds = resultMap.keySet();
		Collection<Employee> employees = this.employeeRepository.findByIds(leaderIds);
		Map<Long, String> employeeMap = new HashMap<>(employees.size());
		for (Employee employee : employees) {
			employeeMap.put(employee.getId(), employee.getName());
		}
		Calendar calendar = Calendar.getInstance();
		for (DealerLogisticStasticsResponse response : responses) {
			response.setLeadName(employeeMap.get(response.getLeadId()));
			response.setCreateOn(calendar);
		}
		return responses;
	}

	@Override
	@Transactional
	public QrCodeScanLogisticResponse createLogisticByQrCodeScan(QrCodeScanLogisticRequest request) {
		if (request == null || request.getMovementType() == null || request.getOperatorId() <= 0 || request.getOperatorType() == null
			|| CollectionUtils.isEmpty(request.getQrCodes())) {
			return null;
		}
		QrCodeScanLogisticResponse response = new QrCodeScanLogisticResponse();
		response.setFailedRecords(new ArrayList<>());
		response.setSuccessRecords(new ArrayList<>());
		Map<String, List<IndividualProduct>> qrCodeMap = new HashMap<>(request.getQrCodes().size());
		Map<String, Logistic> logisticMap = new HashMap<>();
		for (String qrCode : request.getQrCodes()) {
			List<IndividualProduct> productList = this.individualProductRepository.findByBoxIDorCaseID(qrCode);
			if (CollectionUtils.isEmpty(productList)) {
				fillFailedResponseRecord(qrCode, SPECIFIED_QRCODE_NOT_EXIST, response);
			}
			else {
				qrCodeMap.put(qrCode, productList);
				String capInnerCode = productList.get(0).getCapInnerCode();
				Logistic preLogistic = this.logisticRepository.findPreLogisticByProductCapInnerCode(capInnerCode);
				logisticMap.put(qrCode, preLogistic);
			}
		}
		validAndAutoComplete(request, response, logisticMap);
		switch (request.getMovementType()) {
		case JNC_OUT:
			batchCreateLogisticJncOut(request, response, logisticMap, qrCodeMap);
			break;
		case DEALER_IN:
			batchCreateLogisticDealIn(request, response, logisticMap, qrCodeMap);
			break;
		case DEALER_OUT:
			batchCreateLogisticDealerOutToLeader(request, response, logisticMap, qrCodeMap);
			break;
		case LEADER_IN:
			batchCreateLogisticLeaderIn(request, response, logisticMap, qrCodeMap);
			break;
		case LEADER_OUT:
			batchCreateLogisticLeaderOut(request, response, logisticMap, qrCodeMap);
			break;
		case TL_IN:
			batchCreateLogisticTerminalInFromLeader(request, response, logisticMap, qrCodeMap);
			break;
		case DEALERTOTERMINAL_DEALER_OUT:
			batchCreateLogisticDealerOutToTerminal(request, response, logisticMap, qrCodeMap);
			break;
		case DEALERTOTERMINAL_TERMINAL_IN:
			batchCreateLogisticTerminalInFromDealer(request, response, logisticMap, qrCodeMap);
			break;
		}
		return response;
	}

	@Override
	@Transactional(readOnly = true)
	public QrCodeScanLogisticValidateResponse validateLogisticByQrCodeScan(QrCodeScanLogisticRequest request) {
		QrCodeScanLogisticValidateResponse validateResponse = new QrCodeScanLogisticValidateResponse();
		if (request == null || request.getMovementType() == null || request.getOperatorId() <= 0 || request.getOperatorType() == null
			|| CollectionUtils.isEmpty(request.getQrCodes()) || request.getQrCodes().size() != 1) {
			validateResponse.setErrCode(1);
			validateResponse.setErrDescription(INVALID_INPUT);
			return validateResponse;
		}
		Map<String, Logistic> logisticMap = new HashMap<>();
		for (String qrCode : request.getQrCodes()) {
			List<IndividualProduct> productList = this.individualProductRepository.findByBoxIDorCaseID(qrCode);
			if (CollectionUtils.isEmpty(productList)) {
				validateResponse.setErrCode(2);
				validateResponse.setErrDescription(SPECIFIED_QRCODE_NOT_EXIST);
				return validateResponse;
			}
			else {
				String capInnerCode = productList.get(0).getCapInnerCode();
				Logistic preLogistic = this.logisticRepository.findPreLogisticByProductCapInnerCode(capInnerCode);
				logisticMap.put(qrCode, preLogistic);
			}
		}
		QrCodeScanLogisticResponse response = new QrCodeScanLogisticResponse();
		response.setFailedRecords(new ArrayList<>());
		validAndAutoComplete(request, response, logisticMap);
		if (CollectionUtils.isEmpty(response.getFailedRecords())) {
			validateResponse.setErrCode(0);
			validateResponse.setOrderId(request.getOrderId());
		}
		else {
			validateResponse.setErrCode(3);
			validateResponse.setErrDescription(INVALID_PROCESS);
		}
		return validateResponse;
	}

	private void validAndAutoComplete(QrCodeScanLogisticRequest request, QrCodeScanLogisticResponse response, Map<String, Logistic> logisticMap) {
		for (Map.Entry<String, Logistic> entry : logisticMap.entrySet()) {
			String qrCode = entry.getKey();
			Logistic preLogistic = entry.getValue();
			switch (request.getMovementType()) {
			case JNC_OUT:
				if (preLogistic != null || request.getOperatorType() != LogisticOperatorType.JNC) {
					fillFailedResponseRecord(qrCode, INVALID_PROCESS, response);
					logisticMap.remove(qrCode);
				}
				break;
			case DEALER_IN:
				if (preLogistic == null || preLogistic.getMovementType() != MovementType.JNC_OUT || preLogistic.getOperatorId() != request
					.getOperatorId()) {
					fillFailedResponseRecord(qrCode, INVALID_PROCESS, response);
					logisticMap.put(qrCode, null);
				}
				else {
					request.setOrderId(preLogistic.getOrderId());
					request.setOrderType(OrderType.DEALER_ORDER);
				}
				break;
			case DEALER_OUT:
				if (preLogistic == null || request.getOperatorId() != preLogistic.getOperatorId() || request.getReceiverId() < 1 || preLogistic
					.getMovementType() != MovementType.DEALER_IN) {
					fillFailedResponseRecord(qrCode, INVALID_PROCESS, response);
					logisticMap.put(qrCode, null);
				}
				break;
			case DEALERTOTERMINAL_DEALER_OUT:
				if (preLogistic == null || request.getOperatorId() != preLogistic.getOperatorId() || preLogistic
					.getMovementType() != MovementType.DEALER_IN) {
					fillFailedResponseRecord(qrCode, INVALID_PROCESS, response);
					logisticMap.put(qrCode, null);
				}
				break;
			case LEADER_IN:
				long receiverId = 0;
				if (preLogistic != null && preLogistic instanceof LogisticDealerOutToLeader) {
					LogisticDealerOutToLeader preDealOutLogistic = (LogisticDealerOutToLeader) preLogistic;
					receiverId = preDealOutLogistic.getLeaderEmployee() == null ? 0 : preDealOutLogistic.getLeaderEmployee().getId();
				}
				if (preLogistic == null || preLogistic.getMovementType() != MovementType.DEALER_OUT || request.getOperatorId() != receiverId) {
					fillFailedResponseRecord(qrCode, INVALID_PROCESS, response);
					logisticMap.put(qrCode, null);
				}
				break;
			case LEADER_OUT:
				if (preLogistic == null || request.getOperatorId() != preLogistic.getOperatorId() || preLogistic
					.getMovementType() != MovementType.LEADER_IN || request.getOrderId() < 1 || request.getOrderType() != OrderType.TERMINAL_ORDER) {
					fillFailedResponseRecord(qrCode, INVALID_PROCESS, response);
					logisticMap.put(qrCode, null);
				}
				break;
			case TL_IN:
				if (preLogistic == null || preLogistic.getMovementType() != MovementType.LEADER_OUT) {
					fillFailedResponseRecord(qrCode, INVALID_PROCESS, response);
					logisticMap.put(qrCode, null);
				}
				break;
			case DEALERTOTERMINAL_TERMINAL_IN:
				if (preLogistic == null || preLogistic.getMovementType() != MovementType.DEALERTOTERMINAL_DEALER_OUT) {
					fillFailedResponseRecord(qrCode, INVALID_PROCESS, response);
					logisticMap.put(qrCode, null);
				}
				break;
			default:
				return;
			}
		}
		// return false;
	}

	private void fillFailedResponseRecord(String qrCode, String errorDescription, QrCodeScanLogisticResponse response) {
		QrCodeScanLogisticFailedRecord record = new QrCodeScanLogisticFailedRecord();
		record.setQrCode(qrCode);
		record.setErrorDescription(errorDescription);
		response.getFailedRecords().add(record);
	}

	private boolean validDealerByDealerOrder(Long orderId, Long dealerId) {
		DealerOrder dealerOrder = this.dealerOrderRepository.findOne(orderId);
		return dealerId.longValue() == dealerOrder.getDealer().getId().longValue();
	}

	private void batchCreateLogisticJncOut(QrCodeScanLogisticRequest request, QrCodeScanLogisticResponse response, Map<String, Logistic> logisticMap,
		Map<String, List<IndividualProduct>> qrCodeMap) {
		DealerOrder dealerOrder = this.dealerOrderRepository.findOne(request.getOrderId());
		for (Map.Entry<String, Logistic> entry : logisticMap.entrySet()) {
			String qrCode = entry.getKey();
			List<IndividualProduct> productList = qrCodeMap.get(qrCode);
			for (IndividualProduct product : productList) {
				LogisticJncOut logistic = new LogisticJncOut();
				fillInLogisticRecord(logistic, request, product);
				// logistic.setDealerOrder(dealerOrder);
				this.logisticJncOutRepository.save(logistic);
			}
			response.getSuccessRecords().add(qrCode);
		}
		this.logisticJncOutRepository.flush();
	}

	private void batchCreateLogisticDealIn(QrCodeScanLogisticRequest request, QrCodeScanLogisticResponse response, Map<String, Logistic> logisticMap,
		Map<String, List<IndividualProduct>> qrCodeMap) {
		DealerOrder dealerOrder = this.dealerOrderRepository.findOne(request.getOrderId());
		Dealer dealer = this.dealerRepository.findOne(request.getOperatorId());
		for (Map.Entry<String, Logistic> entry : logisticMap.entrySet()) {
			if (entry.getValue() == null) {
				continue;
			}
			String qrCode = entry.getKey();
			List<IndividualProduct> productList = qrCodeMap.get(qrCode);
			for (IndividualProduct product : productList) {
				LogisticDealerIn logistic = new LogisticDealerIn();
				fillInLogisticRecord(logistic, request, product);
				logistic.setDealerOrder(dealerOrder);
				logistic.setDealer(dealer);
				this.logisticDealerInRepository.save(logistic);
			}
			response.getSuccessRecords().add(qrCode);
		}
		this.logisticDealerInRepository.flush();
	}

	private void batchCreateLogisticDealerOutToLeader(QrCodeScanLogisticRequest request, QrCodeScanLogisticResponse response,
		Map<String, Logistic> logisticMap, Map<String, List<IndividualProduct>> qrCodeMap) {
		Employee employee = null;
		EmployeeView employeeView = null;
		if (request.getReceiverId() >= 1) {
			employee = this.employeeRepository.findOne(request.getReceiverId());
			if (employee != null) {
				employeeView = this.employeeViewRepository.findOne(employee.getExternalId());
			}
		}
		TerminalOrder terminalOrder = this.terminalOrderRepository.findOne(request.getOrderId());
		Dealer dealer = this.dealerRepository.findOne(request.getOperatorId());
		for (Map.Entry<String, Logistic> entry : logisticMap.entrySet()) {
			if (entry.getValue() == null) {
				continue;
			}
			String qrCode = entry.getKey();
			List<IndividualProduct> productList = qrCodeMap.get(qrCode);
			for (IndividualProduct product : productList) {
				LogisticDealerOutToLeader logistic = new LogisticDealerOutToLeader();
				fillInLogisticRecord(logistic, request, product);
				logistic.setLeaderEmployee(employee);
				if (employeeView != null && CollectionUtils.isEmpty(employeeView.getPositions())) {
					Position position = this.positionRepository.findOne(employeeView.getPositions().get(0).getId());
					logistic.setLeaderPosition(position);
				}
				logistic.setDealer(dealer);
				logistic.setTerminalOrder(terminalOrder);
				this.logisticDealerOutToLeaderRepository.save(logistic);
			}
			response.getSuccessRecords().add(qrCode);
		}
		this.logisticDealerOutToLeaderRepository.flush();
	}

	private void batchCreateLogisticDealerOutToTerminal(QrCodeScanLogisticRequest request, QrCodeScanLogisticResponse response,
		Map<String, Logistic> logisticMap, Map<String, List<IndividualProduct>> qrCodeMap) {
		Terminal terminal = this.terminalRepository.findOne(request.getReceiverId());
		TerminalOrder terminalOrder = this.terminalOrderRepository.findOne(request.getOrderId());
		Dealer dealer = this.dealerRepository.findOne(request.getOperatorId());
		for (Map.Entry<String, Logistic> entry : logisticMap.entrySet()) {
			if (entry.getValue() == null) {
				continue;
			}
			String qrCode = entry.getKey();
			List<IndividualProduct> productList = qrCodeMap.get(qrCode);
			for (IndividualProduct product : productList) {
				LogisticDealerOutToTerminal logistic = new LogisticDealerOutToTerminal();
				fillInLogisticRecord(logistic, request, product);
				logistic.setDealer(dealer);
				logistic.setTerminalOrder(terminalOrder);
				logistic.setTerminal(terminal);
				this.logisticDealerOutToTerminalRepository.save(logistic);
			}
			response.getSuccessRecords().add(qrCode);
		}
		this.logisticDealerOutToTerminalRepository.flush();
	}

	private void batchCreateLogisticLeaderIn(QrCodeScanLogisticRequest request, QrCodeScanLogisticResponse response,
		Map<String, Logistic> logisticMap, Map<String, List<IndividualProduct>> qrCodeMap) {
		Employee employee = this.employeeRepository.findOne(request.getOperatorId());
		EmployeeView employeeView = null;
		if (employee != null) {
			employeeView = this.employeeViewRepository.findOne(employee.getExternalId());
		}
		for (Map.Entry<String, Logistic> entry : logisticMap.entrySet()) {
			if (entry.getValue() == null) {
				continue;
			}
			String qrCode = entry.getKey();
			List<IndividualProduct> productList = qrCodeMap.get(qrCode);
			for (IndividualProduct product : productList) {
				LogisticLeaderIn logistic = new LogisticLeaderIn();
				fillInLogisticRecord(logistic, request, product);
				logistic.setLeaderEmployee(employee);
				if (employeeView != null && CollectionUtils.isEmpty(employeeView.getPositions())) {
					Position position = this.positionRepository.findOne(employeeView.getPositions().get(0).getId());
					logistic.setLeaderPosition(position);
				}
				this.logisticLeaderInRepository.save(logistic);
			}
			response.getSuccessRecords().add(qrCode);
		}
		this.logisticLeaderInRepository.flush();
	}

	private void batchCreateLogisticLeaderOut(QrCodeScanLogisticRequest request, QrCodeScanLogisticResponse response,
		Map<String, Logistic> logisticMap, Map<String, List<IndividualProduct>> qrCodeMap) {
		Employee employee = this.employeeRepository.findOne(request.getOperatorId());
		EmployeeView employeeView = null;
		if (employee != null) {
			employeeView = this.employeeViewRepository.findOne(employee.getExternalId());
		}
		for (Map.Entry<String, Logistic> entry : logisticMap.entrySet()) {
			if (entry.getValue() == null) {
				continue;
			}
			String qrCode = entry.getKey();
			List<IndividualProduct> productList = qrCodeMap.get(qrCode);
			for (IndividualProduct product : productList) {
				LogisticLeaderOut logistic = new LogisticLeaderOut();
				fillInLogisticRecord(logistic, request, product);
				logistic.setLeaderEmployee(employee);
				if (employeeView != null && CollectionUtils.isEmpty(employeeView.getPositions())) {
					Position position = this.positionRepository.findOne(employeeView.getPositions().get(0).getId());
					logistic.setLeaderPosition(position);
				}
				this.logisticLeaderOutRepository.save(logistic);
			}
			response.getSuccessRecords().add(qrCode);
		}
		this.logisticLeaderOutRepository.flush();
	}

	private void batchCreateLogisticTerminalInFromLeader(QrCodeScanLogisticRequest request, QrCodeScanLogisticResponse response,
		Map<String, Logistic> logisticMap, Map<String, List<IndividualProduct>> qrCodeMap) {
		Terminal terminal = this.terminalRepository.findOne(request.getOperatorId());
		for (Map.Entry<String, Logistic> entry : logisticMap.entrySet()) {
			if (entry.getValue() == null) {
				continue;
			}
			String qrCode = entry.getKey();
			List<IndividualProduct> productList = qrCodeMap.get(qrCode);
			for (IndividualProduct product : productList) {
				LogisticTerminalInFromLeader logistic = new LogisticTerminalInFromLeader();
				fillInLogisticRecord(logistic, request, product);
				logistic.setTerminal(terminal);
				this.logisticTerminalInFromLeaderRepository.save(logistic);
			}
			response.getSuccessRecords().add(qrCode);
		}
		this.logisticTerminalInFromLeaderRepository.flush();
	}

	private void batchCreateLogisticTerminalInFromDealer(QrCodeScanLogisticRequest request, QrCodeScanLogisticResponse response,
		Map<String, Logistic> logisticMap, Map<String, List<IndividualProduct>> qrCodeMap) {
		Terminal terminal = this.terminalRepository.findOne(request.getOperatorId());
		for (Map.Entry<String, Logistic> entry : logisticMap.entrySet()) {
			if (entry.getValue() == null) {
				continue;
			}
			String qrCode = entry.getKey();
			List<IndividualProduct> productList = qrCodeMap.get(qrCode);
			for (IndividualProduct product : productList) {
				LogisticTerminalInFromDealer logistic = new LogisticTerminalInFromDealer();
				fillInLogisticRecord(logistic, request, product);
				logistic.setTerminal(terminal);
				this.logisticTerminalInFromDealerRepository.save(logistic);
			}
			response.getSuccessRecords().add(qrCode);
		}
		this.logisticTerminalInFromDealerRepository.flush();
	}

	private void fillInLogisticRecord(Logistic logistic, QrCodeScanLogisticRequest request, IndividualProduct product) {
		logistic.setIndividualProduct(product);
		logistic.setMovementType(request.getMovementType());
		logistic.setOperatorId(request.getOperatorId());
		logistic.setOperatorType(request.getOperatorType());
		logistic.setTimestamp(Calendar.getInstance());
		logistic.setIsCancelled(false);
		logistic.setYear(product.getYear());
		logistic.setMonth(product.getMonth());
		logistic.setOrderType(request.getOrderType());
		logistic.setOrderId(request.getOrderId());
	}

}
