/**
 * 
 */
package com.sap.jnc.marketing.service.banquet.rebate;

import java.util.List;

//import java.util.HashMap;

import com.sap.jnc.marketing.dto.request.banquet.banquetrebate.BanquetRebateScanRequest;
import com.sap.jnc.marketing.dto.response.banquet.banquetrebate.BanquetRebateResponse;
import com.sap.jnc.marketing.dto.shared.banquet.banquetrebate.BanquetRebateSingleInfo;
import com.sap.jnc.marketing.persistence.model.BanquetItem;

/**
 * @author Joel.Cheng I310645
 *
 */
public interface BanquetRebateScanService {

	// public HashMap<String, Object> scan(BanquetRebateScanRequest request);
	public BanquetRebateSingleInfo scan(BanquetRebateScanRequest request);

	public List<BanquetItem> getItemList(Long banquetId);

	public List<String> getItemBoxIdList(Long banquetId);

	public BanquetRebateResponse rebate(long banquetId, List<BanquetRebateSingleInfo> banquetRebateSingleInfoList);
}
