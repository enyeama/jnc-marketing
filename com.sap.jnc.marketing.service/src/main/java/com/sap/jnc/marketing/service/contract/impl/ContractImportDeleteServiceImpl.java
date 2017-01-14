/*
 * @author James Jiang
 */
package com.sap.jnc.marketing.service.contract.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.response.contract.ContractImportResponse;
import com.sap.jnc.marketing.dto.shared.contract.ContractRecord;
import com.sap.jnc.marketing.persistence.model.Contract;
import com.sap.jnc.marketing.persistence.model.ContractItem;
import com.sap.jnc.marketing.persistence.repository.ContractItemRepository;
import com.sap.jnc.marketing.persistence.repository.ContractRepository;
import com.sap.jnc.marketing.service.contract.ContractImportDeleteService;

import me.chanjar.weixin.common.util.StringUtils;

@Service
@Transactional
public class ContractImportDeleteServiceImpl implements ContractImportDeleteService {

	@Autowired
	private ContractRepository contractRepository;
	@Autowired
	private ContractItemRepository contractItemRepository;

	private static final String INVALID_CONTRACT_ID = "合同ID不能为空";
	private static final String INVALID_CONTRACT_ID_EXISTED = "合同ID不存在";
	private static final long INVALID_CONTRACT_ID_CODE = 2L;

	@Override
	public void ContractDelete(List<ContractRecord> requestList, List<ContractImportResponse> responseList) {
		int i = 3;
		Set<String> contratSet = new HashSet<>();
		for (ContractRecord request : requestList) {
			i++;
			if (StringUtils.isBlank(request.getContratId())) {
				ContractImportResponse response = new ContractImportResponse(request.getOperationFlag(), request.getContratId(), i, request
					.getContratId(), INVALID_CONTRACT_ID_CODE, INVALID_CONTRACT_ID);
				responseList.add(response);
				continue;
			}
			contratSet.add(request.getContratId());
		}
		if (responseList.size() > 0) {
			return;
		}
		List<Contract> contractList = contractRepository.findContractByExternalIds(contratSet);
		Map<String, Contract> contractMap = new HashMap<>();
		for (Contract contract : contractList) {
			contractMap.put(contract.getExternalId(), contract);
		}
		int j = 3;
		for (ContractRecord request : requestList) {
			j++;
			if (!contractMap.containsKey(request.getContratId())) {
				ContractImportResponse response = new ContractImportResponse(request.getOperationFlag(), request.getContratId(), j, request
					.getContratId(), INVALID_CONTRACT_ID_CODE, INVALID_CONTRACT_ID_EXISTED);
				responseList.add(response);
				continue;
			}
		}

		if (responseList.size() > 0) {
			return;
		}

		for (Contract contract : contractList) {
			List<ContractItem> contractItemList = contract.getItems();
			contractItemRepository.delete(contractItemList);
		}
		contractRepository.delete(contractList);

		return;
	}

}
