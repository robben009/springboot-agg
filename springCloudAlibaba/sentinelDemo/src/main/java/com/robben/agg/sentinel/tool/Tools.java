package com.robben.agg.sentinel.tool;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class Tools {

    public static String webHandle(String req, BlockException e) {
        return "web默认返回-接口被限流了";
    }

    public static String dubboHandle(String req, BlockException e) {
        return "dubbo默认返回-接口被限流了";
    }
}
