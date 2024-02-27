package com.hjz.sharding.controller;

import com.hjz.sharding.database.entity.OrderEntity;
import com.hjz.sharding.database.mapper.OrderMapper;
import com.hjz.sharding.database.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Tag(name = "数据库操作")
@RequiredArgsConstructor
@RestController
@RequestMapping("/com/hjz/sharding")
public class ShardingController {
    private final OrderService orderService;

    @Operation(summary = "插入数据")
    @PostMapping(value = "/save")
    public String send(@RequestBody OrderEntity orderEntity) {
        orderService.save(orderEntity);
        return "1";
    }




}
