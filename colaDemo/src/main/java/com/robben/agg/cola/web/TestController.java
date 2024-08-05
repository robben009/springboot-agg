package com.robben.agg.cola.web;


import com.alibaba.cola.extension.BizScenario;
import com.alibaba.cola.extension.ExtensionExecutor;
import com.alibaba.cola.statemachine.StateMachine;
import com.robben.agg.cola.extension.PayExtPt;
import com.robben.agg.cola.stateMachine.Context;
import com.robben.agg.cola.stateMachine.Event;
import com.robben.agg.cola.stateMachine.State;
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
    private final StateMachine<State, Event, Context> stateMachine;

    @Operation(summary = "支付-扩展点测试")
    @GetMapping("pay")
    public String pay(@RequestParam(name = "payChannel") String payChannel, @RequestParam(name = "orderId") String orderId) {
        String result = extensionExecutor.execute(PayExtPt.class, BizScenario.valueOf(payChannel), ex -> ex.payOrder(orderId));
        return result;
    }


    @Operation(summary = "名字-状态机测试")
    @PostMapping("name")
    public State pay(@RequestBody Context c) {
        State state = stateMachine.fireEvent(State.STATE1, Event.EVENT1, c);
        return state;
    }

}
