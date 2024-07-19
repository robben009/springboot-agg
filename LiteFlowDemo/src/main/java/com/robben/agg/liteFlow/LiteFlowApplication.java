package com.robben.agg.liteFlow;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class LiteFlowApplication {

    //注意启动时需要在命令行中增加参数 --add-opens java.base/sun.reflect.annotation=ALL-UNNAMED
    public static void main(String[] args) {
        SpringApplication.run(LiteFlowApplication.class, args);
    }

}
