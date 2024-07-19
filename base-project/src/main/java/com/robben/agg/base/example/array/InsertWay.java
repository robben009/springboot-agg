package com.robben.example.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsertWay {

    public static void main(String[] args) {
        int[] a = {1,2,3,4,5,6,7,8,9};
        Integer[] alist = {1,2,3,4,5,6,7,8,9};

//        int[] b = addTail(a,10);
//        int[] b = addHead(a,10);
//        System.out.println(Arrays.toString(b));

        List<Integer> list = new ArrayList<>(Arrays.asList(alist));
//        list.add(11);
//        System.out.println(JSON.toJSONString(list));
//        list = addHead(list,12);
//        System.out.println(JSON.toJSONString(list));

    }

    //尾插法
    public static int[] addTail(int[] arr,int value){
        int[] a1 = Arrays.copyOf(arr,arr.length+1);
        a1[arr.length] = value;
        return a1;
    }


    //头插法
    public static int[] addHead(int[] arr,int value){
        int[] a1 = Arrays.copyOf(arr,arr.length+1);
        for(int i=a1.length-1;i>0;i--){
            a1[i]=a1[i-1];
        }
        a1[0]=value;
        return a1;
    }

    //头插法-list
    public static List<Integer> addHead(List<Integer> a1,int value){
        List<Integer> list = new ArrayList<>(a1.size()+1);
        list.add(value);
        for (int i = 0; i < a1.size(); i++) {
            list.add(a1.get(i));
        }
        return list;
    }

}


