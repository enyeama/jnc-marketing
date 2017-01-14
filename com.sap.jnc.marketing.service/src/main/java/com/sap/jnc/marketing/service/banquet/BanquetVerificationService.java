package com.sap.jnc.marketing.service.banquet;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;

import com.sap.jnc.marketing.dto.request.banquet.BanquetVerificationRequest;
import com.sap.jnc.marketing.persistence.criteria.banquet.BanquetVerificationSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.BanquetVerification;

/**
 * @author Joel.Cheng I310645
 *
 */
public interface BanquetVerificationService {
	
	BanquetVerification create(BanquetVerificationRequest mergeRequet);
	
	BanquetVerification update(BanquetVerificationRequest mergeRequet);
	
	void batchSumbitBanquetVerification(List<Long> ids);
	
	void batchSignBanquetVerification(List<Long> ids);
	
	BanquetVerification findOneById(@PathVariable Long id);
	
	Page<BanquetVerification> advanceSearch(BanquetVerificationSearchKeywordNode searchCriteria, PageRequest pageRequest);

}
