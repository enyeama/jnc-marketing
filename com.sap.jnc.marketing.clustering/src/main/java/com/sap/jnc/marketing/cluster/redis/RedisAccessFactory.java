package com.sap.jnc.marketing.cluster.redis;

import com.sap.jnc.marketing.cluster.config.SessionConfig;
import com.sap.jnc.marketing.cluster.redis.impl.RedisClusterAccess;
import com.sap.jnc.marketing.cluster.redis.impl.RedisSingleNodeAccess;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisAccessFactory {
    private static final Logger LOG = LoggerFactory
            .getLogger(RedisAccessFactory.class);
    private static RedisAccess redisUtilInterface = null;

    public synchronized static RedisAccess getRedisAccessInstance() {
        if (redisUtilInterface == null) {
            initJedis();
        }
        return redisUtilInterface;
    }

    private synchronized static void initJedis() {
        try {
            String clusters = SessionConfig.getValue("redis_cluster.host");
            if (StringUtils.isEmpty(clusters)) {
                LOG.info("no redis cluster config found，initializing single node redis");
                String jedisHost = SessionConfig.getValue("redis.pool.host");
                String host = jedisHost.split(":")[0];
                int port = Integer.valueOf(jedisHost.split(":")[1]);
                JedisPoolConfig config = new JedisPoolConfig();
                JedisPool pool = new JedisPool(config, host, port);
                redisUtilInterface = RedisSingleNodeAccess.getInstance(pool);
                return;
            }
            String[] clusterHostAndPorts = clusters.split(",");
            Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
            for (String hostAndPort : clusterHostAndPorts) {
                String[] hostConfig = hostAndPort.split(":");
                String host = hostConfig[0];
                String port = hostConfig[1];
                jedisClusterNode.add(new HostAndPort(host, Integer.valueOf(port)));
            }
            EnhancedJedisCluster jedisCluster = new EnhancedJedisCluster(jedisClusterNode);
            redisUtilInterface = RedisClusterAccess.getInstance(jedisCluster);
        } catch (Exception e) {
            LOG.error("initialize jedis failed...");
            throw new RuntimeException(e);
        }
    }
}
