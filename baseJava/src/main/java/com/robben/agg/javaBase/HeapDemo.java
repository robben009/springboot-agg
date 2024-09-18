package com.robben.agg.javaBase;

import lombok.extern.slf4j.Slf4j;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

@Slf4j
public class HeapDemo {
    public static void main(String[] args) {
        Comparator<Map.Entry<String, Integer>> comp = Comparator.comparingInt(Map.Entry::getValue);
        PriorityQueue<Map.Entry<String, Integer>> pa = new PriorityQueue<>(comp);

        Map<String, Integer> frequencies = new HashMap<>();
        frequencies.put("1",1);
        frequencies.put("2",2);

        //最小堆
        for (Map.Entry<String, Integer> s : frequencies.entrySet()) {
            if (pa.size() < 12) {
                pa.offer(s);
            } else if (comp.compare(s, pa.peek()) > 0) {
                pa.poll();
                pa.offer(s);
            }
        }

        while (!pa.isEmpty()){
            Map.Entry<String, Integer> poll = pa.poll();
            log.info("poll={}",poll);
        }

    }

}
