package com.sap.jnc.marketing.service.banquet.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.request.banquet.BanquetVerificationRequest;
import com.sap.jnc.marketing.persistence.criteria.banquet.BanquetVerificationSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.BanquetVerification;
import com.sap.jnc.marketing.persistence.repository.BanquetItemRepository;
import com.sap.jnc.marketing.persistence.repository.BanquetRepository;
import com.sap.jnc.marketing.persistence.repository.BanquetVerificationRepository;
import com.sap.jnc.marketing.persistence.repository.BanquetVerifyApplicationRepository;
import com.sap.jnc.marketing.persistence.repository.DepartmentRepository;
import com.sap.jnc.marketing.persistence.repository.DepartmentViewRepository;
import com.sap.jnc.marketing.persistence.repository.EmployeeRepository;
import com.sap.jnc.marketing.persistence.repository.EmployeeViewRepository;
import com.sap.jnc.marketing.service.banquet.BanquetVerificationService;

/**
 * @author Joel.Cheng I310645
 */
@Service
@Transactional
public class BanquetVerificationServiceImpl implements BanquetVerificationService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeViewRepository employeeViewRepository;

	@Autowired
	private BanquetRepository banquetRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private DepartmentViewRepository departmentViewRepository;

	@Autowired
	private BanquetVerifyApplicationRepository banquetVerifyApplicationRepository;

	@Autowired
	private BanquetVerificationRepository banquetVerificationRepository;

	@Autowired
	private BanquetItemRepository banquetItemRepository;

	@Override
	@Transactional(readOnly = true)
	public Page<BanquetVerification> advanceSearch(BanquetVerificationSearchKeywordNode searchCriteria, PageRequest pageRequest) {
		return banquetVerificationRepository.advanceSearch(searchCriteria, pageRequest);
	}

	@Override
	@Transactional
	public BanquetVerification create(BanquetVerificationRequest mergeRequest) {
		BanquetVerification entity = new BanquetVerification();
		this.assignScalarFields(mergeRequest, entity);
		// entity.setStatus(BanquetVerificationStatus.WAITFORAPPLY);
		return this.banquetVerificationRepository.saveAndFlush(entity);
	}

	@Transactional(readOnly = true)
	private void assignScalarFields(BanquetVerificationRequest mergeRequest, final BanquetVerification entity) {
		// // 快递公司
		// if (StringUtils.isNotBlank(mergeRequest.getExpressCompany())) {
		// entity.setExpressCompany(mergeRequest.getExpressCompany());
		// }
		// // 快递单号
		// if (StringUtils.isNotBlank(mergeRequest.getExpressNO())) {
		// entity.setExpressNO(mergeRequest.getExpressNO());
		// }
		// // 宴会报备单号
		// if (mergeRequest.getBanquetId() != null) {
		// Long banquetId = mergeRequest.getBanquetId();
		// Banquet banquet = banquetRepository.findOne(banquetId);
		// if (banquet != null) {
		// entity.setBanquet(banquet);
		//
		// }
		// }
		// // 邮寄日期
		// if (mergeRequest.getExpressTime() != null) {
		// entity.setExpressTime(mergeRequest.getExpressTime());
		// }
		// // 预计到达日期
		// if (mergeRequest.getPlanArriveTime() != null) {
		// entity.setPlanArriveTime(mergeRequest.getPlanArriveTime());
		// }
		// // 邮寄人
		// if (mergeRequest.getId() != null) {
		// Employee emp = employeeRepository.findOne(mergeRequest.getId());
		// if (emp != null && StringUtils.isNoneBlank(emp.getExternalId())) {
		// EmployeeView empView = employeeViewRepository.findOneByExternalId(emp.getExternalId());
		// if (empView != null && empView.getId() != null) {
		// Employee creator = employeeRepository.findOne(empView.getId());
		// if (creator != null) {
		// entity.setCreator(creator);
		// }
		// }
		// }
		// }
		// // 办事处
		// if (mergeRequest.getOfficeId() != null) {
		// Department department = departmentRepository.findOne(mergeRequest.getOfficeId());
		// if (department != null && StringUtils.isNotBlank(department.getExternalId())) {
		// DepartmentView departmentView = departmentViewRepository.findOne(department.getExternalId());
		// if (departmentView != null && departmentView.getId() != null) {
		// Department office = departmentRepository.findOne(departmentView.getId());
		// entity.setOffice(office);
		// }
		// }
		// }
		// // 销售渠道
		// if (StringUtils.isNotBlank(mergeRequest.getSalesChannel())) {
		// entity.setSalesChannel(mergeRequest.getSalesChannel());
		// }
		// // 邮寄瓶盖数
		// BottleQuantity bottleQuantity = new BottleQuantity();
		// if (mergeRequest.getBottleQuantityValue() != null) {
		// bottleQuantity.setValue(mergeRequest.getBottleQuantityValue());
		// }
		// if (StringUtils.isNotBlank(mergeRequest.getBottleQuantityUnit())) {
		// bottleQuantity.setUnit(mergeRequest.getBottleQuantityUnit());
		// }
		// entity.setBottleQuantity(bottleQuantity);
		// // 邮寄盒盖数
		// if (mergeRequest.getBoxQuantity() != null) {
		// entity.setBoxQuantity(mergeRequest.getBoxQuantity());
		// }
	}

	@Override
	@Transactional
	public BanquetVerification update(BanquetVerificationRequest mergeRequest) {
		if (mergeRequest.getId() == null) {
			return null;
		}
		BanquetVerification entity = this.banquetVerificationRepository.findOne(mergeRequest.getId());
		if (entity == null) {
			return null;
		}
		this.assignScalarFields(mergeRequest, entity);
		return this.banquetVerificationRepository.saveAndFlush(entity);
	}

	@Override
	@Transactional
	public void batchSumbitBanquetVerification(List<Long> ids) {
		List<BanquetVerification> list = new ArrayList<BanquetVerification>();
		if (CollectionUtils.isNotEmpty(ids)) {
			for (Long id : ids) {
				BanquetVerification banquetVerification = this.banquetVerificationRepository.findOne(id);
				if (banquetVerification != null) {
					// banquetVerification.setApplyTime(Calendar.getInstance());
					// banquetVerification.setStatus(BanquetVerificationStatus.DELIVERED);
					list.add(banquetVerification);
				}
			}
			if (list.size() > 0) {
				this.banquetVerificationRepository.save(list);
			}
		}
	}

	@Override
	public BanquetVerification findOneById(Long id) {
		BanquetVerification anquetVerification = this.banquetVerificationRepository.findOne(id);
		if (anquetVerification == null) {
			return null;
		}
		return anquetVerification;
	}

	@Override
	public void batchSignBanquetVerification(List<Long> ids) {
		if (CollectionUtils.isNotEmpty(ids)) {
			for (Long id : ids) {
				BanquetVerification banquetVerification = this.banquetVerificationRepository.findOne(id);
				BanquetVerification verification = new BanquetVerification();
				if (banquetVerification != null) {
					// banquetVerification.setStatus(BanquetVerificationStatus.WAITFORVERIFY);
					BanquetVerification application = banquetVerificationRepository.saveAndFlush(banquetVerification);
					if (application != null) {
						// verification.setBanquetVerification(application);
						// String verificationNumber = generateVerificationNumber(application);
						// verification.setVerificationNumber(verificationNumber);
						banquetVerificationRepository.saveAndFlush(verification);
					}
				}
			}
		}
	}

}
