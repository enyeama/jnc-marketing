package com.sap.jnc.marketing.persistence.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.criteria.ka.KAAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.criteria.ka.KAExportCriteriaRequest;
import com.sap.jnc.marketing.persistence.model.Terminal;

@NoRepositoryBean
public interface TerminalRepository extends JpaRepository<Terminal, Long> {

	Terminal findTerminalById(Long id);

	List<Terminal> findBySalesman(String salePosExtId, String titleKey);

	List<Terminal> findByCreater(String createrId, String titleKey);

	List<Terminal> findTerminalsByCityManager(String cityId);

	Page<Terminal> advanceKASearch(KAAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest);

	List<Terminal> findByIds(Collection<Long> ids);

	List<Terminal> findByExternalIds(Collection<String> externalIds);

	List<Terminal> findAllKAs(KAExportCriteriaRequest criteria);

	List<Terminal> findByKASpecialExternalId(String externalId);

	List<Terminal> findByKAName(String kaSpecialExternalId, String kaName);

	List<Terminal> findByKaSales(String salesManId, String titleKey);

	List<Terminal> findAllByOrganizationCode(String organizationCode);

	Page<Terminal> findAllWithNoUserReference(PageRequest pageRequest);
}
