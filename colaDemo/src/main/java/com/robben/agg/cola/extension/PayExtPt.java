package com.robben.agg.cola.extension;

import com.alibaba.cola.extension.ExtensionPointI;

public interface PayExtPt extends ExtensionPointI {
    String DefaultBizId = "defaultPay";

    String payOrder(String orderId);

}
