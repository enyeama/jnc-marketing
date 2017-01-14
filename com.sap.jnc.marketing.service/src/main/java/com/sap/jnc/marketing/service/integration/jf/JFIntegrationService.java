/*
 * This interface is used to handle the restful api call from JF production
 * @author      James Jiang
 */
package com.sap.jnc.marketing.service.integration.jf;

import com.sap.jnc.marketing.dto.request.integration.JFDeleteRequest;
import com.sap.jnc.marketing.dto.request.integration.JFMergeRequest;
import com.sap.jnc.marketing.dto.request.integration.JFUpdateRequest;

public interface JFIntegrationService {

	JFMergeRequest create(JFMergeRequest dto);

	JFUpdateRequest update(JFUpdateRequest dto);

	JFDeleteRequest delete(JFDeleteRequest dto);

}
