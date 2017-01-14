package com.sap.jnc.marketing.service.banquet;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;

import com.sap.jnc.marketing.dto.request.banquet.BanquetApproveRequest;
import com.sap.jnc.marketing.dto.request.banquet.BanquetMergeRequest;
import com.sap.jnc.marketing.dto.response.banquet.BanquetChannelRelProduct;
import com.sap.jnc.marketing.dto.response.banquet.BanquetEnumInfoResponse;
import com.sap.jnc.marketing.dto.response.banquet.BanquetRelatedInfoResponse;
import com.sap.jnc.marketing.dto.response.banquet.EmployeeInfoResponse;
import com.sap.jnc.marketing.persistence.criteria.banquet.BanquetSearchKeywordNode;
import com.sap.jnc.marketing.persistence.enumeration.BanquetApplicationType;
import com.sap.jnc.marketing.persistence.model.Banquet;
import com.sap.jnc.marketing.persistence.model.BanquetType;



/**
 * @author I332242 Zhu Qiang
 */
public interface BanquetService {
	
	Banquet findOne(Long id);

	Banquet create(BanquetMergeRequest request);

	Banquet update(Long id, BanquetMergeRequest request);

	void delete(Long id);
	
	Page<Banquet> advanceSearch(BanquetSearchKeywordNode searchCriteria, PageRequest pageRequest);
	
	List<Long> batchUpdate(BanquetApproveRequest banquetApproveRequest);
	
	List<BanquetType> findBanquetType();
	
	BanquetRelatedInfoResponse findBanquetRelatedInfo(Long empId);
	
	List<BanquetEnumInfoResponse> findBanquetEnumType(String type);
	
	List<EmployeeInfoResponse> findHandlerByCreatorId(Long creatorId);
	
	BanquetChannelRelProduct findBanquetChannel (BanquetApplicationType banquetApplicationType);
	
	List<String> findAllOffice();
	
	Banquet assignHandler(BanquetMergeRequest request);
	
	Banquet cancelBanquet(@PathVariable Long empId);
}
