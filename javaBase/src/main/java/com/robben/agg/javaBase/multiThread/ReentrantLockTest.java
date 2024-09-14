package com.robben.agg.javaBase.multiThread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class ReentrantLockTest {
    /**
     * 详细解释ReentrantLock的使用，主要引用了下面两篇博客
     * https://blog.csdn.net/zengmingen/article/details/53260650  lockInterruptibly的使用
     * https://www.cnblogs.com/takumicx/p/9338983.html   ReentrantLock的使用
     */

    private Lock lock = new ReentrantLock();

    public void work() {
        String tName = Thread.currentThread().getName();
        try {
            log.info(tName + "-开始获取锁..........");
            lock.lockInterruptibly();

            log.info(tName + "-获取到锁了！！！！");
            log.info(tName + "-睡觉了，睡个25秒！");
            Thread.sleep(25000);

            log.info(tName + "-睡醒了，干活！");
            for (int i = 0; i < 5; i++) {
                log.info(tName + "：" + i);
            }
        } catch (Exception e) {
            log.info(tName+"-我好像被中断了！");
            e.printStackTrace();
        }finally{
            lock.unlock();
            log.info(tName + "-释放了锁");
        }
    }


    public static void main(String[] args) {
        ReentrantLockTest vo = new ReentrantLockTest();
        Thread t0 = new Thread(){
            @Override
            public void run() {
                vo.work();
            }
        };

        Thread t1=new Thread(){
            @Override
            public void run() {
                vo.work();
            }
        };

        String tName=Thread.currentThread().getName();
        try {
            log.info(tName+"-启动t0！");
            t0.start();
            log.info(tName+"-我等个5秒，再启动t1");
            Thread.sleep(5000);
            log.info(tName+"-启动t1");
            t1.start();

            log.info(tName+"-t1获取不到锁，t0这货睡觉了，没释放，我等个5秒！");
            Thread.sleep(5000);
            log.info(tName+"-等了5秒了，不等了，让t1不干活了！");
            t1.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
