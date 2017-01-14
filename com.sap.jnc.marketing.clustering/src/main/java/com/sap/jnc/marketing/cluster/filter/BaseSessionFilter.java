package com.sap.jnc.marketing.cluster.filter;

import com.sap.jnc.marketing.cluster.request.RedisServletRequest;
import com.sap.jnc.marketing.cluster.session.manager.DefaultSessionManager;
import com.sap.jnc.marketing.cluster.session.manager.RedisSessionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseSessionFilter implements Filter {
    protected final Logger LOGGER = LoggerFactory
            .getLogger(this.getClass());

    /**
     *
     */
    private RedisSessionManager sessionManager;

    /* (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
        if (applicationContext == null) {
            LOGGER.error("web application");
        }
        sessionManager = new DefaultSessionManager(applicationContext);
        setSessionListener(filterConfig, sessionManager);
        sessionManager.init();
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        if (isDistributedSessionApplied(request)) {
            LOGGER.debug("RedisServletRequest has already been applied.");
            chain.doFilter(request, response);
        } else {
            RedisServletRequest redisServletRequest = new RedisServletRequest((HttpServletRequest) request, sessionManager, (HttpServletResponse) response);
            chain.doFilter(redisServletRequest, response);
        }
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {

    }

    protected abstract void setSessionListener(FilterConfig filterConfig, RedisSessionManager sessionManager);

    protected static boolean isDistributedSessionApplied(ServletRequest req) {
        return req instanceof RedisServletRequest;
    }
}
