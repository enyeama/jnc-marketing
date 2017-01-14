package com.sap.jnc.marketing.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.criteria.dealer.BonusConfigAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.BonusDispatchConfig;

/**
 * 红包支付配置Controller
 * 
 * @author I327119
 */
@NoRepositoryBean
public interface BonusDispatchConfigRepository extends JpaRepository<BonusDispatchConfig, Long> {

	/**
	 * 通过查询条件和分页信息查询红包配置分页对象
	 * 
	 * @param searchCriteria
	 *            查询条件
	 * @param pageRequest
	 *            分页信息
	 * @return 红包配置分页对象
	 */
	public Page<BonusDispatchConfig> advanceSearch(BonusConfigAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest);

	/**
	 * 通过erpCategory的id来获得当日有效的红包配置
	 * 
	 * @param erpCategoryId
	 *            "物料分类"代码
	 * @return 红包配置
	 */
	public BonusDispatchConfig findCurrentValidConfigByErpCategoryId(Long erpCategoryId);

	/**
	 * 通过erpCategory的id来获得最近修改的红包配置
	 * 
	 * @param erpCategoryId
	 * @return
	 * @throws Exception 
	 */
	public BonusDispatchConfig findLastBonusByCategoryId(Long erpCategoryId) throws Exception;

}
