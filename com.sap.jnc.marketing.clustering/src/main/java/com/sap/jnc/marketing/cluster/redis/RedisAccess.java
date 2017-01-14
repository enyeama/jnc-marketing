package com.sap.jnc.marketing.cluster.redis;

import java.util.List;
import java.util.Map;

public interface RedisAccess {

    String set(byte[] key, byte[] value);

    byte[] get(byte[] key);

    String get(String key);

    boolean del(String key);

    boolean del(byte[] key);

    Boolean exists(String key);

    Boolean exists(byte[] key);

    Boolean hexists(byte[] key, byte[] filed);

    Long hset(byte[] key, byte[] field, byte[] value);

    Long hset(byte[] key, byte[] field, byte[] value, int expire);

    Object hget(byte[] key, byte[] field);

    Object hget(byte[] key, byte[] field, int expire);

    String hmset(byte[] key, Map<byte[], byte[]> map);

    String hmset(byte[] key, Map<byte[], byte[]> map, int expire);

    List<Object> hmget(byte[] key, int expire, byte[]... fields);

    Long hdel(byte[] key, byte[]... field);

    Long hset(String key, String field, String value);

    Long hset(String key, String field, String value, int expire);

    String hget(String key, String field);

    String hget(String key, String field, int expire);

    String hmset(String key, Map<String, String> map);

    String hmset(String key, Map<String, String> map, int expire);

    Long hdel(String key, String... field);

    Long setnx(final String key, final String value);

    String getSet(final String key, final String value);
}
