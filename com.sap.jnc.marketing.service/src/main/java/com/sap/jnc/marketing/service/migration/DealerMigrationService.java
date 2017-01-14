package com.sap.jnc.marketing.service.migration;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.dto.response.migration.DealerMigrationPageResponse.Item;
import com.sap.jnc.marketing.persistence.criteria.migration.DealerMigrationAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.Dealer;
/**
 * @author I323691 Marco Huang
 */
public interface DealerMigrationService {
	//经销商主数据页面分页查询
	Page<Dealer> queryDealer(DealerMigrationAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest);

	Workbook exportDealer(List<Item> dealerList);
}
