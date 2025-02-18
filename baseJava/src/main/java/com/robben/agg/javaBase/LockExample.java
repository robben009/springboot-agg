package com.robben.agg.javaBase;

import java.util.concurrent.locks.ReentrantLock;


public class LockExample {
    // ABC 三个线程抢夺一把锁。显示指明使用非公平锁
    private static final ReentrantLock lock = new ReentrantLock(false);
    // 获取锁后对 count 进行++ 操作
    private static volatile int count = 0;

    public static void main(String[] args) throws InterruptedException {
        // 线程 A
        Thread a = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                // 获取锁
                lock.lock();
                try {
                    count++;
                    System.out.println(Thread.currentThread().getName() + " incremented count to " + count);
                } finally {
                    // 释放锁
                    lock.unlock();
                }
            }
        }, "A");
        // 线程 B
        Thread b = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                // 抢占锁
                lock.lock();
                try {
                    count++;
                    System.out.println(Thread.currentThread().getName() + " incremented count to " + count);
                } finally {
                    lock.unlock();
                }
            }
        }, "B");
        // 线程 B
        Thread c = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                // 抢占锁
                lock.lock();
                try {
                    count++;
                    System.out.println(Thread.currentThread().getName() + " incremented count to " + count);
                } finally {
                    lock.unlock();
                }
            }
        }, "C");

        a.start();

        // 先让 B 线程晚一点执行
        System.out.println("---------");
        Thread.sleep(20000);
        b.start();

        // C 线程最后执行
        System.out.println("---------");
        Thread.sleep(20000);
        c.start();

        a.join();
        b.join();
        c.join();
    }
}
