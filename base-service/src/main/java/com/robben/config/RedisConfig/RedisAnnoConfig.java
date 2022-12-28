package com.robben.config.RedisConfig;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import java.time.Duration;

/**
 * 配置redis注解以fastJson的形式序列化存入,有两个方法,
 * 方法一实体类如;
 *  {
 *  "@type": "com.robben.model.UserVo",
 *   "id": 1,
 *   "name": "aaa",
 *   "pas": "12345678"
 * }
 */

@Slf4j
@Configuration
@EnableCaching
public class RedisAnnoConfig {

    /**
     * 如果不配置spring.redis.*的话,可以直接覆写redistemplate,创建新的连接工厂
     * @param rcf
     * @return
     */
//    @PostConstruct
//    public void initHanle(){
//        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
//        configuration.setHostName(hostName);
//        configuration.setPort(port);
//        configuration.setDatabase(database);
//        configuration.setPassword("aaaaa");
//
//        /* ========= 连接池通用配置 ========= */
//        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
//        genericObjectPoolConfig.setMaxTotal(maxActive);
//        genericObjectPoolConfig.setMinIdle(minIdle);
//        genericObjectPoolConfig.setMaxIdle(maxIdle);
//
//        /* ========= lettuce pool ========= */
//        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder =
//                LettucePoolingClientConfiguration.builder();
//        builder.poolConfig(genericObjectPoolConfig);
//        connectionFactory = new LettuceConnectionFactory(configuration, builder.build());
//        connectionFactory.afterPropertiesSet();
//    }

    @Bean
    @Primary
    public RedisCacheManager cacheManager(RedisConnectionFactory rcf){
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ZERO)  //默认没有过期时间2
                .entryTtl(Duration.ofMinutes(10))  //设置默认过期时间为10min
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()));
//                .disableCachingNullValues();  //注释掉可以存储null值

//        RedisCacheManager redisCacheManager = RedisCacheManager.builder(rcf)
//                .cacheDefaults(config)
//                .transactionAware()
//                .build();
//
//        log.info("自定义RedisCacheManager加载完成");
//        return redisCacheManager;

        return new MyRedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(rcf), config);

    }

    class MyRedisCacheManager extends RedisCacheManager {
        public MyRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
            super(cacheWriter, defaultCacheConfiguration);
        }

        @Override
        protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
            String[] splitArr = StringUtils.delimitedListToStringArray(name, "#");
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
                        // 没有单位认为没有配置，不进行过期时间处理
                        break;
                }
            }
            return super.createRedisCache(name, cacheConfig);
        }
    }



    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory rcf){
        RedisTemplate<String,Object> r = new RedisTemplate<>();
        r.setConnectionFactory(rcf);
        r.setKeySerializer(keySerializer());
        r.setValueSerializer(valueSerializer());
        r.setHashKeySerializer(keySerializer());
        r.setHashValueSerializer(valueSerializer());

        log.info("自定义RedisTemplate加载完成");
        return r;
    }

    private RedisSerializer<String> keySerializer(){
        return new StringRedisSerializer();
    }

    private RedisSerializer<Object> valueSerializer(){
        return new GenericJackson2JsonRedisSerializer();  //也可以选择GenericJackson2JsonRedisSerializer
    }

    @Bean
    public KeyGenerator cacheKeyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getSimpleName());
            sb.append(".");
            sb.append(method.getName());
            sb.append(":");
            for (Object obj : params) {
                // 由于参数可能不同, hashCode肯定不一样, 缓存的key也需要不一样
                sb.append(JSON.toJSONString(obj).hashCode());
            }
            return sb.toString();
        };
    }



//    @Bean
//    public KeyGenerator keyGenerator() {
//        return (target, method, params) -> {
//            StringBuilder sb = new StringBuilder();
//            Cacheable annotation = method.getDeclaringClass().getAnnotation(Cacheable.class);
//            if (annotation != null) {
//                String[] value = annotation.value();
//                char[] cs = value[0].toCharArray();
//                cs[0] -= 32;
//                sb.append(String.valueOf(cs) + ":");
//            }
//            String str = method.getName();
//            String regx = "[A-Z][a-z]+";
//            List<String> strList = new ArrayList<>();
//            Pattern pattern = Pattern.compile(regx);
//            Matcher matcher = pattern.matcher(str);
//            while (matcher.find()) {
//                strList.add(matcher.group());
//            }
//            int cLength = strList.size() - params.length;
//            if (str.contains("set")) {
//                for (int i = 0; i < strList.size(); i++) {
//                    sb.append(strList.get(i)).append(":");
//                    if (i >= cLength + 1) {
//                        sb.append(params[i - cLength - 1]).append(":");
//                    }
//                }
//            } else {
//                for (int i = 0; i < strList.size(); i++) {
//                    sb.append(strList.get(i)).append(":");
//                    if (i >= cLength) {
//                        sb.append(params[i - cLength]).append(":");
//                    }
//                }
//            }
//            sb.deleteCharAt(sb.length() - 1);
//            return sb.toString();
//        };
//    }

}





