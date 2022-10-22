package com.robben.example.objectSize;

import com.carrotsearch.sizeof.RamUsageEstimator;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Description： 获取对象大小
 * Author: robben
 * Date: 2020/11/21 16:45
 */
public class TestObjectSize {

    //所以所有基本类型封装对象所占内存的大小都是16字节.
    public static void main(String[] args) {
        System.out.println(RamUsageEstimator.humanSizeOf(1));
        System.out.println(RamUsageEstimator.humanSizeOf(true));
        System.out.println(RamUsageEstimator.humanSizeOf(1L));
        System.out.println(RamUsageEstimator.humanSizeOf(1f));
        System.out.println(RamUsageEstimator.humanSizeOf(1.0));
        System.out.println(RamUsageEstimator.humanSizeOf(new int[]{}));
        System.out.println(RamUsageEstimator.humanSizeOf(new ArrayList()));
        System.out.println(RamUsageEstimator.humanSizeOf(new HashMap()));
        System.out.println(RamUsageEstimator.humanSizeOf(new ObjectA()));
        System.out.println(RamUsageEstimator.humanSizeOf(new ObjectA()));
    }


    private static class ObjectA {
        String str;   // 4
        int i1;       // 4
        byte b1;      // 1
        byte b2;      // 1
        int i2;       // 4
        ObjectB obj;  //4
        byte b3;      // 1
    }

    private static class ObjectB {

    }

}
