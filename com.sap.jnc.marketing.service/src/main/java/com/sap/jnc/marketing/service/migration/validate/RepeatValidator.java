package com.sap.jnc.marketing.service.migration.validate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.jnc.marketing.service.config.migration.UploadConf;
import com.sap.jnc.marketing.service.config.migration.UploadRow;
import com.sap.jnc.marketing.service.migration.cache.BusinessKey;
import com.sap.jnc.marketing.service.migration.cache.BusinessKeyGenerator;
import com.sap.jnc.marketing.service.migration.cache.CacheManager;

@Component
public class RepeatValidator implements Validate {

	public <T extends UploadRow> List<T> validate(List<T> rows, Class<T> clazz) {
		Map<BusinessKey, List<T>> map = new HashMap<BusinessKey, List<T>>();
		
		CacheManager cacheManager = CacheManager.getInstance();
		UploadConf conf = cacheManager.getUploadConf(clazz);
		for (T row : rows) {
			BusinessKey businessKey = BusinessKeyGenerator.generateRowKey(row, conf.getFileAnnotation(), conf);
			if (!map.containsKey(businessKey)) {
				map.put(businessKey, new ArrayList<T>());
			}
			map.get(businessKey).add(row);
		}

		for (List<T> list : map.values()) {
			if (list.size() <= 1) {
				continue;
			}

			StringBuilder sb = new StringBuilder();
			for (T row : list) {
				sb.append(row.getRowIndex()).append(",");
			}
			String msg = sb.toString().substring(0, sb.length() - 1) + "行重复";
			for (T row : list) {
				row.addErrorMsg(msg);
			}
		}
		
		return null;
	}
}
