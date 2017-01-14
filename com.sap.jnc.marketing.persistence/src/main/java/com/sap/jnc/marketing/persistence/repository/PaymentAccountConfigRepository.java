package com.sap.jnc.marketing.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.criteria.payment.PaymentAccountConfigAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.PaymentAccountConfig;

/**
 * @author C5205383 Kevin Ren
 */
@NoRepositoryBean
public interface PaymentAccountConfigRepository extends JpaRepository<PaymentAccountConfig, Long> {

	Page<PaymentAccountConfig> advanceSearch(PaymentAccountConfigAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest);

	PaymentAccountConfig findLastPaymentConfigByPaymentAccountIdAndPaymentAccountOpenId(Long defaultAccountId, String defaultAccountOpenId)
		throws Exception;

}
