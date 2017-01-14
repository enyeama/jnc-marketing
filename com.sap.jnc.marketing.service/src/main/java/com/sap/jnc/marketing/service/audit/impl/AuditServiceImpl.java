package com.sap.jnc.marketing.service.audit.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.request.audit.AuditAssignInfoRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditAssignRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditBriefRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditDetailRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditItemsRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditOfficeAssignRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditOfficeUnassignRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditPictureRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditResultChangeRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditStatusChangeRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditTaskGenerateRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditTaskInfoRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditTaskRequest;
import com.sap.jnc.marketing.dto.request.audit.PendingAuditRequest;
import com.sap.jnc.marketing.dto.request.audit.WeChatAuditDetailRequest;
import com.sap.jnc.marketing.dto.response.audit.AuditDetailResponse;
import com.sap.jnc.marketing.dto.response.audit.AuditItemResponse;
import com.sap.jnc.marketing.dto.response.audit.AuditPictureResponse;
import com.sap.jnc.marketing.persistence.criteria.audit.AuditTaskSearchCriteria;
import com.sap.jnc.marketing.persistence.criteria.audit.AuditorOfficeAssignmentSeatchCriteria;
import com.sap.jnc.marketing.persistence.enumeration.AuditResult;
import com.sap.jnc.marketing.persistence.enumeration.AuditStatus;
import com.sap.jnc.marketing.persistence.enumeration.AuditType;
import com.sap.jnc.marketing.persistence.model.Audit;
import com.sap.jnc.marketing.persistence.model.AuditBanquet;
import com.sap.jnc.marketing.persistence.model.AuditExhibition;
import com.sap.jnc.marketing.persistence.model.AuditItem;
import com.sap.jnc.marketing.persistence.model.AuditPicture;
import com.sap.jnc.marketing.persistence.model.AuditPromotion;
import com.sap.jnc.marketing.persistence.model.AuditTerminal;
import com.sap.jnc.marketing.persistence.model.Banquet;
import com.sap.jnc.marketing.persistence.model.DepartmentView;
import com.sap.jnc.marketing.persistence.model.Employee;
import com.sap.jnc.marketing.persistence.model.EmployeeView;
import com.sap.jnc.marketing.persistence.model.Exhibition;
import com.sap.jnc.marketing.persistence.model.PositionView;
import com.sap.jnc.marketing.persistence.model.ProvinceManagerOfficeAssignment;
import com.sap.jnc.marketing.persistence.model.Region;
import com.sap.jnc.marketing.persistence.model.Terminal;
import com.sap.jnc.marketing.persistence.model.TerminalOrder;
import com.sap.jnc.marketing.persistence.model.ValidityPeriod;
import com.sap.jnc.marketing.persistence.repository.AuditBanquetRepository;
import com.sap.jnc.marketing.persistence.repository.AuditExhibitionRepository;
import com.sap.jnc.marketing.persistence.repository.AuditItemRepository;
import com.sap.jnc.marketing.persistence.repository.AuditPictureRepository;
import com.sap.jnc.marketing.persistence.repository.AuditPromotionRepository;
import com.sap.jnc.marketing.persistence.repository.AuditRepository;
import com.sap.jnc.marketing.persistence.repository.AuditTerminalRepository;
import com.sap.jnc.marketing.persistence.repository.BanquetRepository;
import com.sap.jnc.marketing.persistence.repository.DepartmentViewRepository;
import com.sap.jnc.marketing.persistence.repository.EmployeeRepository;
import com.sap.jnc.marketing.persistence.repository.EmployeeViewRepository;
import com.sap.jnc.marketing.persistence.repository.ExhibitionRepository;
import com.sap.jnc.marketing.persistence.repository.PositionRepository;
import com.sap.jnc.marketing.persistence.repository.PositionViewRepository;
import com.sap.jnc.marketing.persistence.repository.ProvinceManagerOfficeAssignmentRepository;
import com.sap.jnc.marketing.persistence.repository.RegionRepository;
import com.sap.jnc.marketing.persistence.repository.TerminalOrderRepository;
import com.sap.jnc.marketing.persistence.repository.TerminalRepository;
import com.sap.jnc.marketing.service.audit.AuditService;
import com.sap.jnc.marketing.service.exception.FieldErrorBodyAudit;
import com.sap.jnc.marketing.service.exception.ResponseBodyDBAuditException;

import me.chanjar.weixin.common.util.StringUtils;

/**
 * @author C5231393 Xu Xiaolei
 * @author I330182 Vokda Li
 */
@Service
@Transactional
public class AuditServiceImpl implements AuditService {

	private static String EXTERNAL_PROVINCE_MANAGER = "503";

	private static String EXTERNAL_AUDITOR = "12";

	private static String EXTERNAL_OFFICE_MANAGER = "25";

	@Autowired
	private AuditTerminalRepository auditTerminalRepository;

	@Autowired
	private AuditBanquetRepository auditBanquetRepository;

	@Autowired
	private AuditExhibitionRepository auditExhibitionRepository;

	@Autowired
	private AuditPromotionRepository auditPromotionRepository;

