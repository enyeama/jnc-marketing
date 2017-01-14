package com.sap.jnc.marketing.cluster.redis.impl;

import com.sap.jnc.marketing.cluster.redis.RedisAccess;
import com.sap.jnc.marketing.cluster.redis.SerializeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

@SuppressWarnings("deprecation")
public class RedisSingleNodeAccess implements RedisAccess {

    private JedisPool pool;


    private RedisSingleNodeAccess() {

    }

    private static RedisSingleNodeAccess redisUtil = null;


    public static synchronized RedisSingleNodeAccess getInstance(JedisPool pool) {
        if (redisUtil == null) {
            redisUtil = new RedisSingleNodeAccess();
        }
        redisUtil.setPool(pool);
        return redisUtil;
    }

    public static synchronized RedisSingleNodeAccess getInstance() {
        return redisUtil;
    }


    public String set(byte[] key, byte[] value) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.set(key, value);
        } finally {
            pool.returnResource(jedis);
        }
    }

    public byte[] get(byte[] key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.get(key);
        } finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public String get(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.get(key);
        } finally {
            pool.returnResource(jedis);
        }
    }

    public boolean del(String key) {
        Jedis jedis = pool.getResource();
        long result = -1;
        try {
            result = jedis.del(key);
        } finally {
            pool.returnResource(jedis);
        }
        if (result >= 0) {
            return true;
        }
        return false;
    }

    public boolean del(byte[] key) {
        Jedis jedis = pool.getResource();
        long result = -1;
        try {
            result = jedis.del(key);
        } finally {
            pool.returnResource(jedis);
        }
        if (result >= 0) {
            return true;
        }
        return false;
    }

    public Boolean exists(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.exists(key);
        } finally {
            pool.returnResource(jedis);
        }

    }

    public Boolean exists(byte[] key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.exists(key);
        } finally {
            pool.returnResource(jedis);
        }

    }

    public Boolean hexists(byte[] key, byte[] filed) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.hexists(key, filed);
        } finally {
            pool.returnResource(jedis);
        }

    }


    public Long hset(String key, String field, String value) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.hset(key, field, value);
        } finally {
            pool.returnResource(jedis);
        }
    }


    public Long hset(byte[] key, byte[] field, byte[] value) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.hset(key, field, value);
        } finally {
            pool.returnResource(jedis);
        }
    }

    public Long hset(String key, String field, String value, int expire) {
        Jedis jedis = pool.getResource();
        try {
            Transaction transaction = jedis.multi();
            transaction.hset(key, field, value);
            transaction.expire(key, expire);
            List<Object> result = transaction.exec();
            return (Long) result.get(0);
        } finally {
            pool.returnResource(jedis);
        }
    }


    public Long hset(byte[] key, byte[] field, byte[] value, int expire) {
        Jedis jedis = pool.getResource();
        try {
            Transaction transaction = jedis.multi();
            transaction.hset(key, field, value);
            transaction.expire(key, expire);
            List<Object> result = transaction.exec();
            return (Long) result.get(0);
        } finally {
            pool.returnResource(jedis);
        }
    }


    public Object hget(byte[] key, byte[] field) {
        Jedis jedis = pool.getResource();
        try {
            byte[] data = jedis.hget(key, field);
            return SerializeUtils.deserializeObject(data);
        } finally {
            pool.returnResource(jedis);
        }
    }


    public String hget(String key, String field) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.hget(key, field);
        } finally {
            pool.returnResource(jedis);
        }
    }


    public String hget(String key, String field, int expire) {
        Jedis jedis = pool.getResource();
        try {
            Transaction transaction = jedis.multi();
            transaction.hget(key, field);
            transaction.expire(key, expire);
            List<Object> result = transaction.exec();
            return (String) result.get(0);
        } finally {
            pool.returnResource(jedis);
        }
    }

    public Object hget(byte[] key, byte[] field, int expire) {
        Jedis jedis = pool.getResource();
        try {
            Transaction transaction = jedis.multi();
            transaction.hget(key, field);
            transaction.expire(key, expire);
            List<Object> result = transaction.exec();
            byte[] bytes = (byte[]) result.get(0);
            return SerializeUtils.deserializeObject(bytes);
        } finally {
            pool.returnResource(jedis);
        }
    }


    public String hmset(String key, Map<String, String> map) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.hmset(key, map);
        } finally {
            pool.returnResource(jedis);
        }
    }

    public String hmset(byte[] key, Map<byte[], byte[]> map) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.hmset(key, map);
        } finally {
            pool.returnResource(jedis);
        }
    }

    public String hmset(String key, Map<String, String> map, int expire) {
        Jedis jedis = pool.getResource();
        try {
            Transaction transaction = jedis.multi();
            transaction.hmset(key, map);
            transaction.expire(key, expire);
            List<Object> result = transaction.exec();
            return (String) result.get(0);
        } finally {
            pool.returnResource(jedis);
        }
    }


    public String hmset(byte[] key, Map<byte[], byte[]> map, int expire) {
        Jedis jedis = pool.getResource();
        try {
            Transaction transaction = jedis.multi();
            transaction.hmset(key, map);
            transaction.expire(key, expire);
            List<Object> result = transaction.exec();
            return (String) result.get(0);
        } finally {
            pool.returnResource(jedis);
        }
    }


    @SuppressWarnings("unchecked")
    public List<Object> hmget(byte[] key, int expire, byte[]... fields) {
        Jedis jedis = pool.getResource();
        try {
            Transaction transaction = jedis.multi();
            transaction.hmget(key, fields);
            transaction.expire(key, expire);
            List<Object> result = transaction.exec();
            List<byte[]> dataResult = (List<byte[]>) result.get(0);
            if (result == null || result.size() <= 0) {
                return null;
            }
            List<Object> list = new ArrayList<Object>();
            for (byte[] binaryData : dataResult) {
                list.add(SerializeUtils.deserializeObject(binaryData));
            }
            return list;

        } finally {
            pool.returnResource(jedis);
        }
    }


    public Long hdel(String key, String... field) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.hdel(key, field);
        } finally {
            pool.returnResource(jedis);
        }
    }

    public Long hdel(byte[] key, byte[]... field) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.hdel(key, field);
        } finally {
            pool.returnResource(jedis);
        }
    }

    /**
     * pool
     *
     * @return the pool
     * @since CodingExample Ver(编码范例查看) 1.0
     */

    public JedisPool getPool() {
        return pool;
    }

    /**
     * @param pool the pool to set
     */
    public void setPool(JedisPool pool) {
        this.pool = pool;
    }

    public Long setnx(final String key, final String value) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.setnx(key, value);
        } finally {
            pool.returnResource(jedis);
        }
    }

    public String getSet(final String key, final String value) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.getSet(key, value);
        } finally {
            pool.returnResource(jedis);
        }
    }
}
