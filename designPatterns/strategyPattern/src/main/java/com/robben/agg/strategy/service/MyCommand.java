package com.robben.agg.strategy.service;

public interface MyCommand {

    /**
     * 命令类型
     *
     * @return
     */
    String operateType();

    /**
     * 执行
     *
     * @return
     * @parama
     * @paramb
     */
    Integer execute(int a, int b);

}
