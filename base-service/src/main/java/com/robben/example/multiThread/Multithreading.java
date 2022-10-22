package com.robben.example.multiThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Multithreading {
    private static volatile Multithreading instance;

    private volatile ExecutorService executorService;
    private volatile ExecutorService executorService2;
    private volatile ExecutorService executorService3;
    private volatile ExecutorService executorService4;

    public Multithreading() {
        this.executorService = Executors.newFixedThreadPool(1);
        this.executorService2 = Executors.newSingleThreadExecutor();
        this.executorService3 = Executors.newCachedThreadPool();
        this.executorService4 = Executors.newScheduledThreadPool(2);


    }

    public static Multithreading getInstance(){
        if(instance == null){
            synchronized (Multithreading.class){
                if(instance == null){
                    instance = new Multithreading();
                }
            }
        }
        return instance;
    }

    public void execute(Runnable r){
        executorService.execute(r);
    }


}
