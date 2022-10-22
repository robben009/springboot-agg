package com.robben.example.multiThread;

import com.alibaba.fastjson.JSON;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//https://blog.csdn.net/a837199685/article/details/52712547

public class InheritableThreadLocalTest {
    public static void main(String[] args) {
        ConcurrentHashMap a = new ConcurrentHashMap();
        final InheritableThreadLocal<Span> inheritableThreadLocalTest  = new InheritableThreadLocal<Span>();
        //输出 xiexiexie
        inheritableThreadLocalTest.set(new Span("xiexiexie"));
        System.out.println(JSON.toJSONString(inheritableThreadLocalTest.get()));

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("========");
                System.out.println(JSON.toJSONString(inheritableThreadLocalTest.get()));
                inheritableThreadLocalTest.set(new Span("zhangzhangzhang"));
                System.out.println(JSON.toJSONString(inheritableThreadLocalTest.get()));
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        try {
            executorService.submit(runnable);
            TimeUnit.SECONDS.sleep(1);
            executorService.submit(runnable);
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(JSON.toJSONString(inheritableThreadLocalTest.get()));
    }

    static class Span {
        public String name;
        public int age;
        public Span(String name) {
            this.name = name;
        }
    }
}




