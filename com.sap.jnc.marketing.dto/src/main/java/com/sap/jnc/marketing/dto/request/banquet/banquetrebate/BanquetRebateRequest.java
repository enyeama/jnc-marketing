/**
 * 
 */
package com.sap.jnc.marketing.dto.request.banquet.banquetrebate;

import java.io.Serializable;
import java.util.List;

import com.sap.jnc.marketing.dto.shared.banquet.banquetrebate.BanquetRebateSingleInfo;

/**
 * @author Joel.Cheng I310645
 *
 */
public class BanquetRebateRequest implements Serializable {

	private static final long serialVersionUID = -3857267119445640651L;
	
	private long banquetId;

	private List<BanquetRebateSingleInfo> banquetRebateSingleInfoList;

	public BanquetRebateRequest() {

	}

	public long getBanquetId() {
		return banquetId;
	}

	public void setBanquetId(long banquetId) {
		this.banquetId = banquetId;
	}

	public List<BanquetRebateSingleInfo> getBanquetRebateSingleInfoList() {
		return banquetRebateSingleInfoList;
	}

	public void setBanquetRebateSingleInfoList(List<BanquetRebateSingleInfo> banquetRebateSingleInfoList) {
		this.banquetRebateSingleInfoList = banquetRebateSingleInfoList;
	}

}
