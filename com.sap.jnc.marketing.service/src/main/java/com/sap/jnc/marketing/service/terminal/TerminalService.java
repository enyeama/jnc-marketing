package com.sap.jnc.marketing.service.terminal;

import com.sap.jnc.marketing.dto.request.register.RegisterRequest;
import com.sap.jnc.marketing.dto.request.terminal.TerminalRequest;
import com.sap.jnc.marketing.dto.request.verification.UserVerificationRequest;
import com.sap.jnc.marketing.dto.response.verification.UserVerificationResponse;
import com.sap.jnc.marketing.infrastructure.shared.constant.ApiResult;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.dto.request.terminal.TerminalRequest;
import com.sap.jnc.marketing.persistence.criteria.ka.KAAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.Terminal;
import com.sap.jnc.marketing.persistence.model.UserVerification;

/**
 * Created by guokai on 16/6/17.
 */
public interface TerminalService {

	List<Terminal> findAll();

	Page<Terminal> advanceKASearch(KAAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest);

	Terminal findById(Long id);

	void registerUser(RegisterRequest request);

	List<UserVerification> findAccount(Long id);

	void accountAdd(Long id, UserVerificationRequest request);

	void create(TerminalRequest request);

	UserVerification findDetail(Long terminalId, Long id);
}
