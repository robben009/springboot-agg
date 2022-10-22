package com.robben.utils.guava;

import com.google.common.eventbus.Subscribe;

/**
 * @author hjz
 * @version 1.0
 * @date 2022/6/27 17:35
 */
//public class MqEvent {
//}
//public class StatusEvent {
//}
////第一个观察者，订阅了两种类型的发布
//public class EventHandler {
//
//    @Subscribe
//    public void mq(MqEvent mq) {
//        System.err.println(mq.getClass().getCanonicalName()+" work");
//    }
//
//    @Subscribe
//    public void status(StatusEvent statusEvent) {
//        System.err.println(statusEvent.getClass().getCanonicalName() +" work");
//    }
//}
//// 第二个观察者只订阅了一种类型的发布
//public class OtherHandler {
//
//    @Subscribe
//    public void mq(MqEvent mq) {
//        System.err.println( "OtherHandler work");
//    }
//}
//
//public class EventTest {
//
//    public static void main(String[] args) {
//        //初始化消息总线
//        EventBus eventBus = new EventBus();
//        // 注册订阅者
//        eventBus.register(new EventHandler());
//        eventBus.register(new OtherHandler());
//        //MqEvent推送给了两个订阅者
//        MqEvent mqEvent = new MqEvent();
//        StatusEvent statusEvent = new StatusEvent();
//        //发布消息
//        eventBus.post(mqEvent);
//        eventBus.post(statusEvent);
//    }
//}
