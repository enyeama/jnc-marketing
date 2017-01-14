/**
 * 
 */
package com.sap.jnc.marketing.dto.response;

import java.util.Collection;

/**
 * @author Quansheng Liu I075496
 */
public class CodeScanResponse {
	String productName;
	String productDescription;
	Collection<String> capInnerCodes;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Collection<String> getCapInnerCodes() {
		return capInnerCodes;
	}

	public void setCapInnerCodes(Collection<String> capInnerCodes) {
		this.capInnerCodes = capInnerCodes;
	}

}
