package com.mq.producer;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;

import java.util.HashMap;
import java.util.Map;

@RocketMQTransactionListener(txProducerGroup = "myTransactionGroup")
public class TransactionListenerImpl implements RocketMQLocalTransactionListener {

    private static Map<String, RocketMQLocalTransactionState> STATE_MAP = new HashMap<>();

    /**
     *  执行业务逻辑
     *
     * @param message
     * @param o
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        String transId = (String)message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);

        try {
            System.out.println("执行操作");
            Thread.sleep(500);
            // 设置事务状态
            STATE_MAP.put(transId, RocketMQLocalTransactionState.COMMIT);

            //设置异常-执行业务逻辑
            int a = 100/0;

            // 返回事务状态给生产者
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            e.printStackTrace();
        }

        STATE_MAP.put(transId, RocketMQLocalTransactionState.ROLLBACK);
//        return RocketMQLocalTransactionState.ROLLBACK;
        //设置回查
        return RocketMQLocalTransactionState.UNKNOWN;
    }

    /**
     * 回查
     *
     * @param message
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        String transId = (String)message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
        System.out.println("回查消息 -> transId = " + transId + ", state = " + STATE_MAP.get(transId));
        return STATE_MAP.get(transId);
    }
}