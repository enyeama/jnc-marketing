package com.sap.jnc.marketing.dto.shared.banquet.banquetrebate;

import java.io.Serializable;
import java.util.List;

/**
 * @author Joel.Cheng I310645
 *
 */
public class BanquetRebateSingleInfo implements Serializable {

	private static final long serialVersionUID = -6921162424163631523L;

	private String scanCode; 

	private String scanResult;
	
	private Long id;
	
	private List<BanquetRebateConfigInfo> banquetRebateConfigInfoList;

	public BanquetRebateSingleInfo() {
	}

	public String getScanCode() {
		return scanCode;
	}

	public void setScanCode(String scanCode) {
		this.scanCode = scanCode;
	}

	public String getScanResult() {
		return scanResult;
	}

	public void setScanResult(String scanResult) {
		this.scanResult = scanResult;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<BanquetRebateConfigInfo> getBanquetRebateConfigInfoList() {
		return banquetRebateConfigInfoList;
	}

	public void setBanquetRebateConfigInfoList(List<BanquetRebateConfigInfo> banquetRebateConfigInfoList) {
		this.banquetRebateConfigInfoList = banquetRebateConfigInfoList;
	}

}
