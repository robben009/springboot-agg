package com.robben.agg.base.javaBase.array;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class StackQueueMap {
    //Stack、Queue、Map的遍历

    public static void main(String[] args) {
        List<String> list=new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");

        //这只是碰巧没触发ConcurrentModificationException异常
    /*    for (String s : list) {
            if("two".equals(s)){
                list.remove(s);
            }
        }*/

        //使用迭代器删除列表中的元素
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){

            //多线程情况下加锁
            synchronized (list){
//                System.out.println(iterator.next());  //直接这样写会报错
                String item = iterator.next();
                System.out.println(item);
                if("two".equals(item)){
                    //TODO 注意！！！ 不是list的remove
                    iterator.remove();
                }
            }
        }


        //栈的操作
        Stack<String> s = new Stack<String>();
        s.push("a1");
        s.push("a2");
        s.push("a3");

        for (String s1 : s) {
            System.out.println(s1);
        }

        Iterator<String> iterators = s.iterator();
        while (iterators.hasNext()){
            String a = iterators.next();
            System.out.println(a);
        }
        while (!s.empty()) {
            System.out.println(s.pop());
        }

        //Map的遍历
        Map<String, String> map = new HashMap<String, String>();
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "c");

        //最简洁、最通用的遍历方式
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
           Map.Entry entry = (Map.Entry) iter.next();
           Object key = entry.getKey();
           Object val = entry.getValue();
        }

        //阻塞队列的遍历
        Queue<Integer> q = new LinkedBlockingQueue<Integer>();
        //初始化队列
        for (int i = 0; i < 5; i++) {
            q.offer(i);
        }
        System.out.println("-------1-----");

        //集合方式遍历，元素不会被移除
        for (Integer x : q) {
            System.out.println(x);
        }

        System.out.println("-------2-----");
        //队列方式遍历，元素逐个被移除
        while (q.peek() != null) {
            System.out.println(q.poll());
        }

//        从队列中增加元素
//        add：在添加元素的时候，若超出了度列的长度会直接抛出异常：
//        put：若向队尾添加元素的时候发现队列已经满了会发生阻塞一直等待空间，以加入元素。
//        offer：在添加元素时，如果发现队列已满无法添加的话，会直接返回false。
//
//        从队列中取出并移除元素的方法。
//        remove:若队列为空，抛出NoSuchElementException异常。
//        take:若队列为空，发生阻塞，等待有元素。
//        poll: 若队列为空，返回null。

    }

}