	@Autowired
	private ExhibitionRepository exhibitionRepository;

	@Autowired
	private TerminalRepository terminalRepository;

	@Autowired
	private TerminalOrderRepository terminalOrderRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeViewRepository employeeViewRepository;

	@Autowired
	private BanquetRepository banquetRepository;

	@Autowired
	private AuditRepository auditRepository;

	@Autowired
	private PositionRepository positionRepository;

	@Autowired
	private PositionViewRepository positionViewRepository;

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	private DepartmentViewRepository departmentViewRepository;

	@Autowired
	private AuditItemRepository auditItemRepository;

	@Autowired
	private AuditPictureRepository auditPictureRepository;

	@Autowired
	private ProvinceManagerOfficeAssignmentRepository provinceManagerOfficeAssignmentRepository;

	@Override
	@Transactional
	public void generateAuditTask(AuditTaskGenerateRequest request) {
		if (request.getTargetId() == null) {
			return;
		}
		if (request.getType().equals(AuditType.TERMINAL)) {
			final AuditTerminal entity = new AuditTerminal();
			this.assignTerminalScalarFields(request, entity);
			this.auditTerminalRepository.saveAndFlush(entity);
		}
		else if (request.getType().equals(AuditType.BANQUET)) {
			final AuditBanquet entity = new AuditBanquet();
			this.assignBanquetScalarFields(request, entity);
			this.auditBanquetRepository.saveAndFlush(entity);
		}
		else if (request.getType().equals(AuditType.EXHIBITION)) {
			final AuditExhibition entity = new AuditExhibition();
			this.assignExhibitionScalarFields(request, entity);
			this.auditExhibitionRepository.saveAndFlush(entity);
		}
		else if (request.getType().equals(AuditType.PROMOTION)) {
			final AuditPromotion entity = new AuditPromotion();
			this.assignPromotionScalarFields(request, entity);
			this.auditPromotionRepository.saveAndFlush(entity);
		}
	}

	@Transactional(readOnly = true)
	private void assignTerminalScalarFields(AuditTaskGenerateRequest request, final AuditTerminal entity) {
		this.generateTerminalAuditInfo(entity, request);
	}

	@Transactional(readOnly = true)
	private void assignBanquetScalarFields(AuditTaskGenerateRequest request, final AuditBanquet entity) {
		this.generateBanquetAuditInfo(entity, request);
	}

	@Transactional(readOnly = true)
	private void assignExhibitionScalarFields(AuditTaskGenerateRequest request, final AuditExhibition entity) {
		this.generateExhibitionAuditInfo(entity, request);
	}

	@Transactional(readOnly = true)
	private void assignPromotionScalarFields(AuditTaskGenerateRequest request, final AuditPromotion entity) {
		this.generatePromotionAuditInfo(entity, request);
	}

	@Transactional(readOnly = true)
	private void generateTerminalAuditInfo(AuditTerminal entity, AuditTaskGenerateRequest request) {
		entity.setStatus(AuditStatus.PENDING);
		// find terminal
		final Terminal terminal = this.terminalRepository.findTerminalById(request.getTargetId());
		if (terminal == null) {
			throw this.getExceptionEntity(new RuntimeException(), "terminalId", String.valueOf(request.getTargetId()), "终端不存在");
		}
		entity.setTerminal(terminal);
		entity.setRegion(terminal.getRegion());
		// find terminal office
		final List<PositionView> positions = terminal.getSalesmen();
		if (CollectionUtils.isEmpty(positions)) {
			throw this.getExceptionEntity(new RuntimeException(), "terminalId", String.valueOf(request.getTargetId()), "终端业务员不存在");
		}
		final String officeExternalId = findOffice(positions.get(0).getDepartment());
		if (officeExternalId == null) {
			throw this.getExceptionEntity(new RuntimeException(), "positionId", String.valueOf(positions.get(0).getId()), "终端业务员岗位对应办事处不存在");
		}
		entity.setOffice(positions.get(0).getDepartment().getDepartment());
		// find province manager
		final DepartmentView depResult = departmentViewRepository.findOne(officeExternalId);
		List<PositionView> provinceMangaerAndAuditorList = depResult.getProvinceManagers();
		if (CollectionUtils.isEmpty(provinceMangaerAndAuditorList)) {
			throw getExceptionEntity(new RuntimeException(), "officeId", officeExternalId, "办事处对应省区经理不存在");
		}
		String provinceManagerPostionId = null;
		PositionView provinceManagerPostionView = null;
		for (final PositionView positionView : provinceMangaerAndAuditorList) {
			if (positionView.getJob().getExternalId().equals(EXTERNAL_PROVINCE_MANAGER)) {
				provinceManagerPostionId = positionView.getExternalId();
				provinceManagerPostionView = positionView;
				break;
			}
		}
		if (provinceManagerPostionId == null) {
			throw this.getExceptionEntity(new RuntimeException(), "officeId", officeExternalId, "办事处省区经理岗位对应职务不存在");
		}
		if (CollectionUtils.isEmpty(provinceManagerPostionView.getEmployees())) {
			throw this.getExceptionEntity(new RuntimeException(), "PostionId", provinceManagerPostionId, "省区经理岗位对应人员不存在");
		}
		if (provinceManagerPostionView.getEmployee() == null || provinceManagerPostionView.getEmployee().getEmployee() == null) {
			throw getExceptionEntity(new RuntimeException(), "PostionId", provinceManagerPostionId, "省区经理岗位对应人员不存在");
		}
		entity.setProvinceManager(provinceManagerPostionView.getEmployee().getEmployee());

	}

