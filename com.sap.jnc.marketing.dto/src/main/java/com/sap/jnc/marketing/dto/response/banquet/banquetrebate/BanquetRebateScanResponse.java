/**
 * 
 */
package com.sap.jnc.marketing.dto.response.banquet.banquetrebate;

import java.io.Serializable;
//import java.util.HashMap;
//import java.util.List;

//import com.sap.jnc.marketing.dto.shared.banquet.banquetrebate.BanquetRebateConfigInfo;
import com.sap.jnc.marketing.dto.shared.banquet.banquetrebate.BanquetRebateSingleInfo;

/**
 * @author Joel.Cheng I310645
 *
 */
public class BanquetRebateScanResponse implements Serializable {

	private static final long serialVersionUID = 4856867097357823051L;
	
	private BanquetRebateSingleInfo banquetRebateSingleInfo;

	public BanquetRebateSingleInfo getBanquetRebateSingleInfo() {
		return banquetRebateSingleInfo;
	}

	public void setBanquetRebateSingleInfo(BanquetRebateSingleInfo banquetRebateSingleInfo) {
		this.banquetRebateSingleInfo = banquetRebateSingleInfo;
	}

	public BanquetRebateScanResponse() {
	}

	public BanquetRebateScanResponse(BanquetRebateSingleInfo banquetRebateSingleInfo) {
		if (banquetRebateSingleInfo == null) {
			return;
		}
		this.setBanquetRebateSingleInfo(banquetRebateSingleInfo);
	}
}
