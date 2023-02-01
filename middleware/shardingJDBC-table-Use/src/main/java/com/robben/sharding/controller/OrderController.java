package com.robben.sharding.controller;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/11/16 17:01
 */

import cn.hutool.core.date.SystemClock;
import com.robben.sharding.dao.OrderMapper;
import com.robben.sharding.entity.MyOrderEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "订单服务", value="订单服务")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderMapper orderMapper;


    @Operation("存储订单")
    @GetMapping("/add")
    public boolean add() {
        MyOrderEntity u = new MyOrderEntity();
        u.setSId(SystemClock.now());
        u.setName("haha");
        orderMapper.insert(u);
        return true;
    }


    @GetMapping("/get")
    public MyOrderEntity get(@RequestParam Long id) {
        return orderMapper.selectById(id);
    }


}
