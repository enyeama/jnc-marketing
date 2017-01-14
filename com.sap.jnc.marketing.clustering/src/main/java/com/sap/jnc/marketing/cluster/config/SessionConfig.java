package com.sap.jnc.marketing.cluster.config;


import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.jnc.marketing.cluster.exception.IllegalConfigException;

public class SessionConfig {
	private final static Logger logger = LoggerFactory.getLogger(SessionConfig.class);
	private final static Properties props = new Properties();
	private static final String SESSION_CONFIG_URI = "META-INF/cluster-session";

	static {
		try {
			final URL url = SessionConfig.class.getClassLoader().getResource(SESSION_CONFIG_URI + ".properties");
			logger.debug("session config location---->{}", url);
			if (url == null) {
				props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(SESSION_CONFIG_URI + "-default.properties"));
			} else {
				props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(SESSION_CONFIG_URI + ".properties"));
			}
		} catch (final Exception e) {
			logger.error("error occurred while loading session configuration", e);
			throw new IllegalConfigException(e);
		}
	}

	public static String getValue(String key) {
		return props.getProperty(key);
	}

	public static void updateProperties(String key, String value) {
		props.setProperty(key, value);
	}

}
