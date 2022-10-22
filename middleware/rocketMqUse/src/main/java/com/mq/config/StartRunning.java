package com.mq.config;

import com.mq.producer.BatchProducer;
import com.mq.producer.Producer1;
import com.mq.producer.TransactionProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class StartRunning implements CommandLineRunner {

    @Autowired
    private Producer1 producer1;
    @Autowired
    private BatchProducer batchProducer;
    @Autowired
    private TransactionProducer transactionProducer;

    @Override
    public void run(String... args) throws Exception {
//        producer1.send();

//        batchProducer.send();

        transactionProducer.send();
    }

}
