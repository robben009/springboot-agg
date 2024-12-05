package com.robben.agg.dutychain.method1;

import com.robben.agg.commonDto.OrderVo;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SecondPassHandler extends AbstractHandler {

    private int play(){
        return 90;
    }

    @Override
    public int handler(OrderVo orderVo){
        System.out.println("第二关-->com.robben.agg.dutychain.method1.SecondPassHandler");

        if(orderVo == null){
            orderVo = new OrderVo(1);
        }else{
            orderVo.setId(2);
        }

        int score = play();
        if(score >= 90){
            //分数>=90 并且存在下一关才进入下一关
            if(this.selfHandle != null){
                return this.selfHandle.handler(orderVo);
            }
        }

        return score;
    }
}