	@Transactional(readOnly = true)
	private void generateBanquetAuditInfo(AuditBanquet entity, AuditTaskGenerateRequest request) {
		entity.setStatus(AuditStatus.PENDING);
		// find banquet info
		final Banquet banquet = this.banquetRepository.findOne(request.getTargetId());
		if (banquet == null) {
			throw this.getExceptionEntity(new RuntimeException(), "banquetId", String.valueOf(request.getTargetId()), "宴会报备信息不存在");
		}
		entity.setBanquet(banquet);
		entity.setRegion(banquet.getTerminal().getRegion());
		final String externalId = banquet.getHandler().getExternalId();
		if (StringUtils.isEmpty(externalId)) {
			throw this.getExceptionEntity(new RuntimeException(), "banquetId", String.valueOf(request.getTargetId()), "宴会业务员不存在");
		}
		final EmployeeView employeeView = employeeViewRepository.findOne(externalId);
		if (employeeView == null) {
			throw this.getExceptionEntity(new RuntimeException(), "externalId", externalId, "人员信息不存在");
		}
		final List<PositionView> positions = employeeView.getPositions();
		if (CollectionUtils.isEmpty(positions)) {
			throw this.getExceptionEntity(new RuntimeException(), "externalId", externalId, "人员岗位信息不存在");
		}
		final String officeExternalId = findOffice(positions.get(0).getDepartment());
		if (officeExternalId == null) {
			throw this.getExceptionEntity(new RuntimeException(), "positionId", String.valueOf(positions.get(0).getId()), "终端业务员岗位对应办事处不存在");
		}
		entity.setOffice(departmentViewRepository.findOne(officeExternalId).getDepartment());
		// find province manager
		final DepartmentView depResult = departmentViewRepository.findOne(officeExternalId);
		final List<PositionView> provinceMangaerAndAuditorList = depResult.getProvinceManagers();
		if (CollectionUtils.isEmpty(provinceMangaerAndAuditorList)) {
			throw this.getExceptionEntity(new RuntimeException(), "officeId", officeExternalId, "办事处对应省区经理不存在");
		}
		String provinceManagerPostionId = null;
		PositionView provinceManagerPostionView = null;
		for (final PositionView positionView : provinceMangaerAndAuditorList) {
			if (positionView.getJob().getExternalId().equals(EXTERNAL_PROVINCE_MANAGER)) {
				provinceManagerPostionId = positionView.getExternalId();
				provinceManagerPostionView = positionView;
				break;
			}
		}
		if (provinceManagerPostionId == null) {
			throw this.getExceptionEntity(new RuntimeException(), "officeId", officeExternalId, "办事处省区经理岗位对应职务不存在");
		}
		if (CollectionUtils.isEmpty(provinceManagerPostionView.getEmployees())) {
			throw getExceptionEntity(new RuntimeException(), "PostionId", provinceManagerPostionId, "省区经理岗位对应人员不存在");
		}
		if (provinceManagerPostionView.getEmployee() == null || provinceManagerPostionView.getEmployee().getEmployee() == null) {
			throw getExceptionEntity(new RuntimeException(), "PostionId", provinceManagerPostionId, "省区经理岗位对应人员不存在");
		}
		entity.setProvinceManager(provinceManagerPostionView.getEmployee().getEmployee());
	}

