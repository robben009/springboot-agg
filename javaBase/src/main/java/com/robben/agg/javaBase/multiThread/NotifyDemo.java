package com.robben.agg.javaBase.multiThread;

import java.util.ArrayList;
import java.util.List;

/**
 * notify() 方法的主要作用是 唤醒一个等待在该对象上的线程。这是多线程编程中非常重要的一部分，特别是在实现线程间的协作和条件同步时。
 *
 * 详细说明：
 * 对象监视器（Monitor）：
 *
 * 每个 Java 对象都有一个监视器（monitor），线程可以在其上等待或被唤醒。当一个线程调用 wait() 方法时，它会释放该对象的锁并等待其他线程调用 notify() 或 notifyAll() 方法来唤醒它。
 * notify() 方法：
 * 当一个线程调用 notify() 方法时，它会唤醒一个在该对象上等待的线程。需要注意的是，notify() 不会释放锁，唤醒的线程在继续执行之前需要重新获得对象的锁。
 *
 * wait() 方法：
 * wait() 方法使当前线程等待，直到其他线程调用 notify() 或 notifyAll() 方法，或者发生中断。调用 wait() 的线程会释放持有的对象锁。
 *
 * notifyAll() 方法：
 * notifyAll() 方法会唤醒所有在该对象上等待的线程，而不是只唤醒一个线程。
 */
public class NotifyDemo {
    private static final int MAX_SIZE = 10;
    private final List<Integer> buffer = new ArrayList<>();

    public synchronized void produce(int value) throws InterruptedException {
        while (buffer.size() == MAX_SIZE) {
            wait(); // 缓冲区满，生产者等待
        }
        buffer.add(value);
        System.out.println("Produced: " + value);
        notify(); // 通知消费者
    }

    public synchronized void consume() throws InterruptedException {
        while (buffer.isEmpty()) {
            wait(); // 缓冲区空，消费者等待
        }
        int value = buffer.remove(0);
        System.out.println("Consumed: " + value);
        notify(); // 通知生产者
    }

    public static void main(String[] args) {
        NotifyDemo example = new NotifyDemo();

        // 创建生产者线程
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 20; i++) {
                    example.produce(i);
                    Thread.sleep(100); // 模拟生产时间
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 创建消费者线程
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 20; i++) {
                    example.consume();
                    Thread.sleep(150); // 模拟消费时间
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
    }
}
