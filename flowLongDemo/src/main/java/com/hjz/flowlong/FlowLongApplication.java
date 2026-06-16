package com.hjz.flowlong;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hjz.flowlong.dao.mapper")
public class FlowLongApplication {

    /**
     * 运行该模块注释根目录 build.gradle 文件 afterEvaluate 代码块
     */
    public static void main(String[] args) {
        SpringApplication.run(FlowLongApplication.class, args);
    }
}
