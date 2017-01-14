package com.sap.jnc.marketing.infrastructure.shared.utils.serialization;

import org.nustaq.serialization.FSTConfiguration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.Serializable;

/**
 * @author Alex
 */
public class FSTObjectSerializer implements RedisSerializer<Object> {
	private final static FSTConfiguration configuration = FSTConfiguration.createDefaultConfiguration();

	@Override
	public byte[] serialize(Object object) throws SerializationException {
		return configuration.asByteArray((Serializable) object);
	}

	@Override
	public Object deserialize(byte[] buf) throws SerializationException {
		if (buf == null || buf.length == 0) {
			return null;
		}
		return configuration.asObject(buf);
	}
}
