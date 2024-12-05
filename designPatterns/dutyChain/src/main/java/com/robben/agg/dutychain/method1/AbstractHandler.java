package com.robben.agg.dutychain.method1;

import com.robben.agg.commonDto.OrderVo;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public abstract class AbstractHandler {
    /**
     * 下一关用当前抽象类来接收
     */
    protected AbstractHandler selfHandle;


    public abstract int handler(OrderVo orderVo);



}
