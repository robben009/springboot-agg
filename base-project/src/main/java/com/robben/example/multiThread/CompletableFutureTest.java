package com.robben.example.multiThread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
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
}
