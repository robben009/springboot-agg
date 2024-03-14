package com.robben.example.multiThread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CompletableFutureTest {
    @Autowired
    private ThreadPoolTaskExecutor myTaskExecutor;

    public void testCf() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        List<CompletableFuture<String>> completableFutures = list.stream().map(item -> CompletableFuture.supplyAsync(() -> {
            return item + "aaaa";
        }, myTaskExecutor)).collect(Collectors.toList());

        for (CompletableFuture<String> completableFuture : completableFutures) {
            String i;
            try {
                i = completableFuture.get(1, TimeUnit.SECONDS);
            } catch (Exception e) {
                continue;
            }
        }

        log.info("任务完成");
    }


    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3); // 创建一个固定大小的线程池

        List<CompletableFuture<String>> completableFutures = new ArrayList<>();
        completableFutures.add(CompletableFuture.supplyAsync(() -> longRunningTask("Task 1"), executor));
        completableFutures.add(CompletableFuture.supplyAsync(() -> longRunningTask("Task 2"), executor));
        completableFutures.add(CompletableFuture.supplyAsync(() -> longRunningTask("Task 3"), executor));

        List<String> result = new ArrayList<>();
        CompletableFuture<?> combinedFuture = CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]));

        try {
            log.info("开始了");
            combinedFuture.get(2, TimeUnit.SECONDS); // 等待所有任务完成，超时抛出异常
            log.info("结束了");
        } catch (TimeoutException e) {
            log.info("任务超时");
        } catch (Exception e) {
            log.info("任务执行出错");
        }

        for (CompletableFuture<String> completableFuture : completableFutures) {
            if (combinedFuture.isDone() && completableFuture.isDone()) {
                try {
                    result.add(completableFuture.get()); // 如果任务完成，获取结果并添加到结果列表
                } catch (Exception ex) {
                    log.info("任务 " + completableFuture + " 处理失败");
                }
            } else {
                log.info("任务 " + completableFuture + " 超时未完成");
            }
        }

        log.info("结果: " + result);
        executor.shutdown(); // 关闭线程池
    }

    private static String longRunningTask(String taskName) {
        try {
            Thread.sleep(100); // 模拟耗时任务
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Task " + taskName + " completed";
    }


}
