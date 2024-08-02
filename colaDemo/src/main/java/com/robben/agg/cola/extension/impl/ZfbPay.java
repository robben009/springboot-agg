package com.robben.agg.cola.extension.impl;

import com.alibaba.cola.extension.Extension;
import com.robben.agg.cola.extension.PayExtPt;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Extension(bizId = "RMARK")
public class ZfbPay implements PayExtPt {

    @Override
    public String payOrder(String orderId) {
        return "支付宝支付了" + orderId;
    }
}
