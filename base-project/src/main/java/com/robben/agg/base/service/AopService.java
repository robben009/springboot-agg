package com.robben.agg.base.service;

import cn.hutool.core.date.SystemClock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/5/31 15:37
 */
@Slf4j
@Service
public class AopService {

    public String getValue(String name) {
        log.info("aaa");
        return name + "" + SystemClock.now();
    }


}
