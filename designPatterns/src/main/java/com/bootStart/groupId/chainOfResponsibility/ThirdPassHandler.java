package com.bootStart.groupId.chainOfResponsibility;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ThirdPassHandler extends AbstractHandler{

    private int play(){
        return 95;
    }

    @Override
    public int handler(Order order){
        System.out.println("第三关-->ThirdPassHandler");

        if(order == null){
            order = new Order(1);
        }else{
            order.setId(3);
        }

        int score = play();
        if(score >= 95){
            //分数>=95 并且存在下一关才进入下一关
            if(this.next != null){
                return this.next.handler(order);
            }
        }

        System.out.println("最终获取order=" + order.getId());
        return score;
    }
}
