package com.robben.agg.dutychain;

public class TestHandlerClient {

    public static void main(String[] args) {
        FirstPassHandler firstPassHandler = new FirstPassHandler();//第一关
        SecondPassHandler secondPassHandler = new SecondPassHandler();//第二关
        ThirdPassHandler thirdPassHandler = new ThirdPassHandler();//第三关

        // 和上面没有更改的客户端代码相比，只有这里的set方法发生变化，其他都是一样的
        firstPassHandler.setNext(secondPassHandler);//第一关的下一关是第二关
        secondPassHandler.setNext(thirdPassHandler);//第二关的下一关是第三关

        //说明：因为第三关是最后一关，因此没有下一关

        //从第一个关卡开始
        firstPassHandler.handler(null);
    }

}
