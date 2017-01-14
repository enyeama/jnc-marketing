package com.sap.jnc.marketing.service.migration.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 业务主键
 * 
 * @author I322359
 */
public class BusinessKey implements Serializable {

	private static final long serialVersionUID = -5301627806446860599L;

	private Map<String, Object> map = new HashMap<String, Object>();

	public BusinessKey() {
	}

	public BusinessKey(Map<String, Object> map) {
		this.map = map;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public void addKeyValue(String key, Object value) {
		this.map.put(key, value);
	}

	/**
	 * 是否为空
	 * @return
	 */
	public boolean isEmpty() {
		for (Object val : map.values()) {
			if(val != null && StringUtils.isNoneBlank(val.toString())){
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BusinessKey)) {
			return false;
		}
		BusinessKey businessKey = (BusinessKey) obj;
		if (this.map.size() != businessKey.getMap().size()) {
			return false;
		}
		for (String prop : businessKey.getMap().keySet()) {
			Class<?> cls1 = this.getMap().get(prop).getClass();
			Class<?> cls2 = businessKey.getMap().get(prop).getClass();
			if (cls1 != cls2) {
				return false;
			}
			String str1 = MapUtils.getString(this.getMap(), prop);
			String str2 = MapUtils.getString(businessKey.getMap(), prop);
			if (!str1.equals(str2)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		String str = JSONObject.toJSONString(map);
		return str.hashCode();
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(map);
	}

	public static void main(String[] args) throws Exception {
		BusinessKey key1 = new BusinessKey();
		key1.addKeyValue("A", 1);
		key1.addKeyValue("B", 1);

		BusinessKey key2 = new BusinessKey();
		key2.addKeyValue("A", 1);
		key2.addKeyValue("B", 2);

		System.out.println(key1.hashCode() == key2.hashCode());
		System.out.println(key1.equals(key2));
	}
}
