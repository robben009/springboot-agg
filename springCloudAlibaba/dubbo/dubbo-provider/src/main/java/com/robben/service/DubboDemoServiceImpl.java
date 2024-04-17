package com.robben.service;

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
    public String sayHello(StudentVo vo) {
        return "我看到了一个学生:" + JSON.toJSONString(vo);
    }

}
