/**
 * 
 */
package com.sap.jnc.marketing.service.wechat.employee.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sap.jnc.marketing.dto.request.register.EmployeeRegisterRequest;
import com.sap.jnc.marketing.persistence.model.Employee;
import com.sap.jnc.marketing.persistence.model.User;
import com.sap.jnc.marketing.persistence.repository.EmployeeRepository;
import com.sap.jnc.marketing.persistence.repository.UserRepository;
import com.sap.jnc.marketing.service.wechat.WechatService;
import com.sap.jnc.marketing.service.wechat.employee.WechatEmployeeRegisterService;

/**
 * @author I323560
 *
 */
@Service
@Transactional
public class WechatEmployeeRegisterServiceImpl implements WechatEmployeeRegisterService {

	private static Logger LOGGER = LoggerFactory.getLogger(WechatEmployeeRegisterServiceImpl.class);

	private static final String MOVE_USER_TO_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token={access_token}";
	private static final String ACCEPT = "Accept";
	private static final String OPEN_ID = "openid";
	private static final String TO_GROUP_ID = "to_groupid";
	private static final String ERROR_CODE = "errcode";
	private static final String ERROR_MESSAGE = "errmsg";

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WechatService wechatService;

	@Autowired
	private RestTemplate restTemplate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sap.jnc.marketing.service.wechat.WechatEmployeeRegisterService#
	 * registerEmployee(com.sap.jnc.marketing.persistence.model.Employee)
	 */
	@Override
	@Transactional
	public boolean registerEmployee(EmployeeRegisterRequest employeeRegisterRequest) {
		boolean isVerified = false;
		String openId = employeeRegisterRequest.getOpenId();
		String employeeNumber = employeeRegisterRequest.getEmployeeNumber();
		String idNo = employeeRegisterRequest.getIdNumber();
		String employeeName = employeeRegisterRequest.getEmployeeName();
		String employeePhone = employeeRegisterRequest.getEmployeePhone();

		Employee employee = new Employee();
		employee.setExternalId(employeeNumber);
		employee.setIdCardNO(idNo);
		employee.setName(employeeName);
		employee.setPhone(employeePhone);
		String wechatGroupId = employeeRepository.findWechatGroupId(employee);
		LOGGER.debug(wechatGroupId);
		if (StringUtils.isNotBlank(wechatGroupId)) {
			isVerified = true;
			String accessToken = wechatService.getAccessToken();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
			headers.add(ACCEPT, MediaType.APPLICATION_JSON_VALUE);
			Map<String, String> requestJsonMap = new HashMap<String, String>();
			requestJsonMap.put(OPEN_ID, employeeRegisterRequest.getOpenId());
			requestJsonMap.put(TO_GROUP_ID, wechatGroupId);
			String requestJsonStr = JSONObject.toJSONString(requestJsonMap);
			LOGGER.debug("Post data for binding wechat user and group:" + requestJsonStr);
			HttpEntity<String> requestEntity = new HttpEntity<String>(requestJsonStr, headers);
			String result = restTemplate.postForObject(MOVE_USER_TO_GROUP, requestEntity, String.class, accessToken);
			JSONObject jsonObj = JSON.parseObject(result);
			int errorCode = jsonObj.getInteger(ERROR_CODE);
			String errorMessage = jsonObj.getString(ERROR_MESSAGE);
			if (0 != errorCode) {
				throw new RuntimeException("绑定用户分组错误，错误码：" + errorCode + "， 错误信息：" + errorMessage);
			} else {
				User employeeUser = userRepository.findOneByEmployeeProfile(employeeName, idNo, employeePhone,
						employeeNumber);
				if (null == employeeUser) {
					throw new RuntimeException("员工信息不存在：" + employeeName + "；" + idNo + "；" + employeePhone + "；" + employeeNumber);
				}
				employeeUser.getWechatAccount().setOpenId(openId);
				employeeUser.getWechatAccount().setGroup(wechatGroupId);
				userRepository.saveAndFlush(employeeUser);
			}
		}

		return isVerified;
	}

}
