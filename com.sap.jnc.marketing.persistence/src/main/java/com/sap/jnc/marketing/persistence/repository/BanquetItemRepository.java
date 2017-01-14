package com.sap.jnc.marketing.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.BanquetItem;

/**
 * @author C5205383 Kevin Ren
 */
@NoRepositoryBean
public interface BanquetItemRepository extends JpaRepository<BanquetItem, String> {
	
	public List<BanquetItem> findReportedByBanquetIdAndCapInnerCode(long banquetId, String capInnerCode);
	
	public List<BanquetItem> findReportedByBanquetIdAndBoxId(long banquetId, String boxId);
	
	public List<BanquetItem> findReportedByBanquetId(long banquetId);

	public BanquetItem findbyId(long id);
	
	public List<BanquetItem> findRbatedByBanquetIdAndIsPaid(long banquetId);

}
