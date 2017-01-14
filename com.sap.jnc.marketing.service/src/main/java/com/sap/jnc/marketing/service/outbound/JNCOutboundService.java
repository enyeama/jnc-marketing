/*
 * This interface is used to handle the restful api call from JNC outbound logistic
 * @author James Jiang
 */
package com.sap.jnc.marketing.service.outbound;

import com.sap.jnc.marketing.dto.request.integration.JNCOuntboundInfo;

public interface JNCOutboundService {

	JNCOuntboundInfo dispatchOutboundInfo(JNCOuntboundInfo outboundInfo);

	JNCOuntboundInfo create(JNCOuntboundInfo outboundInfo);

	JNCOuntboundInfo update(JNCOuntboundInfo outboundInfo);

	JNCOuntboundInfo delete(JNCOuntboundInfo outboundInfo);
}
