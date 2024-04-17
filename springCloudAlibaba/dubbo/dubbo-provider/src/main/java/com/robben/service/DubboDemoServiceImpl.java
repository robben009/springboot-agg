package com.robben.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson2.JSON;
import com.robben.DubboDemoService;
import com.robben.vo.StudentVo;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/3/6 16:03
 */
@Service
@DubboService(version = "1.0.0",timeout = 5000)
public class DubboDemoServiceImpl implements DubboDemoService {

    @Override
    @SentinelResource(value = "api2", blockHandler = "exceptionFallbackHandler", fallback = "exceptionFallbackHandler")
    public String sayHello(StudentVo vo) {
        return "我看到了一个学生:" + JSON.toJSONString(vo);
    }


    // Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
    public String exceptionBlockHandler(StudentVo vo, BlockException ex) {
        // Do some log here.
        ex.printStackTrace();
        return "exceptionHandler~~~~~~~~~";
    }
    // Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
    public String exceptionFallbackHandler(StudentVo vo) {
        return "helloFallback~~~~~~~~~~~~";
    }


}
