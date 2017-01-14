package com.sap.jnc.marketing.service.audit;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.dto.request.audit.AuditAssignRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditBriefRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditDetailRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditOfficeAssignRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditOfficeUnassignRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditResultChangeRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditStatusChangeRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditTaskGenerateRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditTaskRequest;
import com.sap.jnc.marketing.dto.request.audit.PendingAuditRequest;
import com.sap.jnc.marketing.dto.request.audit.WeChatAuditDetailRequest;
import com.sap.jnc.marketing.dto.response.audit.AuditDetailResponse;
import com.sap.jnc.marketing.persistence.criteria.audit.AuditTaskSearchCriteria;
import com.sap.jnc.marketing.persistence.criteria.audit.AuditorOfficeAssignmentSeatchCriteria;
import com.sap.jnc.marketing.persistence.model.AuditBanquet;
import com.sap.jnc.marketing.persistence.model.AuditExhibition;
import com.sap.jnc.marketing.persistence.model.AuditItem;
import com.sap.jnc.marketing.persistence.model.AuditPicture;
import com.sap.jnc.marketing.persistence.model.AuditPromotion;
import com.sap.jnc.marketing.persistence.model.AuditTerminal;
import com.sap.jnc.marketing.persistence.model.DepartmentView;
import com.sap.jnc.marketing.persistence.model.EmployeeView;
import com.sap.jnc.marketing.persistence.model.PositionView;
import com.sap.jnc.marketing.persistence.model.ProvinceManagerOfficeAssignment;
import com.sap.jnc.marketing.persistence.model.Region;

/**
 * @author C5231393 Xu Xiaolei
 * @author I330182 Vodka Li
 */
public interface AuditService {

	void generateAuditTask(AuditTaskGenerateRequest request);

	Page<AuditTerminal> searchTerminalAudits(AuditTaskSearchCriteria criteria, PageRequest pageRequest);

	Page<AuditBanquet> searchBanquetAudits(AuditTaskSearchCriteria criteria, PageRequest pageRequest);

	Page<AuditExhibition> searchExhibitionAudits(AuditTaskSearchCriteria criteria, PageRequest pageRequest);

	Page<AuditPromotion> searchPromotionAudits(AuditTaskSearchCriteria criteria, PageRequest pageRequest);

	void assignAuditTasks(AuditAssignRequest request);

	AuditDetailResponse findOneWithAllDetail(Long id);

	Long getPendingAuditCounts(PendingAuditRequest request);

	List<Region> findProvinceRegions();

	List<Region> findCityRegions(String provinceId);

	List<Region> findCountyRegions(String provinceId, String cityId);

	void auditTask(AuditTaskRequest request);

	List<EmployeeView> findAuditors(Long id);

	List<AuditTerminal> findAllByTypeAndStatus(AuditBriefRequest request);

	List<AuditBanquet> findAllBanquetAuditByAuidtorIdAndTypeAndStatus(AuditBriefRequest request);

	AuditTerminal findOneTerminalAuditById(WeChatAuditDetailRequest request);

	AuditBanquet findOneBanquetAuditById(WeChatAuditDetailRequest request);

	List<PositionView> findPositions(Long id);

	void changeStatus(AuditStatusChangeRequest request);

	void changeResult(AuditResultChangeRequest request);

	void submitlAuditDetails(AuditDetailRequest request);

	List<EmployeeView> findAllAuditors();

	List<DepartmentView> findAllOffices();

	void assignAuditorOffice(AuditOfficeAssignRequest request);

	void unassignAuditorOffice(AuditOfficeUnassignRequest request);

	Page<EmployeeView> searchAuditorOfficeUnassignments(AuditorOfficeAssignmentSeatchCriteria criteria, PageRequest pageRequest);

	Page<ProvinceManagerOfficeAssignment> searchAuditorOfficeAssignments(AuditorOfficeAssignmentSeatchCriteria criteria, PageRequest pageRequest);

	List<AuditItem> findOneAuditItemListById(AuditBriefRequest request);

	List<AuditPicture> findOneAuditPictureListById(Long id);

}
