package com.sap.jnc.marketing.persistence.repository;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.criteria.migration.EmployeeAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.Employee;

/**
 * @author I071053 Diouf Du
 */
@NoRepositoryBean
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Page<Employee> advanceSearch(EmployeeAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest);

	Collection<Employee> findByIds(Collection<Long> ids);

	/**
	 * 员工微信注册时验证及获取微信菜单groupId
	 * 
	 * @param employee 员工信息
	 * @return 微信菜单groupId
	 */
	String findWechatGroupId(Employee employee);
}
