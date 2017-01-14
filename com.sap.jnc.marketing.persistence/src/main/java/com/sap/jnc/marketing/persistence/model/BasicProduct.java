package com.sap.jnc.marketing.persistence.model;

public interface BasicProduct {

	String getBoxId();

	String getCaseId();

	Product getProduct();

	Banquet getBanquet();

	BanquetItem getBanquetItem();

	void setBanquetItem(BanquetItem banquetItem);

}