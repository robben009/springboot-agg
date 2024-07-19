package com.robben.agg.redisExtend.config;

import com.redislabs.modules.rejson.JReJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Field;

@Component
public class JedisCompent {
    @Autowired
    private JedisConnectionFactory connectionFactory;
    @Autowired
    private Jedis jedis;

    @Bean
    public Jedis jedis(){
        Field jedisField = ReflectionUtils.findField(JedisConnection.class, "jedis");
        ReflectionUtils.makeAccessible(jedisField);
        Jedis jedis = (Jedis) ReflectionUtils.getField(jedisField, connectionFactory.getConnection());
        return jedis;
    }

    @Bean
    public JReJSON jReJSON(){
        return new JReJSON(jedis);
    }



}
