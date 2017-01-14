/**
 * 
 */
package com.sap.jnc.marketing.service.wechat.employee;

import com.sap.jnc.marketing.dto.request.register.EmployeeRegisterRequest;

/**
 * 微信上用于员工的服务
 * 
 * @author I323560
 *
 */
public interface WechatEmployeeRegisterService {

	/**
	 * 注册员工<br>
	 * 通过微信上输入的员工信息来判断系统是否有该员工存在<br>
	 * 如果存在，获取对应的微信菜单groupId，并向微信绑定该员工的openId和微信菜单groupId
	 * 
	 * @param employeeRegisterRequest
	 *            员工信息
	 * @return 是否注册成功
	 */
	public boolean registerEmployee(EmployeeRegisterRequest employeeRegisterRequest);

}
