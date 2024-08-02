package com.robben.agg.cola.extension;

import com.alibaba.cola.extension.ExtensionPointI;

public interface PayExtPt extends ExtensionPointI {

    String payOrder(String orderId);

}
