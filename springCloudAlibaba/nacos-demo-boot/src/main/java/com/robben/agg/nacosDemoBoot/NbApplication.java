package com.robben.agg.nacosDemoBoot;


import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@NacosPropertySource(dataId = "nacos-demo-boot.yml", groupId = "nacosDemo", autoRefreshed = true)
//@NacosPropertySource(dataId = "nacos-demo-boot-ext.yml", groupId = "nacosDemo", autoRefreshed = true)
public class NbApplication {

    public static void main(String[] args) {
        SpringApplication.run(NbApplication.class, args);
    }

}