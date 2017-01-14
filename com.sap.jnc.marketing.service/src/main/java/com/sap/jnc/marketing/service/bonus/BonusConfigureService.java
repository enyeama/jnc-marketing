package com.sap.jnc.marketing.service.bonus;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.dto.request.bonus.BonusDispatcherRequest;
import com.sap.jnc.marketing.dto.response.bonus.BonusValidityResponse;
import com.sap.jnc.marketing.dto.response.bonus.ProductCategoryResponse;
import com.sap.jnc.marketing.persistence.criteria.dealer.BonusConfigAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.BonusDispatchConfig;

public interface BonusConfigureService {

	/**
	 * 查询 所有的产品类型列表
	 * @return
	 */
	List<ProductCategoryResponse> listProductCategories();

	/**
	 * 分页条件查询红包配置列表
	 * @param keywords
	 * @param pageRequest
	 * @return
	 */
	Page<BonusDispatchConfig> advanceSearch(BonusConfigAdvanceSearchKeywordNode keywords, PageRequest pageRequest);

	/**
	 * 保存新建红包配置
	 * 
	 * @param bonusDispatcherRequest
	 * @return
	 */
	BonusValidityResponse addBonusDispatcher(BonusDispatcherRequest request);

	/**
	 * 验证当前生效日期的合法性
	 * 
	 * @param request
	 * @return
	 */
	BonusValidityResponse isValidFromLegal(BonusDispatcherRequest request);
}
