package com.robben.agg.strategy.service.impl;

import com.robben.agg.strategy.service.MyCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SubCommand implements MyCommand {

    @Override
    public String operateType() {
        return "subtract";
    }

    @Override
    public Integer execute(int a, int b) {
        log.info("做减法了");
        return a - b;
    }
}