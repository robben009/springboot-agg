package com.robben.agg.dutychain.method1;

import com.robben.agg.commonDto.OrderVo;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ThirdPassHandler extends AbstractHandler {

    private int play(){
        return 95;
    }

    @Override
    public int handler(OrderVo orderVo){
        System.out.println("第三关-->com.robben.agg.dutychain.method1.ThirdPassHandler");

        if(orderVo == null){
            orderVo = new OrderVo(1);
        }else{
            orderVo.setId(3);
        }

        int score = play();
        if(score >= 95){
            //分数>=95 并且存在下一关才进入下一关
            if(this.selfHandle != null){
                return this.selfHandle.handler(orderVo);
            }
        }

        System.out.println("最终获取order=" + orderVo.getId());
        return score;
    }
}
