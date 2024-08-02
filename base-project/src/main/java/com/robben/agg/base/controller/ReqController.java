package com.robben.agg.base.controller;

import com.jayway.jsonpath.Configuration;
import com.robben.agg.base.service.CacheService;
import com.robben.agg.base.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/reqTest")
public class ReqController {

    @Autowired
    private CacheService userService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private Configuration configuration;

    //

    /**
     * @param name
     * @param addresss
     * @return
     * @RequestParam 可以接受get请求, 请求方式是form-data(下面填写对应的key和value)、x-www-form-urlencoded请求只能在url中添加参数也能接受
     * @RequestBody 是接受post请求 可以转换为对象
     */
    @GetMapping(value = "/use")
    public String use(@RequestParam String name, @RequestParam String addresss) {
        log.info("name:{},addresss:{}", name, addresss);
        return "success";
    }

    @GetMapping(value = "/use2")
    public String use2(@RequestParam("name") String name, @RequestParam("addresss") String addresss) {
        log.info("name:{},addresss:{}", name, addresss);
        return "success";
    }


}
