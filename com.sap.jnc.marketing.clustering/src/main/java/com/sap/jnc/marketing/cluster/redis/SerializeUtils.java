
package com.sap.jnc.marketing.cluster.redis;


import org.nustaq.serialization.FSTConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;


public class SerializeUtils {
    @SuppressWarnings("unused")
	private final static Logger LOGGER = LoggerFactory.getLogger(SerializeUtils.class);
    private static FSTConfiguration configuration = FSTConfiguration.createDefaultConfiguration();

    public static byte[] serializeObject(Object object) {
        return configuration.asByteArray((Serializable) object);
    }

    public static Object deserializeObject(byte[] buf) {
        if (buf == null || buf.length == 0) {
            return null;
        }
        return configuration.asObject(buf);
    }

}
