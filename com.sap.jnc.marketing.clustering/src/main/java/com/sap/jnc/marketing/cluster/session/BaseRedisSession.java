package com.sap.jnc.marketing.cluster.session;

import java.util.Set;

import javax.servlet.http.HttpSession;

public interface BaseRedisSession extends HttpSession {

	boolean isValid();

	void setTransientAttribute(String paramString, Object paramObject);

	void removeTransientAttribute(String paramString);

	Object getTransientAttribute(String paramString);

	Set<String> getTransientAttributeNames();
}
