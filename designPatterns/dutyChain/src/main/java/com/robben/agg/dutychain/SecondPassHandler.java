package com.robben.agg.dutychain;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SecondPassHandler extends AbstractHandler {

    private int play(){
        return 90;
    }

    @Override
    public int handler(Order order){
        System.out.println("第二关-->com.robben.agg.dutychain.SecondPassHandler");

        if(order == null){
            order = new Order(1);
        }else{
            order.setId(2);
        }

        int score = play();
        if(score >= 90){
            //分数>=90 并且存在下一关才进入下一关
            if(this.next != null){
                return this.next.handler(order);
            }
        }

        return score;
    }
}