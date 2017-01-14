package com.sap.jnc.marketing.dto.response.banquet;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import com.sap.jnc.marketing.persistence.enumeration.BanquetScanType;

/**
 * 
 * @author I324442 Sha Liu
 *
 */
public class BanquetTabVerificationHeaderResponse implements Serializable{

	private static final long serialVersionUID = -1662954160139008838L;
	
	private long verificationId;
	
	private long banquetId;
	
	private String banquetType;
	
	private BanquetScanType scanType;
	
	private String productName;
	
	private String dealerName;
	
	private String channelName;
	
	private int totalRebate;
	
	private BigDecimal bottleQuantity;
	
	private Integer boxQuantity;
	
	private Integer capQuantityOfFirstVerification;
	
	private Integer exteriorRingQuantityOfFirstVerification;
	
	private Integer interiorRingQuantityOfFirstVerification;
	
	private String verifierOfFirstVerification;
	
	private Calendar timeOfFirstVerification;
	
	private String commentOfFirstVerification;
	
	public long getVerificationId() {
		return verificationId;
	}

	public void setVerificationId(long verificationId) {
		this.verificationId = verificationId;
	}

	public long getBanquetId() {
		return banquetId;
	}

	public void setBanquetId(long banquetId) {
		this.banquetId = banquetId;
	}

	public String getBanquetType() {
		return banquetType;
	}

	public void setBanquetType(String banquetType) {
		this.banquetType = banquetType;
	}

	public BanquetScanType getScanType() {
		return scanType;
	}

	public void setScanType(BanquetScanType scanType) {
		this.scanType = scanType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	public int getTotalRebate() {
		return totalRebate;
	}

	public void setTotalRebate(int totalRebate) {
		this.totalRebate = totalRebate;
	}


	public BigDecimal getBottleQuantity() {
		return bottleQuantity;
	}

	public void setBottleQuantity(BigDecimal bottleQuantity) {
		this.bottleQuantity = bottleQuantity;
	}

	public Integer getBoxQuantity() {
		return boxQuantity;
	}

	public void setBoxQuantity(Integer boxQuantity) {
		this.boxQuantity = boxQuantity;
	}

	public Integer getCapQuantityOfFirstVerification() {
		return capQuantityOfFirstVerification;
	}

	public void setCapQuantityOfFirstVerification(Integer capQuantityOfFirstVerification) {
		this.capQuantityOfFirstVerification = capQuantityOfFirstVerification;
	}

	public Integer getExteriorRingQuantityOfFirstVerification() {
		return exteriorRingQuantityOfFirstVerification;
	}

	public void setExteriorRingQuantityOfFirstVerification(Integer exteriorRingQuantityOfFirstVerification) {
		this.exteriorRingQuantityOfFirstVerification = exteriorRingQuantityOfFirstVerification;
	}

	public Integer getInteriorRingQuantityOfFirstVerification() {
		return interiorRingQuantityOfFirstVerification;
	}

	public void setInteriorRingQuantityOfFirstVerification(Integer interiorRingQuantityOfFirstVerification) {
		this.interiorRingQuantityOfFirstVerification = interiorRingQuantityOfFirstVerification;
	}

	public String getVerifierOfFirstVerification() {
		return verifierOfFirstVerification;
	}

	public void setVerifierOfFirstVerification(String verifierOfFirstVerification) {
		this.verifierOfFirstVerification = verifierOfFirstVerification;
	}

	public Calendar getTimeOfFirstVerification() {
		return timeOfFirstVerification;
	}

	public void setTimeOfFirstVerification(Calendar timeOfFirstVerification) {
		this.timeOfFirstVerification = timeOfFirstVerification;
	}

	public String getCommentOfFirstVerification() {
		return commentOfFirstVerification;
	}

	public void setCommentOfFirstVerification(String commentOfFirstVerification) {
		this.commentOfFirstVerification = commentOfFirstVerification;
	}
	

}
