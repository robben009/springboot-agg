package com.robben.agg.javabase.realization;

/**
 * Description： TODO
 * Author: robben
 * Date: 2020/12/10 11:22
 */

import java.util.HashMap;

public class LRUCache<K,V>  {
    private int currentCacheSize;
    private int cacheCapacity;
    private HashMap<K,CacheNode> caches;
    private CacheNode header;
    private CacheNode tail;

    public LRUCache(int size){
        currentCacheSize = 0;
        this.cacheCapacity = size;
        caches = new HashMap<>(size);
    }

    public void put(K k,V v){
        CacheNode node = caches.get(k);
        if(node == null){
            if(caches.size() >= cacheCapacity){
                caches.remove(tail.key);
                removeLast();
            }
            node = new CacheNode();
            node.key = k;
        }
        node.value = v;
        moveToFirst(node);
        caches.put(k,node);
    }

    public Object get(K k){
        CacheNode node = caches.get(k);
        if(node == null){
            return null;
        }
        moveToFirst(node);
        return node.value;
    }

    public Object remove(K k) {
        CacheNode node = caches.get(k);
        if (node != null) {
            // 有可能它不是第一个
            if (node.pre != null) {
                node.pre.next = node.next;
            }
            // 有可能它不是最后一个
            if (node.next != null){
                node.next.pre = node.pre;
            }

            if(node == header){
                header = node.next;
            }
            if(node == tail){
                tail = node.pre;
            }
        }
        return caches.remove(k);
    }

    public void removeLast(){
        if(tail != null){
            tail = tail.pre;
            if(tail == null){
                header = null;
            }else{
                tail.next = null;
            }
        }
    }

    public void clear(){
        tail = null;
        header = null;
        caches.clear();
    }

    public void moveToFirst(CacheNode node){
        if(header == node){
            return ;
        }

        if(node.next != null){
            node.next.pre = node.pre;
        }

        if(node.pre!=null){
            node.pre.next = node.next;
        }

        if(node == tail){
            tail = node.pre;
        }

        if(tail == null || header == null){
            tail = header = node;
            return ;
        }

        node.next = header;
        header.pre = node;
        header = node;
        header.pre = null;
    }

    class CacheNode{
        CacheNode pre;
        CacheNode next;
        Object key;
        Object value;
        public CacheNode(){ }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        CacheNode node = header;
        while (node != null) {
            sb.append(String.format("%s%s",node.key,node.value));
            node = node.next;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        LRUCache<Integer,String> lru = new LRUCache<>(3);
        lru.put(1,"a");
        System.out.println("lru : "+lru.toString());

        lru.put(2,"b");
        System.out.println("lru : "+lru.toString());

        lru.put(3,"c");
        System.out.println("lru : "+lru.toString());

        lru.put(4,"d");
        System.out.println("lru : "+lru.toString());

        lru.put(1,"aa");
        System.out.println("lru : "+lru.toString());

        lru.put(2,"bb");
        System.out.println("lru : "+lru.toString());

        lru.get(4);
        System.out.println("lru : "+lru.toString());

        lru.remove(2);
        System.out.println("lru : "+lru.toString());
    }

}

