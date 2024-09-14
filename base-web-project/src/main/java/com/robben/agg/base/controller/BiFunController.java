package com.robben.agg.base.controller;

import com.robben.agg.base.contants.BbResultEnum;
import com.robben.agg.base.exception.BizPlusException;
import com.robben.agg.base.req.BenefitDetailReq;
import com.robben.agg.base.resp.BbResponse;
import com.robben.agg.base.service.CacheService;
import com.robben.agg.base.service.MpUseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@Slf4j
@Tag(name = "路由API")
@RestController
@RequestMapping("/routerApi")
@RequiredArgsConstructor
public class BiFunController {
    private final MpUseService mpUseService;
    private final Map<String, BiFunction<String, String, BbResponse<T>>> apiRouterHandleMap = new HashMap<>();

    //如果需要一个入参和一个返回的,可以使用Function
    @PostConstruct
    public void dispatcherInit() {
        apiRouterHandleMap.put("getUserInfo",
                (commonParam, requestParam) -> mpUseService.getUserInfo(commonParam));
        apiRouterHandleMap.put("getUserInfo2",
                (commonParam, requestParam) -> mpUseService.getUserInfo2(commonParam));
    }

    @Operation(summary = "路由api")
    @GetMapping(value = "/api")
    public BbResponse api(@RequestParam(value = "apiName") String apiName,@RequestParam(value = "param") String param) {
        if (!apiRouterHandleMap.containsKey(apiName)) {
            throw new BizPlusException(BbResultEnum.PARAMS_ERROR);
        }

        BbResponse<T> result = apiRouterHandleMap.get(apiName).apply(param, null);
        return result;
    }


}
