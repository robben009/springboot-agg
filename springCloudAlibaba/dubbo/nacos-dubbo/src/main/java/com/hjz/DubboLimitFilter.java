package com.hjz;//package com.banma.power.station.core.start.config.sentinel;
//
//import com.alibaba.csp.sentinel.adapter.dubbo.origin.DubboOriginParser;
//import com.alibaba.dubbo.rpc.Invocation;
//import com.alibaba.dubbo.rpc.Invoker;
//
//
//public class DubboLimitFilter implements DubboOriginParser {
//    @Override
//    public String parse(Invoker<?> invoker, Invocation invocation) {
//        // 获取调用方应用名称
//        String attachment = invocation.getAttachment("remote.application");
//        return "app";
//    }
//}
