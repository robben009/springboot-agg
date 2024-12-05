package com.robben.agg.dutychain.method1;

import com.robben.agg.commonDto.OrderVo;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FirstPassHandler extends AbstractHandler {

    private int play(){
        return 80;
    }

    @Override
    public int handler(OrderVo orderVo){
        System.out.println("第一关-->com.robben.agg.dutychain.method1.FirstPassHandler");

        if(orderVo == null){
            orderVo = new OrderVo(1);
        }

        int score = play();
        if(score >= 80){
            //分数>=80 并且存在下一关才进入下一关
            if(this.selfHandle != null){
                return this.selfHandle.handler(orderVo);
            }
        }
        return score;
    }
}