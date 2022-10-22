package com.es.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/3/31 16:33
 */
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
@Data
public class ESConfigNoEncryption {

    private String host;

    private Integer port;

//    @Bean(destroyMethod = "close")
//    public RestHighLevelClient client(){
//        return new RestHighLevelClient(RestClient.builder(new HttpHost(host, port)));
//    }

}
