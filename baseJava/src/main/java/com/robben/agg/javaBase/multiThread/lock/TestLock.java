package com.robben.agg.javaBase.multiThread.lock;

/**
 * Description： TODO
 * Author: robben
 * Date: 2020/12/9 19:52
 */
import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class TestLock {
    // @Test
    public void test() throws Exception {
        final Lock lock = new ReentrantLock();
        lock.lock();

        Thread t1 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
//                lock.lock();
                lock.lockInterruptibly();   //会中断响应
                lock.tryLock(1, TimeUnit.SECONDS);
                System.out.println(Thread.currentThread().getName() + " interrupted.");
            }
        },"child thread -1");

        t1.start();
        Thread.sleep(1000);

        t1.interrupt();

        Thread.sleep(2000);
        System.out.println(123);
    }

    public static void main(String[] args) throws Exception {
        new TestLock().test();
    }

}