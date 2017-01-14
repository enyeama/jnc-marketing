/**
 * 
 */
package com.sap.jnc.marketing.dto.response.banquet.banquetrebate;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.BanquetItem;

/**
 * @author Joel.Cheng I310645
 *
 */
public class BanquetRebateItemResponse implements Serializable {

	private static final String CHECKED = "0";

	private static final long serialVersionUID = 7139748710676212874L;
	
	private String checked;
	
	private String scanCode;
	
	public BanquetRebateItemResponse(){
		
	}
	
	public BanquetRebateItemResponse(BanquetItem banquetItem){
		if(banquetItem == null){
			return;
		}
		this.checked = CHECKED; //default is 0-unchecked
		this.scanCode = banquetItem.getBoxId();
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getScanCode() {
		return scanCode;
	}

	public void setScanCode(String scanCode) {
		this.scanCode = scanCode;
	}
	
}
