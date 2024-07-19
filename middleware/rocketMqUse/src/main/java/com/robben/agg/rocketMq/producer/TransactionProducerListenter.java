package com.robben.agg.rocketMq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 生产者事务监听器
 */
@Slf4j
@Component
@RocketMQTransactionListener
public class TransactionProducerListenter implements RocketMQLocalTransactionListener {

    /**
     * 生产者数据库事务逻辑写在这里
     *
     * 它允许你在发送消息之前先执行一些本地事务操作，并且只有当这些本地事务操作成功完成之后，
     * 消息才会被真正发送出去。如果本地事务操作失败，消息将不会被发送。
     * checkLocalTransaction 方法就是用来检查本地事务状态的。
     * 当 Broker 收到一条事务消息时，它会调用生产者的 checkLocalTransaction 方法来询问本地事务的状态。
    */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object arg) {
        MessageHeaders headers = message.getHeaders();
        // 本地事务id
        String transactionId = (String) headers.get(RocketMQHeaders.PREFIX + RocketMQHeaders.TRANSACTION_ID);
        String age = (String) headers.get("age");
        log.info("本地事务开始 transactionId = {} [age = {}]", transactionId, age);

        if (!StringUtils.hasLength(age)) {
            // 直接回滚
            return RocketMQLocalTransactionState.ROLLBACK;
        }

        // ===== 本地事务开始 =====
        // 执行保存 orderService.saveOrder(order) 执行本地事务
        // ===== 本地事务结束 =====

        // 模拟本地事务执行成功(偶数)和失败(奇数)
        if (Integer.valueOf(age) % 2 == 0) {
            return RocketMQLocalTransactionState.COMMIT;
        } else {
            // 假设这里的失败是本地事务还在执行(还不确定提交还是回滚)
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }


    /**
     * 检查事务状态（监听回查请求）
     * 1.当成产者执行本地事务发生故障或者是返回 UNKNOWN 状态,要保证这条消息最终被消费,RocketMQ会像服务端发送回查请求,确认本地事务的执行状态
     * 2.不会无休止的的信息事务状态回查，默认回查15次，如果15次回查还是无法得知事务状态，RocketMQ默认回滚该消息
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        MessageHeaders headers = message.getHeaders();
        String transactionId = (String) headers.get(RocketMQHeaders.PREFIX + RocketMQHeaders.TRANSACTION_ID);
        String age = (String) headers.get("age");
        log.info("检查本地事务开始 transactionId = {} [age = {}]", transactionId, age);

        if (!StringUtils.hasLength(age)) {
            // 直接回滚,不在发送消息
            return RocketMQLocalTransactionState.ROLLBACK;
        }

        // 查询 orderService.getOrderById(id)
        // 如果能查到则说明本地事务执行成功 返回rocketMQLocalTransactionState.COMMIT
        // 反之则说明本地事务还在执行或者是出现故障

        //这里返回不知道,让其继续等待查询
        return RocketMQLocalTransactionState.UNKNOWN;
    }

}
