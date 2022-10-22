package com.robben.service;

import com.alibaba.fastjson.JSON;
import com.robben.DemoService;
import com.robben.vo.StudentVo;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/3/6 16:03
 */
@Service
@DubboService
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(StudentVo vo) {
        return "我看到了一个学生:" + JSON.toJSONString(vo);
    }

}