	@Transactional(readOnly = true)
	private void generateExhibitionAuditInfo(AuditExhibition entity, AuditTaskGenerateRequest request) {
		entity.setStatus(AuditStatus.PENDING);
		// find exhibition
		final Exhibition exhibition = exhibitionRepository.findOne(request.getTargetId());
		if (exhibition == null) {
			throw this.getExceptionEntity(new RuntimeException(), "exhibitionId", String.valueOf(request.getTargetId()), "陈列信息不存在");
		}
		entity.setExhibition(exhibition);
		// find terminal
		final Terminal terminal = exhibition.getTerminal();
		if (terminal == null) {
			throw this.getExceptionEntity(new RuntimeException(), "exhibitionId", String.valueOf(request.getTargetId()), "陈列对应终端信息不存在");
		}
		entity.setRegion(terminal.getRegion());
		// find terminal office
		final List<PositionView> positions = terminal.getSalesmen();
		if (CollectionUtils.isEmpty(positions)) {
			throw this.getExceptionEntity(new RuntimeException(), "terminalId", String.valueOf(terminal.getId()), "终端对应人员岗位信息不存在");
		}
		final String officeExternalId = findOffice(positions.get(0).getDepartment());
		if (officeExternalId == null) {
			throw this.getExceptionEntity(new RuntimeException(), "positionId", String.valueOf(positions.get(0).getId()), "终端业务员岗位对应办事处不存在");
		}
		entity.setOffice(this.departmentViewRepository.findOne(officeExternalId).getDepartment());
		// find province manager
		final DepartmentView depResult = departmentViewRepository.findOne(officeExternalId);
		List<PositionView> provinceMangaerAndAuditorList = depResult.getProvinceManagers();
		if (CollectionUtils.isEmpty(provinceMangaerAndAuditorList)) {
			throw this.getExceptionEntity(new RuntimeException(), "officeId", officeExternalId, "办事处对应省区经理不存在");
		}
		String provinceManagerPostionId = null;
		PositionView provinceManagerPostionView = null;
		for (final PositionView positionView : provinceMangaerAndAuditorList) {
			if (positionView.getJob().getExternalId().equals(EXTERNAL_PROVINCE_MANAGER)) {
				provinceManagerPostionId = positionView.getExternalId();
				provinceManagerPostionView = positionView;
				break;
			}
		}
		if (provinceManagerPostionId == null) {
			throw this.getExceptionEntity(new RuntimeException(), "officeId", officeExternalId, "办事处省区经理岗位对应职务不存在");
		}
		if (CollectionUtils.isEmpty(provinceManagerPostionView.getEmployees())) {
			throw this.getExceptionEntity(new RuntimeException(), "PostionId", provinceManagerPostionId, "省区经理岗位对应人员不存在");
		}
		if (provinceManagerPostionView.getEmployee() == null || provinceManagerPostionView.getEmployee().getEmployee() == null) {
			throw getExceptionEntity(new RuntimeException(), "PostionId", provinceManagerPostionId, "省区经理岗位对应人员不存在");
		}
		entity.setProvinceManager(provinceManagerPostionView.getEmployee().getEmployee());
	}

	@Transactional(readOnly = true)
	private void generatePromotionAuditInfo(AuditPromotion entity, AuditTaskGenerateRequest request) {
		entity.setStatus(AuditStatus.PENDING);
		// find terminalOrder
		final TerminalOrder terminalOrder = terminalOrderRepository.findOne(request.getTargetId());
		if (terminalOrder == null) {
			throw this.getExceptionEntity(new RuntimeException(), "promotionId", String.valueOf(request.getTargetId()), "访销信息不存在");
		}
		entity.setTerminalOrder(terminalOrder);
		// find terminal
		final Terminal terminal = terminalOrder.getTerminal();
		if (terminal == null) {
			throw this.getExceptionEntity(new RuntimeException(), "promotionId", String.valueOf(request.getTargetId()), "访销对应终端信息不存在");
		}
		entity.setRegion(terminal.getRegion());
		// find terminal office
		final List<PositionView> positions = terminal.getSalesmen();
		if (CollectionUtils.isEmpty(positions)) {
			throw this.getExceptionEntity(new RuntimeException(), "terminalId", String.valueOf(terminal.getId()), "终端对应人员岗位信息不存在");
		}
		final String officeExternalId = findOffice(positions.get(0).getDepartment());
		if (officeExternalId == null) {
			throw this.getExceptionEntity(new RuntimeException(), "positionId", String.valueOf(positions.get(0).getId()), "终端业务员岗位对应办事处不存在");
		}
		entity.setOffice(this.departmentViewRepository.findOne(officeExternalId).getDepartment());
		// find province manager
		final DepartmentView depResult = departmentViewRepository.findOne(officeExternalId);
		List<PositionView> provinceMangaerAndAuditorList = depResult.getProvinceManagers();
		if (CollectionUtils.isEmpty(provinceMangaerAndAuditorList)) {
			throw this.getExceptionEntity(new RuntimeException(), "officeId", officeExternalId, "办事处对应省区经理不存在");
		}
		String provinceManagerPostionId = null;
		PositionView provinceManagerPostionView = null;
		for (final PositionView positionView : provinceMangaerAndAuditorList) {
			if (positionView.getJob().getExternalId().equals(EXTERNAL_PROVINCE_MANAGER)) {
				provinceManagerPostionId = positionView.getExternalId();
				provinceManagerPostionView = positionView;
				break;
			}
		}
		if (provinceManagerPostionId == null) {
			throw this.getExceptionEntity(new RuntimeException(), "officeId", officeExternalId, "办事处省区经理岗位对应职务不存在");
		}
		if (CollectionUtils.isEmpty(provinceManagerPostionView.getEmployees())) {
			throw this.getExceptionEntity(new RuntimeException(), "PostionId", provinceManagerPostionId, "省区经理岗位对应人员不存在");
		}
		if (provinceManagerPostionView.getEmployee() == null || provinceManagerPostionView.getEmployee().getEmployee() == null) {
			throw getExceptionEntity(new RuntimeException(), "PostionId", provinceManagerPostionId, "省区经理岗位对应人员不存在");
		}
		entity.setProvinceManager(provinceManagerPostionView.getEmployee().getEmployee());
	}

