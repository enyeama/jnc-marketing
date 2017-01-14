package com.sap.jnc.marketing.cluster.session;

import java.util.Set;

public interface RedisSessionInternal extends BaseRedisSession {

	void finishRequest();

	BaseSessionData getBaseSessionData();

	Set<String> getAttributeNameSet();

	void setAttributeQuiet(String paramString, Object paramObject);

	void invalidateQuiet();

}
