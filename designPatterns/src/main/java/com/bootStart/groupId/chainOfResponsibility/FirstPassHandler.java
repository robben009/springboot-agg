package com.bootStart.groupId.chainOfResponsibility;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FirstPassHandler extends AbstractHandler{

    private int play(){
        return 80;
    }

    @Override
    public int handler(Order order){
        System.out.println("第一关-->FirstPassHandler");

        if(order == null){
            order = new Order(1);
        }

        int score = play();
        if(score >= 80){
            //分数>=80 并且存在下一关才进入下一关
            if(this.next != null){
                return this.next.handler(order);
            }
        }
        return score;
    }
}