package com.robben.agg.base.utils;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;
import lombok.Data;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLocalDemo {
    public static void main(String[] args) throws InterruptedException {
//        demo1();
        demo2();
//        demo3();
    }

    //inheritableThreadLocal只能在new Thread中的init方法中做操作，对于线程池中的线程无法继承和操作
    private static void demo1() {
        ThreadLocal<String> ThreadLocal = new ThreadLocal<>();
        ThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
        ThreadLocal.set("父线程数据:threadLocal");
        inheritableThreadLocal.set("父线程数据:inheritableThreadLocal");

        new Thread(() -> {
            System.out.println("子线程获取父类ThreadLocal数据：" + ThreadLocal.get());
            System.out.println("子线程获取父类inheritableThreadLocal数据：" + inheritableThreadLocal.get());
        }).start();
    }

    /**
     * TransmittableThreadLocal则解决了线程在线程池中的ThreadLocal的传递问题
     * 参考地址 https://github.com/alibaba/transmittable-thread-local
     */
    private static ExecutorService executorService = TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(1));


    private static void demo2() throws InterruptedException {
//        TransmittableThreadLocal<LocalBean> ttl = new TransmittableThreadLocal();

        //这种是深度拷贝,确保每次子线程修改不影响主线程，特别注意要是存放的是对象，一定要深度拷贝！！！！
        TransmittableThreadLocal<LocalBean> ttl = new TransmittableThreadLocal<LocalBean>() {
            @Override
            public LocalBean copy(LocalBean parentValue) {
                // 创建一个新的 LocalBean 对象（浅拷贝或深拷贝，根据业务）
                return new LocalBean(parentValue.getProp());
            }
        };

        LocalBean localBean = new LocalBean("初始值1");

        ttl.set(localBean); // 在主线程设置初始值
        new Thread(() -> {
            System.out.println("单一线程获取上下文值=" + ttl.get());
            localBean.setProp("单一线程值1");
        }).start();

        TimeUnit.SECONDS.sleep(1);
        System.out.println("主线程当前值=" + ttl.get());

        // 1、线程池中取值，设置对象内的属性值，测试是否影响外部线程
        executorService.execute(() -> {
            LocalBean localBean1 = ttl.get();

            System.out.println(String.format("线程池1获取值="+ localBean1.getProp()));
            localBean1.setProp("池1");
            System.out.println("线程池1获取值="+ ttl.get().getProp());
        });

        TimeUnit.SECONDS.sleep(1);
        System.out.println("主线程当前值=" + ttl.get());

        // 2、修改对象引用，测试是否影响外部线程
        executorService.execute(() -> {
            System.out.println(String.format("线程池2获取值="+ ttl.get().getProp()));
            ttl.set(new LocalBean("池2"));
        });

        TimeUnit.SECONDS.sleep(1);
        System.out.println("主线程当前值=" + ttl.get());
    }


    static TransmittableThreadLocal<String> transmittableThreadLocal = new TransmittableThreadLocal<>();

    // 共享的是不可变的 String
    private static void demo3() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService = TtlExecutors.getTtlExecutorService(executorService); // 用TtlExecutors装饰线程池

        transmittableThreadLocal.set("初始值1");
        executorService.execute(() -> {
            System.out.println(transmittableThreadLocal.get());
            transmittableThreadLocal.set("线程池更新1");// 子线程设置新的值

        });
        System.out.println(transmittableThreadLocal.get());

        TimeUnit.SECONDS.sleep(1);
        transmittableThreadLocal.set("初始值2");// 主线程设置新的值

        executorService.execute(() -> System.out.println(transmittableThreadLocal.get()));
    }


    @Data
    static class LocalBean {
        private String prop;

        LocalBean(String p) {
            this.prop = p;
        }
    }
}