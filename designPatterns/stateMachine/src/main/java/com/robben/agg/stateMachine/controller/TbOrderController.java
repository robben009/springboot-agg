package com.robben.agg.stateMachine.controller;

import com.robben.agg.stateMachine.dao.domain.TbOrder;
import com.robben.agg.stateMachine.dao.mapper.TbOrderMapper;
import com.robben.agg.stateMachine.service.TbOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@Tag(name = "测试")
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class TbOrderController {
    private final TbOrderService tbOrderService;
    private final TbOrderMapper tbOrderMapper;

    @Operation(summary = "根据id查询订单")
    @GetMapping("/getById")
    public TbOrder getById(@RequestParam("id") Long id) {
        TbOrder tbOrder = tbOrderMapper.selectById(id);
        return tbOrder;
    }

    @Operation(summary = "创建订单")
    @GetMapping("/create")
    public String create(@RequestBody TbOrder order) {
        tbOrderService.create(order);
        return "sucess";
    }

    @Operation(summary = "对订单进行支付")
    @GetMapping("/pay")
    public String pay(@RequestParam("id") Long id) {
        tbOrderService.pay(id);
        return "success";
    }

    @Operation(summary = "对订单进行发货")
    @GetMapping("/deliver")
    public String deliver(@RequestParam("id") Long id) {
        tbOrderService.deliver(id);
        return "success";
    }

    @Operation(summary = "对订单进行确认收货")
    @GetMapping("/receive")
    public String receive(@RequestParam("id") Long id) {
        tbOrderService.receive(id);
        return "success";
    }
}