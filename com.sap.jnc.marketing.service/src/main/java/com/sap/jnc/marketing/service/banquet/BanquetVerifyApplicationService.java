package com.sap.jnc.marketing.service.banquet;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;

import com.sap.jnc.marketing.dto.request.banquet.BanquetVerifyApplyRequest;
import com.sap.jnc.marketing.persistence.criteria.banquet.BanquetVerifyApplicationSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.BanquetVerifyApplication;
import com.sap.jnc.marketing.persistence.model.DepartmentView;

/**
 * @author I332242 Zhu Qiang
 */
public interface BanquetVerifyApplicationService {
	
	BanquetVerifyApplication create(BanquetVerifyApplyRequest mergeRequet);
	
	DepartmentView findOfficeByCityManagerId(Long id);
	
	BanquetVerifyApplication update(BanquetVerifyApplyRequest mergeRequet);
	
	void batchSumbitBanquetVfyApplication(List<Long> ids);
	
	void batchSignBanquetVfyApplication(List<Long> ids);
	
	BanquetVerifyApplication findOneById(@PathVariable Long id);
	
	Page<BanquetVerifyApplication> advanceSearch(BanquetVerifyApplicationSearchKeywordNode searchCriteria, PageRequest pageRequest);
	
	int findRbatedByBanquetId(long banquetId);
}
