package com.sap.jnc.marketing.cluster.filter;

import com.sap.jnc.marketing.cluster.session.manager.RedisSessionManager;

import javax.servlet.FilterConfig;
import javax.servlet.annotation.WebFilter;

/**
 * @author Alex
 */
@WebFilter(filterName = "DistributedSession", urlPatterns = { "/*" })
public class DistributedSessionFilter extends BaseSessionFilter {
    @Override
    protected void setSessionListener(FilterConfig filterConfig, RedisSessionManager sessionManager) {
        LOGGER.info("session event is not supported yet");
    }
}
