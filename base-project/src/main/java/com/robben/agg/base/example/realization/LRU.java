package com.robben.agg.base.example.realization;

import java.util.LinkedHashMap;

public class LRU {

    //https://blog.csdn.net/wang_8101/article/details/83067860

    private final int CAPACITY=0;
    private LinkedHashMap<Integer,Integer> cache;

    public LRU(int capacity){
        cache = new LinkedHashMap<Integer,Integer>(capacity,0.75f,true){
            //在LinkedHashMap添加元素后，会调用removeEldestEntry防范，传递的参数时最久没有被访问的键值对，如果方法返回true
            @Override
            protected boolean removeEldestEntry(java.util.Map.Entry<Integer, Integer> eldest) {
                return size() > CAPACITY;
            }
        };
    }

    public int get(int key) {
        return cache.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        cache.put(key, value);
    }

}


//public class LRUCache<K, V> extends LinkedHashMap<K, V> {
//
//    private int maxEntries;
//
//    public LRUCache(int maxEntries) {
//        super(16, 0.75f, true);
//        this.maxEntries = maxEntries;
//    }
//
//    @Override
//    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
//        return size() > maxEntries;
//    }
//
//}

