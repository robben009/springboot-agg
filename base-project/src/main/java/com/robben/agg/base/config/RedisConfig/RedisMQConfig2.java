package com.robben.agg.base.config.RedisConfig;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisMQConfig2 {

//    //redis的订阅发布用接口实现(通用接口)
//    @Bean("container2")
//    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,ApplicationContext a) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//
//        MessageListenerAdapter messageListenerAdapter;
//        RedisMQConfig2Handler redisMQConfig2Handler;
//
//        Map<String, RedisMQConfig2Handler> maps = a.getBeansOfType(RedisMQConfig2Handler.class);
//        for (Map.Entry<String, RedisMQConfig2Handler> bean : maps.entrySet()) {
//            redisMQConfig2Handler = bean.getValue();
//            messageListenerAdapter = new MessageListenerAdapter(redisMQConfig2Handler);
//            messageListenerAdapter.afterPropertiesSet();
//            container.addMessageListener(messageListenerAdapter,new PatternTopic(redisMQConfig2Handler.redisChannel()));
//        }
//
//        return container;
//    }


}
