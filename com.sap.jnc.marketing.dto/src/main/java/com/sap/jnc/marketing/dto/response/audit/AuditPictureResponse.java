package com.sap.jnc.marketing.dto.response.audit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.sap.jnc.marketing.persistence.model.Audit;
import com.sap.jnc.marketing.persistence.model.AuditPicture;

/**
 * @author C5231393 Xu Xiaolei
 */
public class AuditPictureResponse implements Serializable {

	private static final long serialVersionUID = -5768576257337237666L;

	private List<String> pictureUrls = new ArrayList<String>();

	public AuditPictureResponse(Audit audit) {
		for (AuditPicture auditPicture : audit.getPictures()) {
			if (auditPicture != null && !StringUtils.isEmpty(auditPicture.getUrl())) {
				this.pictureUrls.add(auditPicture.getUrl());
			}
		}
	}

	public List<String> getPictureUrls() {
		return pictureUrls;
	}

	public void setPictureUrls(List<String> pictureUrls) {
		this.pictureUrls = pictureUrls;
	}

}
