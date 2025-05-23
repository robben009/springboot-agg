package com.robben.agg.cola.extension.impl;

import com.alibaba.cola.extension.Extension;
import com.robben.agg.cola.extension.PayExtPt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Extension(bizId = PayExtPt.DefaultBizId, useCase = "WX")
public class WxPay implements PayExtPt {

    @Override
    public String payOrder(String orderId) {
        return "微信支付了" + orderId;
    }

}
