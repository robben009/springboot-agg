package com.robben.agg.strategy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class CommandFactory {

    /**
     * Spring会自动将Strategy接口的实现类注入到这个Map中，key为bean id，value值则为对应的策略实现类
     */
    @Autowired
    private Map<String, MyCommand> commandMap;


    /**
     * 执行计算
     *
     * @param operateType
     * @param a
     * @param b
     * @return
     */
    public int calculate(String operateType, int a, int b) {
        MyCommand targetCommand = Optional.ofNullable(commandMap.get(operateType))
                .orElseThrow(() -> new IllegalArgumentException("Invalid Operator"));
        return targetCommand.execute(a, b);
    }


}