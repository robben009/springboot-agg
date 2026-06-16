package com.hjz.flowlong.config;

import com.aizuda.bpm.engine.FlowLongEngine;
import com.aizuda.bpm.engine.core.FlowCreator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class FlowDeployRunner implements CommandLineRunner {

    private final FlowLongEngine flowLongEngine;

    @Override
    public void run(String... args) {
        flowLongEngine.processService().deployByResource("process.json",
                FlowCreator.of("system", "系统"), false);
        log.info("流程部署完成: process.json");
    }
}