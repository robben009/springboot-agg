package com.robben.agg.nacosGateWay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * Description： TODO
 * Author: robben
 * Date: 2020/12/15 17:43
 */

@SpringBootApplication
@EnableDiscoveryClient
public class NGateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(NGateWayApplication.class, args);
    }

//    @Bean
//    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
//        return builder.routes().build();
//    }

}
