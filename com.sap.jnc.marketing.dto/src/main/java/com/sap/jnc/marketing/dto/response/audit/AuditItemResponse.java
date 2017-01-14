package com.sap.jnc.marketing.dto.response.audit;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.sap.jnc.marketing.persistence.model.Audit;
import com.sap.jnc.marketing.persistence.model.AuditItem;

/**
 * @author C5231393 Xu Xiaolei
 */
public class AuditItemResponse implements Serializable {

	private static final long serialVersionUID = 3494841971646156110L;

	private Map<String, String> fieldsItems = new HashMap<String, String>();

	public AuditItemResponse(Audit audit) {
		for (AuditItem auditItem : audit.getItems()) {
			if (!StringUtils.isEmpty(auditItem.getFieldName()) && !StringUtils.isEmpty(auditItem.getFieldValue())) {
				this.fieldsItems.put(auditItem.getFieldName(), auditItem.getFieldValue());
			}
		}
	}

	public Map<String, String> getFieldsItems() {
		return fieldsItems;
	}

	public void setFieldsItems(Map<String, String> fieldsItems) {
		this.fieldsItems = fieldsItems;
	}

}
