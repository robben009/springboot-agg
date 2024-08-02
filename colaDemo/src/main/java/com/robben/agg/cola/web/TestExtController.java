package com.robben.agg.cola.web;


import com.alibaba.cola.extension.BizScenario;
import com.alibaba.cola.extension.ExtensionExecutor;
import com.robben.agg.cola.extension.PayExtPt;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "扩展点")
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestExtController {
    private final ExtensionExecutor extensionExecutor;

    @Operation(summary = "支付")
    @GetMapping("pay")
    public String pay(@RequestParam(name = "payChannel") String payChannel, @RequestParam(name = "orderId") String orderId) {
        String result = extensionExecutor.execute(PayExtPt.class, BizScenario.valueOf(payChannel), ex -> ex.payOrder(orderId));
        return result;
    }

}
