package com.sap.jnc.marketing.service.migration.validate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.jnc.marketing.service.config.migration.UploadConf;
import com.sap.jnc.marketing.service.config.migration.UploadRow;
import com.sap.jnc.marketing.service.migration.cache.BusinessKey;
import com.sap.jnc.marketing.service.migration.cache.BusinessKeyGenerator;
import com.sap.jnc.marketing.service.migration.cache.CacheManager;
import com.sap.jnc.marketing.service.util.JNCBeanUtils;

/**
 * 树型校验
 * 
 * @author I322359
 */
@Component
public class TreeValidator implements Validate {

	private static Logger logger = LoggerFactory.getLogger(TreeValidator.class);

	@SuppressWarnings("unchecked")
	public <T extends UploadRow> List<T> validate(List<T> rows, Class<T> clazz) {
		// 得到缓存对象
		CacheManager cacheManager = CacheManager.getInstance();
		UploadConf conf = cacheManager.getUploadConf(clazz);

		String[] parentKeys = conf.getFileAnnotation().parentKeys();
		String[] keys = conf.getFileAnnotation().keys();

		// 将缓存对象反转为Row
		Map<BusinessKey, T> treeCache = (Map<BusinessKey, T>) cacheManager.revertRows(conf);

		// 用于把上传的row保存下来, 校验使用
		Map<BusinessKey, T> rowMap = new HashMap<BusinessKey, T>();
		// 根据新的文件处理缓存, 如果文件中有增加、修改、删除
		boolean flag = false;
		for (T row : rows) {
			BusinessKey businessKey = BusinessKeyGenerator.generateRowKey(row, conf.getFileAnnotation(), conf);
			rowMap.put(businessKey, row);
			flag = treeCache.containsKey(businessKey);
			logger.debug("存在标志为：" + flag);
			if (row.getOperation().equalsIgnoreCase("C") && !flag) {
				// 新增缓存
				treeCache.put(businessKey, row);
			}
			else if (row.getOperation().equalsIgnoreCase("U") && flag) {
				// 修改缓存
				treeCache.put(businessKey, row);
			}
			else if (row.getOperation().equalsIgnoreCase("D") && flag) {
				// 删除缓存
				treeCache.remove(businessKey);
				logger.debug("删除对象：" + treeCache.get(businessKey));
			}
			else {
				
			}
		}

		// 将操作后的treeCache放入cache manager进行管理，用于在后期进行CUD操作
		cacheManager.addTreeCache(conf.getFileAnnotation().entity(), treeCache);
		
		// 校验记录的上下级关系是否存在, 并记录存在问题的记录
		for (Entry<BusinessKey, T> entry : treeCache.entrySet()) {
			boolean parentFlag = true;

			// 根据Row定义的UploadFile(keys, parentKeys)来组装当前Row的Parent BusinessKey
			BusinessKey parentBusinessKey = new BusinessKey();
			for (int i = 0; i < parentKeys.length; i++) {
				String parentKey = parentKeys[i];
				String key = keys[i];
				Object parentVal = JNCBeanUtils.getProperty(entry.getValue(), parentKey);
				if (parentVal != null) {
					parentFlag = false;
				}
				parentBusinessKey.addKeyValue(key, parentVal);
			}

			// 当为最上级时，不存在父级，不需要校验
			if(parentFlag){
				continue;
			}

			// 非最上级时，当前Row的BusinessKey
			BusinessKey businessKey = entry.getKey();

			if (!treeCache.containsKey(parentBusinessKey)) {
				if (rowMap.containsKey(businessKey)) {
					rowMap.get(businessKey).addErrorMsg("上下级关系异常");
				}
				if (rowMap.containsKey(parentBusinessKey)) {
					rowMap.get(parentBusinessKey).addErrorMsg("上下级关系异常");
				}
			}
		}

		return new ArrayList<T>(rowMap.values());
	}
}
