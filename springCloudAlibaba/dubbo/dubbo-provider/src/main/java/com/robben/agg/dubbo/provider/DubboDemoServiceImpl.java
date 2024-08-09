package com.robben.agg.dubbo.provider;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson2.JSONObject;
import com.robben.agg.dubbo.api.DubboDemoService;
import com.robben.agg.dubbo.api.StudentVo;
import com.robben.pp.GameVo;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/3/6 16:03
 */
@Service
@DubboService
public class DubboDemoServiceImpl implements DubboDemoService {

    @Override
//    @SentinelResource(value = "api2", blockHandler = "exceptionFallbackHandler", fallback = "exceptionFallbackHandler")
    public StudentVo sayHello(StudentVo vo) {
        vo.setAge(vo.getAge() + 1);
        vo.setName(vo.getName() + "~~~");

        GameVo gameVo = new GameVo();
        gameVo.setGameName(System.currentTimeMillis() + "~~~~~~");

        JSONObject j = new JSONObject();
        j.put("gameVo", gameVo);

        vo.setExtInfo(j);
        return vo;
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
