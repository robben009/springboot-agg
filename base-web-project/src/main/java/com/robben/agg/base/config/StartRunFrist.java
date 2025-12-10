package com.robben.agg.base.config;

import com.robben.agg.customstarter.CustomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 开机启动
 */

@Slf4j
@Component
@Order(value = 1)
public class StartRunFrist implements ApplicationRunner {
    @Value("${spring.profiles.active}")
    private String environment;
    @Value("${server.servlet.context-path}")
    private String projectName;
    @Autowired
    private CustomService customService;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void run(ApplicationArguments args) {
        //清除所有rabbitmq队列及其里面的消息
        log.info("~~~~~~~~~~~~~~~~~~~springBoot_startSuccess!!!~~~~~~~~~~~~~~~~~~~");
        log.info("~~~~~~~~~~~~~~~~~~~environment:{}~~~~~~~~~~~~~~~~~~~", environment);
        log.info("~~~~~~~~~~~~~~~~~~~projectName:{}~~~~~~~~~~~~~~~~~~~", projectName);
        log.info("customService,msg={}", customService.getMsg());

//        mockReq();
    }

    private void mockReq() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3000);
                    String url = "http://localhost:28081/base-web-project/base/use";

                    try {
                        // 2. 发送 GET 请求，直接获取响应字符串
                        String response = restTemplate.getForObject(url, String.class);
                        log.info("请求返回结果={}", response);
                    } catch (Exception e) {
                        log.error("请求失败", e);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.run();
    }

}
