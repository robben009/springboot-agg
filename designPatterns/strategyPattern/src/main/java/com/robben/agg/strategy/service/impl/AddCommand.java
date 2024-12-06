package com.robben.agg.strategy.service.impl;

import com.robben.agg.strategy.service.MyCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AddCommand implements MyCommand {

    @Override
    public String operateType() {
        return "add";
    }

    @Override
    public Integer execute(int a, int b) {
        log.info("做加法了");
        return a + b;
    }

}