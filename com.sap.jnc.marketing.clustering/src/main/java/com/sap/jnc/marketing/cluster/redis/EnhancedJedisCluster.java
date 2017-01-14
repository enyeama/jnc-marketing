package com.sap.jnc.marketing.cluster.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisClusterCommand;
import redis.clients.jedis.Pipeline;

/**
 * @author Alex
 * Enhanced jedis cluster client supports expire parameter
 */
@SuppressWarnings("rawtypes")
public class EnhancedJedisCluster extends JedisCluster {

    public EnhancedJedisCluster(Set<HostAndPort> nodes) {
        super(nodes);
    }

    public String hget(final String key, final String field, final int expire) {
        return (String)(new JedisClusterCommand(this.connectionHandler, this.maxRedirections) {
            public String execute(Jedis connection) {
                Pipeline pipeline = connection.pipelined();
                pipeline.hget(key, field);
                pipeline.expire(key, expire);
                List result = pipeline.syncAndReturnAll();
                return (String)result.get(0);
            }
        }).run(key);
    }

    
	public Long hset(final String key, final String field, final String value, final int expire) {
        return (Long)(new JedisClusterCommand(this.connectionHandler, this.maxRedirections) {
            public Long execute(Jedis connection) {
                Pipeline pipeline = connection.pipelined();
                pipeline.hset(key, field, value);
                pipeline.expire(key, expire);
                List result = pipeline.syncAndReturnAll();
                return (Long)result.get(0);
            }
        }).run(key);
    }

    public String hmset(final String key, final Map<String, String> map, final int expire) {
        return (String)(new JedisClusterCommand(this.connectionHandler, this.maxRedirections) {
            public String execute(Jedis connection) {
                Pipeline pipeline = connection.pipelined();
                pipeline.hmset(key, map);
                pipeline.expire(key, expire);
                List result = pipeline.syncAndReturnAll();
                return (String)result.get(0);
            }
        }).run(key);
    }

    public Long hset(final byte[] key, final byte[] field, final byte[] value, final int expire) {
        return (Long)(new JedisClusterCommand(this.connectionHandler, this.maxRedirections) {
            public Long execute(Jedis connection) {
                Pipeline pipeline = connection.pipelined();
                pipeline.hset(key, field, value);
                pipeline.expire(key, expire);
                List result = pipeline.syncAndReturnAll();
                return (Long)result.get(0);
            }
        }).runBinary(key);
    }

    public byte[] hget(final byte[] key, final byte[] field, final int expire) {
        return (byte[])(new JedisClusterCommand(this.connectionHandler, this.maxRedirections) {
            public byte[] execute(Jedis connection) {
                Pipeline pipeline = connection.pipelined();
                pipeline.hget(key, field);
                pipeline.expire(key, expire);
                List result = pipeline.syncAndReturnAll();
                return (byte[])((byte[])result.get(0));
            }
        }).runBinary(key);
    }

    public String hmset(final byte[] key, final Map<byte[], byte[]> hash, final int expire) {
        return (String)(new JedisClusterCommand(this.connectionHandler, this.maxRedirections) {
            public String execute(Jedis connection) {
                Pipeline pipeline = connection.pipelined();
                pipeline.hmset(key, hash);
                pipeline.expire(key, expire);
                List result = pipeline.syncAndReturnAll();
                return (String)result.get(0);
            }
        }).runBinary(key);
    }

    @SuppressWarnings("unchecked")
	public List<byte[]> hmget(final byte[] key, final int expire, final byte[]... fields) {
        return (List)(new JedisClusterCommand(this.connectionHandler, this.maxRedirections) {
            public List<byte[]> execute(Jedis connection) {
                Pipeline pipeline = connection.pipelined();
                pipeline.hmget(key, fields);
                pipeline.expire(key, expire);
                List result = pipeline.syncAndReturnAll();
                return (List)result.get(0);
            }
        }).runBinary(key);
    }

}
