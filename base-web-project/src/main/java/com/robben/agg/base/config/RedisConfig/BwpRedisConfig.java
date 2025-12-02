package com.robben.agg.base.config.RedisConfig;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.support.spring6.data.redis.GenericFastJsonRedisSerializer;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Slf4j
@Configuration
@EnableCaching
public class BwpRedisConfig extends CachingConfigurerSupport {
    @Value("${spring.data.redis.host}")
    private String redisHost;
    @Value("${spring.data.redis.port}")
    private Integer redisPort;
    @Value("${spring.data.redis.password}")
    private String redisPwd;

    private LettuceConnectionFactory connectionFactory;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + redisHost + ":" + redisPort)
                .setPassword(redisPwd);
        return Redisson.create(config);
    }


    @PostConstruct
    public void initHanle() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisHost);
        configuration.setPort(redisPort);
        configuration.setDatabase(0);
        configuration.setPassword(redisPwd);

        /* ========= 连接池通用配置 ========= */
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();

        /* ========= lettuce pool ========= */
        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder();
        builder.poolConfig(genericObjectPoolConfig);
        connectionFactory = new LettuceConnectionFactory(configuration, builder.build());
        connectionFactory.afterPropertiesSet();
    }


    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(keySerializer());// key序列化
        redisTemplate.setValueSerializer(valueSerializer());// value序列化
        redisTemplate.setHashKeySerializer(keySerializer());// Hash key序列化
        redisTemplate.setHashValueSerializer(valueSerializer());// Hash value序列化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean(name = "stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(connectionFactory);
        // 明确配置序列化器（虽然 StringRedisTemplate 默认已经配置好了）
        template.setKeySerializer(keySerializer());
        template.setValueSerializer(RedisSerializer.string());
        template.setHashKeySerializer(keySerializer());
        template.setHashValueSerializer(RedisSerializer.string());
        template.afterPropertiesSet();
        return template;
    }


    @Bean
    @Primary
    public RedisCacheManager cacheManager() {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                //设置默认过期时间为1h,Duration.ZERO为永久
                .entryTtl(Duration.ZERO)
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()));
        //如果需要设置缓存不能为空,则可以还加上disableCachingNullValues
        //.disableCachingNullValues();

        return new MyRedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory), config);
    }

    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    private RedisSerializer<Object> valueSerializer() {
        return new GenericFastJsonRedisSerializer();
    }

    @Bean
    @Primary
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(".");
            sb.append(method.getName());

            if (params.length > 0) {
                sb.append(":");
                for (Object obj : params) {
                    sb.append(JSON.toJSONString(obj));
                }
            }

            return sb.toString();
        };
    }

    class MyRedisCacheManager extends RedisCacheManager {

        public MyRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
            super(cacheWriter, defaultCacheConfiguration);
        }

        @Override
        protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
            if (StringUtils.isEmpty(name)) {
                name = "defaultCacheName";
            }
            String[] splitArr = name.split("#");
            name = splitArr[0];
            // 解析key的ttl
            if (splitArr.length > 1) {
                String timeunit = splitArr[1];
                long ttl = Long.parseLong(splitArr[2]);

                // 根据 timeunit 设置 ttl
                switch (timeunit) {
                    case "s":
                        cacheConfig = cacheConfig.entryTtl(Duration.ofSeconds(ttl));
                        break;
                    case "m":
                        cacheConfig = cacheConfig.entryTtl(Duration.ofMinutes(ttl));
                        break;
                    case "h":
                        cacheConfig = cacheConfig.entryTtl(Duration.ofHours(ttl));
                        break;
                    case "d":
                        cacheConfig = cacheConfig.entryTtl(Duration.ofDays(ttl));
                        break;
                    default:
                        // 没有单位认为没有配置，不进行过期时间处理,使用RedisCacheConfiguration的配置
                        break;
                }
            }
            return super.createRedisCache(name, cacheConfig);
        }
    }

}