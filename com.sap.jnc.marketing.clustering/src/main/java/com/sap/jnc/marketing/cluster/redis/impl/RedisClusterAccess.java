package com.sap.jnc.marketing.cluster.redis.impl;

import com.sap.jnc.marketing.cluster.redis.EnhancedJedisCluster;
import com.sap.jnc.marketing.cluster.redis.RedisAccess;
import com.sap.jnc.marketing.cluster.redis.SerializeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class RedisClusterAccess implements RedisAccess {


    private EnhancedJedisCluster cluster;

    private RedisClusterAccess() {

    }

    private static RedisClusterAccess redisUtil = null;


    public static synchronized RedisAccess getInstance(EnhancedJedisCluster cluster) {
        if (redisUtil == null) {
            redisUtil = new RedisClusterAccess();
        }
        redisUtil.setCluster(cluster);
        return redisUtil;
    }

    public String set(byte[] key, byte[] value) {
        return cluster.set(key, value);
    }

    public byte[] get(byte[] key) {
        return cluster.get(key);
    }

    @Override
    public String get(String key) {
        return cluster.get(key);
    }

    public boolean del(String key) {
        long result = cluster.del(key);
        if (result >= 0) {
            return true;
        }
        return false;
    }

    public boolean del(byte[] key) {
        long result = cluster.del(key);
        if (result >= 0) {
            return true;
        }
        return false;
    }

    public Boolean exists(String key) {
        return cluster.exists(key);

    }

    public Boolean exists(byte[] key) {
        return cluster.exists(key);

    }

    public Boolean hexists(byte[] key, byte[] filed) {
        return cluster.hexists(key, filed);

    }


    public Long hset(String key, String field, String value) {
        return cluster.hset(key, field, value);
    }


    public Long hset(byte[] key, byte[] field, byte[] value) {
        return cluster.hset(key, field, value);
    }


    public Long hset(String key, String field, String value, int expire) {
        return cluster.hset(key, field, value, expire);
    }

    public Long hset(byte[] key, byte[] field, byte[] value, int expire) {
        return cluster.hset(key, field, value, expire);
    }


    public Object hget(byte[] key, byte[] field) {
        byte[] value = cluster.hget(key, field);
        return SerializeUtils.deserializeObject(value);
    }


    public String hget(String key, String field) {
        return cluster.hget(key, field);
    }


    public String hget(String key, String field, int expire) {
        return cluster.hget(key, field, expire);
    }


    public Object hget(byte[] key, byte[] field, int expire) {
        byte[] value = cluster.hget(key, field, expire);
        return SerializeUtils.deserializeObject(value);
    }


    public String hmset(String key, Map<String, String> map) {
        return cluster.hmset(key, map);
    }


    public String hmset(byte[] key, Map<byte[], byte[]> map) {
        return cluster.hmset(key, map);
    }


    public String hmset(String key, Map<String, String> map, int expire) {
        return cluster.hmset(key, map, expire);
    }

    public String hmset(byte[] key, Map<byte[], byte[]> map, int expire) {
        return cluster.hmset(key, map, expire);
    }

    public List<Object> hmget(byte[] key, int expire, byte[]... fields) {
        List<byte[]> result = cluster.hmget(key, expire, fields);
        if (result == null || result.size() <= 0) {
            return null;
        }
        List<Object> list = new ArrayList<Object>();
        for (byte[] binaryData : result) {
            list.add(SerializeUtils.deserializeObject(binaryData));
        }
        return list;
    }


    /**
     * @param cluster the cluster to set
     */
    public void setCluster(EnhancedJedisCluster cluster) {
        this.cluster = cluster;
    }

    /* (non-Javadoc)
     * @see com.xuwei.util.interfaces.RedisUtilInterface#hdel(java.lang.String, java.lang.String[])
     */
    @Override
    public Long hdel(String key, String... field) {
        return cluster.hdel(key, field);
    }

    /* (non-Javadoc)
     * @see com.xuwei.util.interfaces.RedisUtilInterface#hdel(byte[], byte[][])
     */
    @Override
    public Long hdel(byte[] key, byte[]... field) {
        return cluster.hdel(key, field);
    }

    public Long setnx(final String key, final String value) {
        return cluster.setnx(key, value);
    }

    public String getSet(final String key, final String value) {
        return cluster.getSet(key, value);
    }
}
