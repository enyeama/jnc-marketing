package com.sap.jnc.marketing.service.banquet.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.request.banquet.BanquetVerifyApplyRequest;
import com.sap.jnc.marketing.persistence.criteria.banquet.BanquetVerifyApplicationSearchKeywordNode;
import com.sap.jnc.marketing.persistence.enumeration.BanquetVerifyApplicationStatus;
import com.sap.jnc.marketing.persistence.model.Banquet;
import com.sap.jnc.marketing.persistence.model.BanquetItem;
import com.sap.jnc.marketing.persistence.model.BanquetVerification;
import com.sap.jnc.marketing.persistence.model.BanquetVerifyApplication;
import com.sap.jnc.marketing.persistence.model.BottleQuantity;
import com.sap.jnc.marketing.persistence.model.Department;
import com.sap.jnc.marketing.persistence.model.DepartmentView;
import com.sap.jnc.marketing.persistence.model.Employee;
import com.sap.jnc.marketing.persistence.model.EmployeeView;
import com.sap.jnc.marketing.persistence.repository.BanquetItemRepository;
import com.sap.jnc.marketing.persistence.repository.BanquetRepository;
import com.sap.jnc.marketing.persistence.repository.BanquetVerificationRepository;
import com.sap.jnc.marketing.persistence.repository.BanquetVerifyApplicationRepository;
import com.sap.jnc.marketing.persistence.repository.DepartmentRepository;
import com.sap.jnc.marketing.persistence.repository.DepartmentViewRepository;
import com.sap.jnc.marketing.persistence.repository.EmployeeRepository;
import com.sap.jnc.marketing.persistence.repository.EmployeeViewRepository;
import com.sap.jnc.marketing.service.banquet.BanquetVerifyApplicationService;

/**
 * @author I332242 Zhu Qiang
 */
@Service
@Transactional
public class BanquetVerifyApplicationServiceImpl implements BanquetVerifyApplicationService {
	
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
	public Page<BanquetVerifyApplication> advanceSearch(BanquetVerifyApplicationSearchKeywordNode searchCriteria, PageRequest pageRequest) {
		return banquetVerifyApplicationRepository.advanceSearch(searchCriteria, pageRequest);
	}

