package com.robben.agg.cola.web;


import com.alibaba.cola.extension.BizScenario;
import com.alibaba.cola.extension.ExtensionExecutor;
import com.alibaba.cola.statemachine.StateMachine;
import com.robben.agg.cola.extension.PayExtPt;
import com.robben.agg.cola.service.OrderStatusService;
import com.robben.agg.cola.stateMachine.OrderEventEnum;
import com.robben.agg.cola.stateMachine.OrderStatusEnum;
import com.robben.agg.cola.stateMachine.dto.OrderContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "测试")
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final ExtensionExecutor extensionExecutor;
    private final OrderStatusService orderStatusService;
    private final StateMachine<OrderStatusEnum, OrderEventEnum, OrderContext> stateMachine;


    @Operation(summary = "支付-扩展点")
    @GetMapping("pay")
    public String pay(@RequestParam(name = "payChannel") String payChannel, @RequestParam(name = "orderId") String orderId) {
        String result = extensionExecutor.execute(PayExtPt.class, BizScenario.valueOf(PayExtPt.DefaultBizId, payChannel), ex -> ex.payOrder(orderId));
        return result;
    }


    @Operation(summary = "名字-状态机")
    @PostMapping("name")
    public OrderStatusEnum pay(@RequestBody OrderContext c) {
        OrderStatusEnum orderStatusEnum = stateMachine.fireEvent(OrderStatusEnum.STATE1, OrderEventEnum.EVENT1, c);
        return orderStatusEnum;
    }

    @Operation(summary = "事件使用-状态机")
    @PostMapping("eventHandle")
    public OrderStatusEnum eventHandle() {
        OrderContext orderContext = new OrderContext();
        orderContext.setOrderId("orderId1");
        orderContext.setCallUserId("callUserId1");
        orderContext.setEventEnum(OrderEventEnum.EVENT1);
        orderContext.setNowStatus(OrderStatusEnum.STATE1.getCode());
        orderContext.setExtData(null);

        OrderStatusEnum orderStatusEnum = orderStatusService.modifyMyStatus(orderContext);
        return orderStatusEnum;
    }

}
