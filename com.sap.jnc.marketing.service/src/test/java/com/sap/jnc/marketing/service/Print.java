package com.sap.jnc.marketing.service;

import java.util.Collection;

public interface Print {

	public void printObject(Object obj);
	
	public void printList(Collection<?> collection);
}
