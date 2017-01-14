package com.sap.jnc.marketing.service;

import java.util.Collection;

import com.alibaba.fastjson.JSONObject;

public abstract class CommonPrint implements Print {

	@Override
	public void printObject(Object obj) {
		System.out.println("-------------------------------------------------------------");
		System.out.println(JSONObject.toJSONString(obj));
		System.out.println("-------------------------------------------------------------");
	}

	@Override
	public void printList(Collection<?> collection) {
		System.out.println("-------------------------------------------------------------");
		for (Object obj : collection.toArray()) {
			System.out.println(JSONObject.toJSONString(obj));
		}
		System.out.println("-------------------------------------------------------------");
	}
}
