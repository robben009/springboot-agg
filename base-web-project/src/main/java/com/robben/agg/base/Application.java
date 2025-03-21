package com.robben.agg.base;

import cn.hutool.core.net.NetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@Slf4j
@MapperScan(basePackages = {"com.robben.agg.base.dao.mapper"})
@SpringBootApplication(scanBasePackages = {"com.robben.agg"})
public class Application {

    public static void main(String[] args) {
        //下面语句使得日志输出使用异步处理，减小输出日志对性能的影响
//        System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
        SpringApplication.run(Application.class, args);
//        SpringApplication application = new SpringApplication(Application.class);
//        application.setDefaultProperties(getInitConfFile());
//        application.run(args);
    }


    //可以指定配置文件
    private static Properties getInitConfFile() {
        Properties properties = new Properties();

        String ip = NetUtil.getLocalhostStr();
        log.info("获取当前ip={}",ip);
        if(ip.startsWith("172.16")){
            properties.setProperty("spring.profiles.active", "test");
        }else{
            properties.setProperty("spring.profiles.active", "yun");
        }

        return properties;
    }

}
