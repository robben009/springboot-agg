package com.robben;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

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
