package com.robben;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@SpringBootApplication
@ServletComponentScan
@NacosPropertySource(dataId = "base-service", autoRefreshed = true)
public class Application {

    public static void main(String[] args) {
        //下面语句使得日志输出使用异步处理，减小输出日志对性能的影响
        System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");

        SpringApplication.run(Application.class, args);
    }

}
