package com.robben.agg.base.service;

import com.robben.agg.base.common.Contants;
import com.robben.agg.base.utils.OtherUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ThreadPoolService {

    @Autowired
    private OtherUtils otherUtils;

    public String getTime() {
        return otherUtils.getTime();
    }

    @Async(Contants.MY_EXECUTOR)
    public void getThreadName() {
        System.out.println("asdfasdfasd1111111" + Thread.currentThread().getName());
    }

    @Async
    public void getThreadName2() {
        System.out.println("asdfasdfasd2222" + Thread.currentThread().getName());
    }
}
