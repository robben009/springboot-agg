package com.robben.agg.dutychain.web;


import com.robben.agg.commonDto.OrderVo;
import com.robben.agg.dutychain.method2_spring.OrderHandleChainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "测试")
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final OrderHandleChainService orderHandleChainService;

    @Operation(summary = "责任链")
    @GetMapping("chain")
    public String chain() {
        orderHandleChainService.execute(new OrderVo());
        return "1";
    }

}