	@Override
	@Transactional
	public BanquetVerifyApplication create(BanquetVerifyApplyRequest mergeRequest) {
		BanquetVerifyApplication entity = new BanquetVerifyApplication();
		this.assignScalarFields(mergeRequest, entity);
		entity.setStatus(BanquetVerifyApplicationStatus.WAITFORAPPLY);
		return this.banquetVerifyApplicationRepository.saveAndFlush(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public DepartmentView findOfficeByCityManagerId(Long id) {
		DepartmentView department = new DepartmentView();
		Employee emp = employeeRepository.findOne(id);
		if (emp != null && StringUtils.isNoneBlank(emp.getExternalId())) {
			EmployeeView empView = employeeViewRepository.findOneByExternalId(emp.getExternalId());
			if (empView != null && CollectionUtils.isNotEmpty(empView.getPositions())) {
				if (empView.getPositions().get(0) != null && empView.getPositions().get(0).getDepartment() != null) {
					department = empView.getPositions().get(0).getDepartment().getSuperior();
					
				}
			}
		}
		return department;
	}

	@Transactional(readOnly = true)
	private void assignScalarFields(BanquetVerifyApplyRequest mergeRequest, final BanquetVerifyApplication entity) {
		// 快递公司
		if (StringUtils.isNotBlank(mergeRequest.getExpressCompany())) {
			entity.setExpressCompany(mergeRequest.getExpressCompany());
		}
		// 快递单号
		if (StringUtils.isNotBlank(mergeRequest.getExpressNO())) {
			entity.setExpressNO(mergeRequest.getExpressNO());
		}
		// 宴会报备单号
		if (mergeRequest.getBanquetId() != null) {
			Long banquetId = mergeRequest.getBanquetId();
			Banquet banquet = banquetRepository.findOne(banquetId);
			if (banquet != null) {
				entity.setBanquet(banquet);
				
			}
		}
		// 邮寄日期
		if (mergeRequest.getExpressTime() != null) {
			entity.setExpressTime(mergeRequest.getExpressTime());
		}
		// 预计到达日期
		if (mergeRequest.getPlanArriveTime() != null) {
			entity.setPlanArriveTime(mergeRequest.getPlanArriveTime());
		}
		// 邮寄人
		if (mergeRequest.getCreatorId() != null) {
			Employee emp = employeeRepository.findOne(mergeRequest.getCreatorId());
			if (emp != null && StringUtils.isNoneBlank(emp.getExternalId())) {
				EmployeeView empView = employeeViewRepository.findOneByExternalId(emp.getExternalId());
				if (empView != null && empView.getId() != null) {
					Employee creator = employeeRepository.findOne(empView.getId());
					if (creator != null) {
						entity.setCreator(creator);
					}
				}
			}
		}
		// 办事处
		if (mergeRequest.getOfficeId() != null) {
			Department department = departmentRepository.findOne(mergeRequest.getOfficeId());
			if (department != null && StringUtils.isNotBlank(department.getExternalId())) {
				DepartmentView departmentView = departmentViewRepository.findOne(department.getExternalId());
				if (departmentView != null && departmentView.getId() != null) {
					Department office = departmentRepository.findOne(departmentView.getId());
					entity.setOffice(office);
				}
			}
		}
		// 销售渠道
		if (StringUtils.isNotBlank(mergeRequest.getSalesChannel())) {
			entity.setSalesChannel(mergeRequest.getSalesChannel());
		}
		// 邮寄瓶盖数
		BottleQuantity bottleQuantity = new BottleQuantity();
		if (mergeRequest.getBottleQuantityValue() != null) {
			bottleQuantity.setValue(mergeRequest.getBottleQuantityValue());
		}
		if (StringUtils.isNotBlank(mergeRequest.getBottleQuantityUnit())) {
			bottleQuantity.setUnit(mergeRequest.getBottleQuantityUnit());
		}
		entity.setBottleQuantity(bottleQuantity);
		// 邮寄盒盖数
		if (mergeRequest.getBoxQuantity() != null) {
			entity.setBoxQuantity(mergeRequest.getBoxQuantity());
		}
	}

	@Override
	@Transactional
	public BanquetVerifyApplication update(BanquetVerifyApplyRequest mergeRequest) {
		if (mergeRequest.getId() == null ) {
			return null;
		}
		BanquetVerifyApplication entity = this.banquetVerifyApplicationRepository.findOne(mergeRequest.getId());
		if (entity == null) {
			return null;
		}
		this.assignScalarFields(mergeRequest, entity);
		return this.banquetVerifyApplicationRepository.saveAndFlush(entity);
	}

	@Override
	@Transactional
	public void batchSumbitBanquetVfyApplication(List<Long> ids) {
		List<BanquetVerifyApplication> list = new ArrayList<BanquetVerifyApplication>();
		if (CollectionUtils.isNotEmpty(ids)) {
			for (Long id : ids) {
				BanquetVerifyApplication banquetVerifyApplication = this.banquetVerifyApplicationRepository.findOne(id);
				if (banquetVerifyApplication != null) {
					banquetVerifyApplication.setApplyTime(Calendar.getInstance());
					banquetVerifyApplication.setStatus(BanquetVerifyApplicationStatus.DELIVERED);
					list.add(banquetVerifyApplication);
				}
			}
			if (list.size() > 0) {
				this.banquetVerifyApplicationRepository.save(list);
			}
		}
	}

	@Override
	public BanquetVerifyApplication findOneById(Long id) {
		BanquetVerifyApplication anquetVerifyApplication = this.banquetVerifyApplicationRepository.findOne(id);
		if (anquetVerifyApplication == null) {
			return null;
		}
		return anquetVerifyApplication;
	}

	@Override
	public void batchSignBanquetVfyApplication(List<Long> ids) {
		if (CollectionUtils.isNotEmpty(ids)) {
			for (Long id : ids) {
				BanquetVerifyApplication banquetVerifyApplication = this.banquetVerifyApplicationRepository.findOne(id);
				BanquetVerification verification = new BanquetVerification();
				if (banquetVerifyApplication != null) {
					banquetVerifyApplication.setStatus(BanquetVerifyApplicationStatus.WAITFORVERIFY);
					BanquetVerifyApplication application = banquetVerifyApplicationRepository.saveAndFlush(banquetVerifyApplication);
					if (application != null) {
						verification.setBanquetVerifyApplication(application);
						String verificationNumber = generateVerificationNumber(application);
						verification.setVerificationNumber(verificationNumber);
						banquetVerificationRepository.saveAndFlush(verification);
					}
				}
			}
		}
	}
	
	private String generateVerificationNumber (BanquetVerifyApplication application) {
		String id = application.getId().toString();
		int idLength = id.length();
		switch (idLength) {
		case 1:
			id = "000" + id;
			break;
		case 2:
			id = "00" + id;
			break;
		case 3:
			id = "0" + id;
			break;
		default:
			break;
		}
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		id = sf.format(date) + id;
		return id;
	}

	@Override
	public int findRbatedByBanquetId(long banquetId) {
		List<BanquetItem> list = this.banquetItemRepository.findRbatedByBanquetIdAndIsPaid(banquetId);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.size();
		}
		return 0;
	}
	
}
