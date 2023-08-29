package com.robben.utils.guava;

import cn.hutool.core.date.StopWatch;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.List;
import java.util.Set;

/**
 * @author hjz
 * @version 1.0
 * @date 2022/6/27 17:00
 * https://blog.csdn.net/tc979907461/article/details/105394977?spm=1001.2101.3001.6661.1&utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1-105394977-blog-120974851.pc_relevant_paycolumn_v3&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1-105394977-blog-120974851.pc_relevant_paycolumn_v3&utm_relevant_index=1
 *
 * 主要是记录一些guava的给工具类使用
 */
public class GuavaTools {


    public static void main(String[] args) {

        //字符串拼接
        List<String> list = Lists.newArrayList("a","b","c");
        String value = Joiner.on("-").skipNulls().join(list);
        System.out.println(value);
        //输出为： a-b-c

        //Splitter用来分割字符串
        String testString = "Monday,Tuesday,,Thursday,Friday,,";
        //英文分号分割；忽略空字符串
        Splitter splitter = Splitter.on(",").omitEmptyStrings().trimResults();
        System.out.println(splitter.split(testString).toString());
        //转换为了：[Monday, Tuesday, Thursday, Friday]

        //CharMatcher常用来从字符串里面提取特定字符串。
        //比如想从字符串中得到所有的数字.
        String value2 = CharMatcher.any().retainFrom("some text 2046 and more");
        //value=2046
        System.out.println(value2);


        //集合创建,各种以S结尾的工厂类简化了集合的创建。在创建泛型实例的时候，它们使代码更加简洁
        List<String> list2 =Lists.newArrayList();
        List<String> list3 = Lists.newArrayList("a","b","c");
        Set<Integer> set = Sets.newHashSet();

        //集合的交集、并集和差集
//        Sets.intersection(setA, setB);
//        Sets.union(setA, setB);
//        Sets.difference(setA, setB);

        //计时的功能不如hutool的好用
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        stopWatch.stop();
        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        System.out.println(totalTimeMillis);

        //布隆过滤器
        //期望的误判率。 误判率不能为0，因为不可能达到。
        double fpp=0.05;
        BloomFilter<Integer> bloomFilter=BloomFilter.create(Funnels.integerFunnel(),1000000,fpp);
        for (int i = 0; i < 100000; i++) {
            bloomFilter.put(i);
        }
        int count=0;
        for (int i = 100000; i < 200000; i++) {
            if (bloomFilter.mightContain(i)) {
                count++;
                System.out.println(i + "误判了");
            }
        }
        System.out.println("误判条数"+count);


        //限流RateLimiter


    }


}