	@Override
	@Transactional(readOnly = true)
	public Page<AuditTerminal> searchTerminalAudits(AuditTaskSearchCriteria criteria, PageRequest pageRequest) {
		final List<Region> regionList = this.findRegions(criteria);
		return this.auditTerminalRepository.searchAudits(criteria, pageRequest, regionList);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<AuditBanquet> searchBanquetAudits(AuditTaskSearchCriteria criteria, PageRequest pageRequest) {
		final List<Region> regionList = this.findRegions(criteria);
		return this.auditBanquetRepository.searchAudits(criteria, pageRequest, regionList);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<AuditExhibition> searchExhibitionAudits(AuditTaskSearchCriteria criteria, PageRequest pageRequest) {
		final List<Region> regionList = this.findRegions(criteria);
		return this.auditExhibitionRepository.searchAudits(criteria, pageRequest, regionList);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<AuditPromotion> searchPromotionAudits(AuditTaskSearchCriteria criteria, PageRequest pageRequest) {
		final List<Region> regionList = this.findRegions(criteria);
		return this.auditPromotionRepository.searchAudits(criteria, pageRequest, regionList);
	}

	private List<Region> findRegions(AuditTaskSearchCriteria criteria) {
		List<Region> regionList = new ArrayList<Region>();
		String provinceId = null;
		String cityId = null;
		String countyId = null;
		if (criteria.getProvinceRegion() != null) {
			provinceId = criteria.getProvinceRegion();
		}
		if (criteria.getCityRegion() != null) {
			cityId = criteria.getCityRegion();
		}
		if (criteria.getCountyRegion() != null) {
			countyId = criteria.getCountyRegion();
		}
		if (provinceId != null && cityId != null && countyId != null) {
			Region countyRegion = this.regionRepository.findAllWithCounty(provinceId, cityId, countyId);
			regionList.add(countyRegion);
		}
		if (provinceId != null && cityId != null && countyId == null) {
			regionList = this.regionRepository.findAllWithCityAndCounty(provinceId, cityId);
		}
		if (provinceId != null && cityId == null && countyId == null) {
			regionList = this.regionRepository.findAllWithProvinceAndCityAndCounty(provinceId);
		}
		return regionList;
	}

	@Override
	@Transactional
	public void assignAuditTasks(AuditAssignRequest request) {
		final List<Long> idsList = request.getAudits().stream().map(audit -> audit.getId()).collect(Collectors.toList());
		final List<Audit> list = auditRepository.findAllByAuditIds(idsList);
		for (final AuditAssignInfoRequest requestAudit : request.getAudits()) {
			final Audit foundAudit = IterableUtils.find(list, (mergingAudit) -> {
				return Objects.equals(mergingAudit.getId(), requestAudit.getId());
			});
			final Calendar cal = Calendar.getInstance();
			foundAudit.setAssignTime(cal);
			foundAudit.setAuditor(this.employeeRepository.findOne(requestAudit.getAuditorId()));
			foundAudit.setAuditorPosition(this.positionRepository.findOne(requestAudit.getAuditorPositionId()));
			foundAudit.setStatus(AuditStatus.ASSIGNED);
			this.auditRepository.save(foundAudit);
		}
	}

	@Override
	@Transactional
	public void auditTask(AuditTaskRequest request) {
		for (AuditTaskInfoRequest item : request.getAudits()) {
			Audit audit = auditRepository.findOne(item.getId());
			if (audit == null) {
				throw this.getExceptionEntity(new RuntimeException(), "auditd", String.valueOf(item.getId()), "稽核信息不存在");
			}
			audit.setStatus(item.getAuditStatus());
			this.auditRepository.save(audit);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public AuditDetailResponse findOneWithAllDetail(Long id) {
		if (id == null) {
			return null;
		}
		final List<Audit> audits = this.auditRepository.findAllWithItemAndPictures(id);
		final AuditDetailResponse result = new AuditDetailResponse();
		final List<AuditItemResponse> items = new ArrayList<AuditItemResponse>();
		final List<AuditPictureResponse> pictures = new ArrayList<AuditPictureResponse>();
		for (final Audit audit : audits) {
			final AuditItemResponse item = new AuditItemResponse(audit);
			items.add(item);
			final AuditPictureResponse picture = new AuditPictureResponse(audit);
			pictures.add(picture);
		}
		result.setAuditItems(items);
		result.setAuditPicture(pictures);
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Region> findProvinceRegions() {
		return this.regionRepository.findAllWithProvince();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Region> findCityRegions(String provinceId) {
		return this.regionRepository.findAllByProvinceId(provinceId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Region> findCountyRegions(String provinceId, String cityId) {
		return this.regionRepository.findAllByCityId(provinceId, cityId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<EmployeeView> findAuditors(Long id) {
		if (id == null) {
			return null;
		}
		Employee employee = employeeRepository.findOne(id);
		if (employee == null) {
			throw this.getExceptionEntity(new RuntimeException(), "employeeId", String.valueOf(id), "人员信息不存在");
		}
		EmployeeView employeeView = employeeViewRepository.findOne(employee.getExternalId());
		if (employeeView == null) {
			throw this.getExceptionEntity(new RuntimeException(), "employeeId", String.valueOf(id), "人员信息不存在");
		}
		List<PositionView> posResult = employeeView.getPosition().getOffice().getProvinceManagers();
		if (CollectionUtils.isEmpty(posResult)) {
			throw this.getExceptionEntity(new RuntimeException(), "employeeId", String.valueOf(id), "人员岗位信息不存在");
		}
		List<PositionView> auditorPostionViewList = new ArrayList<PositionView>();
		for (PositionView positionView : posResult) {
			if (positionView.getJob().getExternalId().equals(EXTERNAL_AUDITOR)) {
				auditorPostionViewList.add(positionView);
			}
		}
		List<EmployeeView> empResult = employeeViewRepository.findAllByIds(auditorPostionViewList);
		if (CollectionUtils.isEmpty(empResult)) {
			throw this.getExceptionEntity(new RuntimeException(), "employeeId", String.valueOf(id), "人员信息不存在");
		}
		return empResult;
	}

	@Override
	@Transactional(readOnly = true)
	public List<PositionView> findPositions(Long id) {
		List<PositionView> posResult = new ArrayList<PositionView>();
		if (id == null) {
			posResult = positionViewRepository.findAll();
		}
		else {
			final EmployeeView empResult = employeeViewRepository.findOneById(id);
			if (empResult == null) {
				throw this.getExceptionEntity(new RuntimeException(), "employeeId", String.valueOf(id), "人员信息不存在");
			}
			final String positionId = empResult.getPosition().getExternalId();
			if (positionId == null) {
				throw this.getExceptionEntity(new RuntimeException(), "positionId", positionId, "人员对应岗位信息不存在");
			}
			final PositionView posDepResult = this.positionViewRepository.findOneByPositionId(positionId);
			if (posDepResult == null) {
				throw this.getExceptionEntity(new RuntimeException(), "positionId", String.valueOf(positionId), "人员岗位对应办事处信息不存在");
			}
			final DepartmentView depResult = this.departmentViewRepository.findAllById(posDepResult.getDepartment().getExternalId());
			if (depResult == null) {
				throw this.getExceptionEntity(new RuntimeException(), "positionId", String.valueOf(positionId), "办事处信息不存在");
			}
			posResult = depResult.getPositions();
		}
		return posResult;
	}

	@Override
	@Transactional
	public void changeStatus(AuditStatusChangeRequest request) {
		if (request.getId() == null) {
			return;
		}
		Audit audit = this.auditRepository.findOne(request.getId());
		if (audit == null) {
			throw this.getExceptionEntity(new RuntimeException(), "auditId", String.valueOf(request.getId()), "稽核信息不存在");
		}
		AuditType type = audit.getType();
		if (type.equals(AuditType.BANQUET)) {
			AuditBanquet auditBanquet = auditBanquetRepository.findOne(request.getId());
			auditBanquet.setStatus(request.getAuditStatus());
			this.auditBanquetRepository.saveAndFlush(auditBanquet);
		}
		else if (type.equals(AuditType.TERMINAL)) {
			AuditTerminal auditTerminal = auditTerminalRepository.findOne(request.getId());
			auditTerminal.setStatus(request.getAuditStatus());
			this.auditTerminalRepository.saveAndFlush(auditTerminal);
		}
		else if (type.equals(AuditType.EXHIBITION)) {
			AuditExhibition auditExhibition = auditExhibitionRepository.findOne(request.getId());
			auditExhibition.setStatus(request.getAuditStatus());
			this.auditExhibitionRepository.saveAndFlush(auditExhibition);
		}
		else if (type.equals(AuditType.PROMOTION)) {
			AuditPromotion auditPromotion = auditPromotionRepository.findOne(request.getId());
			auditPromotion.setStatus(request.getAuditStatus());
			this.auditPromotionRepository.saveAndFlush(auditPromotion);
		}
	}

	@Override
	@Transactional
	public void changeResult(AuditResultChangeRequest request) {
		if (request.getId() == null) {
			return;
		}
		Audit audit = this.auditRepository.findOne(request.getId());
		if (audit == null) {
			throw this.getExceptionEntity(new RuntimeException(), "auditId", String.valueOf(request.getId()), "稽核信息不存在");
		}
		AuditType type = audit.getType();
		if (type.equals(AuditType.BANQUET)) {
			AuditBanquet auditBanquet = auditBanquetRepository.findOne(request.getId());
			auditBanquet.setAuditResult(request.getResult());
			this.auditBanquetRepository.saveAndFlush(auditBanquet);
		}
		else if (type.equals(AuditType.TERMINAL)) {
			AuditTerminal auditTerminal = auditTerminalRepository.findOne(request.getId());
			auditTerminal.setAuditResult(request.getResult());
			this.auditTerminalRepository.saveAndFlush(auditTerminal);
		}
		else if (type.equals(AuditType.EXHIBITION)) {
			AuditExhibition auditExhibition = auditExhibitionRepository.findOne(request.getId());
			auditExhibition.setAuditResult(request.getResult());
			this.auditExhibitionRepository.saveAndFlush(auditExhibition);
		}
		else if (type.equals(AuditType.PROMOTION)) {
			AuditPromotion auditPromotion = auditPromotionRepository.findOne(request.getId());
			auditPromotion.setAuditResult(request.getResult());
			this.auditPromotionRepository.saveAndFlush(auditPromotion);
		}

	}

	@Override
	@Transactional
	public void submitlAuditDetails(AuditDetailRequest request) {
		final Long auditId = request.getId();
		final Audit audit = this.auditRepository.findOne(auditId);
		if (!CollectionUtils.isEmpty(request.getItemList())) {
			List<AuditItem> items = auditItemRepository.findAllByAuditId(auditId);
			this.auditItemRepository.delete(items);
		}
		for (final AuditItemsRequest items : request.getItemList()) {
			final AuditItem auditItem = new AuditItem();
			auditItem.setAudit(audit);
			if (!StringUtils.isEmpty(items.getFieldName())) {
				auditItem.setFieldName(items.getFieldName());
			}
			if (!StringUtils.isEmpty(items.getFieldValue())) {
				auditItem.setFieldValue(items.getFieldValue());
			}
			if (items.getIsConfirm() != null) {
				auditItem.setIsConfirm(items.getIsConfirm());
				if (!items.getIsConfirm()) {
					audit.setAuditResult(AuditResult.FAILD);
				}
			}
			if (!StringUtils.isEmpty(items.getComment())) {
				auditItem.setComment(items.getComment());
			}
			this.auditItemRepository.saveAndFlush(auditItem);
		}
		if (!CollectionUtils.isEmpty(request.getPictureList())) {
			List<AuditPicture> pictures = auditPictureRepository.findAllByAuditId(auditId);
			this.auditPictureRepository.delete(pictures);
		}
		for (final AuditPictureRequest pictures : request.getPictureList()) {
			final AuditPicture auditPicture = new AuditPicture();
			auditPicture.setAudit(audit);
			if (pictures.getType() != null) {
				auditPicture.setType(pictures.getType());
			}
			if (!StringUtils.isEmpty(pictures.getUrl())) {
				auditPicture.setUrl(pictures.getUrl());
			}
			if (!StringUtils.isEmpty(pictures.getAddress())) {
				auditPicture.setAddress(pictures.getAddress());
			}
			if (!StringUtils.isEmpty(pictures.getComment())) {
				auditPicture.setComment(pictures.getComment());
			}
			if (pictures.getProcessorId() != null) {
				final Employee processor = this.employeeRepository.findOne(pictures.getProcessorId());
				auditPicture.setProcessor(processor);
			}
			this.auditPictureRepository.saveAndFlush(auditPicture);
		}

		AuditStatus status = AuditStatus.Audited;
		audit.setStatus(status);
		final Calendar cal = Calendar.getInstance();
		audit.setAuditTime(cal);
		this.auditRepository.saveAndFlush(audit);
	}

	@Override
	@Transactional(readOnly = true)
	public List<EmployeeView> findAllAuditors() {
		return this.employeeViewRepository.findAllAuditors();
	}

	@Override
	@Transactional(readOnly = true)
	public List<DepartmentView> findAllOffices() {
		return this.departmentViewRepository.findAllOffices();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<EmployeeView> searchAuditorOfficeUnassignments(AuditorOfficeAssignmentSeatchCriteria criteria, PageRequest pageRequest) {
		return this.employeeViewRepository.advanceSearch(criteria, pageRequest);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ProvinceManagerOfficeAssignment> searchAuditorOfficeAssignments(AuditorOfficeAssignmentSeatchCriteria criteria,
		PageRequest pageRequest) {
		return this.provinceManagerOfficeAssignmentRepository.advanceSearch(criteria, pageRequest);
	}

	@Override
	@Transactional
	public void assignAuditorOffice(AuditOfficeAssignRequest request) {
		// 判断待分配人是稽核员还是省区经理
		PositionView posView = positionViewRepository.findOne(request.getPositionExternalId());
		if (posView == null) {
			throw this.getExceptionEntity(new RuntimeException(), "positionExternalId", request.getPositionExternalId(), "待分配人员岗位不存在");
		}
		if (posView.getJob() == null) {
			throw this.getExceptionEntity(new RuntimeException(), "positionExternalId", request.getPositionExternalId(), "待分配人员职务不存在");
		}
		List<ProvinceManagerOfficeAssignment> result = null;
		if (posView.getJob().getExternalId().equals(EXTERNAL_PROVINCE_MANAGER)) {
			result = this.provinceManagerOfficeAssignmentRepository.findOneByPositionId(request.getOfficeExternalId(), request
				.getPositionExternalId(), request.getValidFrom(), request.getValidTo(), true);
		}
		else if (posView.getJob().getExternalId().equals(EXTERNAL_AUDITOR)) {
			result = this.provinceManagerOfficeAssignmentRepository.findOneByPositionId(request.getOfficeExternalId(), request
				.getPositionExternalId(), request.getValidFrom(), request.getValidTo(), false);
		}
		if (!CollectionUtils.isEmpty(result)) {
			throw this.getExceptionEntity(new RuntimeException(), "positionExternalId", request.getPositionExternalId(), "待分配人员已经分配了办事处");
		}
		else {
			ProvinceManagerOfficeAssignment item = new ProvinceManagerOfficeAssignment();
			DepartmentView depView = departmentViewRepository.findOne(request.getOfficeExternalId());
			if (depView == null) {
				throw this.getExceptionEntity(new RuntimeException(), "departmentExternalId", request.getOfficeExternalId(), "办事处不存在");
			}
			item.setOffice(depView);
			item.setProvinceManager(posView);
			ValidityPeriod validityPeriod = new ValidityPeriod();
			validityPeriod.setValidFrom(request.getValidFrom());
			validityPeriod.setValidTo(request.getValidTo());
			item.setValidityPeriod(validityPeriod);
			provinceManagerOfficeAssignmentRepository.saveAndFlush(item);
		}

	}

	@Override
	@Transactional
	public void unassignAuditorOffice(AuditOfficeUnassignRequest request) {
		ProvinceManagerOfficeAssignment item = provinceManagerOfficeAssignmentRepository.findOne(request.getId());
		if (item == null) {
			throw this.getExceptionEntity(new RuntimeException(), "id", String.valueOf(request.getId()), "该人员分配办事处信息不存在");
		}
		ValidityPeriod validityPeriod = new ValidityPeriod();
		validityPeriod.setValidTo(request.getValidTo());
		provinceManagerOfficeAssignmentRepository.saveAndFlush(item);

	}

	@Override
	@Transactional(readOnly = true)
	public Long getPendingAuditCounts(PendingAuditRequest request) {
		return auditRepository.countByTypeAndStatus(request.getAuditorId(), request.getAuditType(), request.getAuditStatus());
	}

	@Override
	@Transactional(readOnly = true)
	public List<AuditTerminal> findAllByTypeAndStatus(AuditBriefRequest request) {
		return auditTerminalRepository.findAllByTypeAndStatus(request.getAuditorId(), request.getAuditType(), request.getAuditStatus());
	}

	@Override
	@Transactional
	public AuditTerminal findOneTerminalAuditById(WeChatAuditDetailRequest request) {
		return auditTerminalRepository.findOneTerminalAuditById(request.getId());
	}

	@Override
	@Transactional(readOnly = true)
	public List<AuditBanquet> findAllBanquetAuditByAuidtorIdAndTypeAndStatus(AuditBriefRequest request) {
		return auditBanquetRepository.findAllByAuditorIdAndTypeAndStatus(request.getAuditorId(), request.getAuditType(), request.getAuditStatus());
	}

	@Override
	@Transactional(readOnly = true)
	public AuditBanquet findOneBanquetAuditById(WeChatAuditDetailRequest request) {
		return auditBanquetRepository.findOneById(request.getId());
	}

	@Override
	@Transactional(readOnly = true)
	public List<AuditItem> findOneAuditItemListById(AuditBriefRequest request) {
		if (request.getAuditId() == null) {
			return null;
		}
		final List<AuditItem> items = auditItemRepository.findAllByAuditId(request.getAuditId());
		if (CollectionUtils.isEmpty(items)) {
			if (request.getAuditType().equals(AuditType.TERMINAL)) {
				final AuditTerminal auditTerminal = this.auditTerminalRepository.findOneTerminalByTargetId(request.getAuditId());
				final AuditItem address = new AuditItem();
				address.setFieldName("address");
				address.setFieldValue(auditTerminal.getTerminal().getAddress());
				final AuditItem branchName = new AuditItem();
				branchName.setFieldName("branchName");
				branchName.setFieldValue(auditTerminal.getTerminal().getBranchName());
				final List<AuditItem> newTerminalAuditItem = new ArrayList<>();
				newTerminalAuditItem.add(address);
				newTerminalAuditItem.add(branchName);
				return newTerminalAuditItem;
			}
		}

		// }
		return auditItemRepository.findAllByAuditId(request.getAuditId());
	}

	@Override
	@Transactional(readOnly = true)
	public List<AuditPicture> findOneAuditPictureListById(Long id) {
		if (id == null) {
			return null;
		}
		return auditPictureRepository.findAllByAuditId(id);
	}

	/*
	 * This method is used to construct the error format of DB operation. The error will be returned to client via the exception handler in Controller
	 */
	private ResponseBodyDBAuditException getExceptionEntity(RuntimeException e, String field, String rejectValue, String message) {
		FieldErrorBodyAudit errors = new FieldErrorBodyAudit();
		List<FieldErrorBodyAudit.FieldErrorBodyItems> itemsBody = new ArrayList<FieldErrorBodyAudit.FieldErrorBodyItems>(1);
		FieldErrorBodyAudit.FieldErrorBodyItems itemBody = errors.new FieldErrorBodyItems(field, rejectValue, message);
		itemsBody.add(itemBody);
		errors.setError(itemsBody);
		return new ResponseBodyDBAuditException(e, errors);
	}

	private String findOffice(DepartmentView dep) {
		List<PositionView> list = dep.getPositions();
		for (PositionView pos : list) {
			if (pos.getJob() != null && pos.getJob().getExternalId().equals(EXTERNAL_OFFICE_MANAGER)) {
				return dep.getExternalId();
			}
		}
		if (dep.getSuperior() != null) {
			return findOffice(dep.getSuperior());
		}
		else {
			return null;
		}
	}

}
