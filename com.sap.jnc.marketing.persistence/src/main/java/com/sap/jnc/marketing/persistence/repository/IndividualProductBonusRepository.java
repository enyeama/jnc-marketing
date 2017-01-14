package com.sap.jnc.marketing.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.IndividualProductBonus;

/**
 * 产品实体红包Repository
 * 
 * @author I323560
 */
@NoRepositoryBean
public interface IndividualProductBonusRepository extends JpaRepository<IndividualProductBonus, String> {

	/**
	 * 通过瓶盖内码和验证码，验证是否两码匹配
	 * 
	 * @param capInnerCode
	 *            瓶盖内码
	 * @param verificationCode
	 *            验证码
	 * @return 验证结果
	 */
	boolean verifyCode(String capInnerCode, String verificationCode);

	/**
	 * 通过瓶盖内码获取"物料四级分类"的物料分类id
	 * 
	 * @param capInnerCode
	 *            瓶盖内码
	 * @return 物料分类id
	 */
	Long findProductErpCategoryIdByCapInnerCode(String capInnerCode);

}
