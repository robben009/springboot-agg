package com.robben.agg.javabase;

import com.google.common.base.Joiner;
import com.robben.agg.base.model.StudentVo;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class JDK8CollectorsDemo {

    public void test(){

        StudentVo s1 = new StudentVo("aa", 10,1);
        StudentVo s2 = new StudentVo("bb", 20,2);
        StudentVo s3 = new StudentVo("cc", 10,3);
        List<StudentVo> list = Arrays.asList(s1, s2, s3);

        //获取list中某个属性的集合
        List<Integer> ageList = list.stream().map(StudentVo::getAge).collect(Collectors.toList()); // [10, 20, 10]

        //转成set
        Set<Integer> ageSet = list.stream().map(StudentVo::getAge).collect(Collectors.toSet()); // [20, 10]

        //list组装成一个map集合
        Map<String, Integer> studentMap = list.stream().collect(Collectors.toMap(StudentVo::getName, StudentVo::getAge)); // {cc=10, bb=20, aa=10}

        //字符串分隔符连接
        String joinName = list.stream().map(StudentVo::getName).collect(Collectors.joining(",", "(", ")")); // (aa,bb,cc)

        //聚合操作
        //1.学生总数
        Long count = list.stream().collect(Collectors.counting()); // 3
        //2.最大年龄 (最小的minBy同理)
        Integer maxAge = list.stream().map(StudentVo::getAge).collect(Collectors.maxBy(Integer::compare)).get(); // 20
        //3.所有人的年龄
        Integer sumAge = list.stream().collect(Collectors.summingInt(StudentVo::getAge)); // 40
        //4.平均年龄
        Double averageAge = list.stream().collect(Collectors.averagingDouble(StudentVo::getAge)); // 13.333333333333334
        // 带上以上所有方法
        DoubleSummaryStatistics statistics = list.stream().collect(Collectors.summarizingDouble(StudentVo::getAge));
        System.out.println("count:" + statistics.getCount() + ",max:" + statistics.getMax() + ",sum:" + statistics.getSum() + ",average:" + statistics.getAverage());

        //分组
        Map<Integer, List<StudentVo>> ageMap = list.stream().collect(Collectors.groupingBy(StudentVo::getAge));
        //多重分组,先根据类型分再根据年龄分
        Map<Integer, Map<Integer, List<StudentVo>>> typeAgeMap = list.stream().collect(Collectors.groupingBy(StudentVo::getId, Collectors.groupingBy(StudentVo::getAge)));

        //分区
        //分成两部分，一部分大于10岁，一部分小于等于10岁
        Map<Boolean, List<StudentVo>> partMap = list.stream().collect(Collectors.partitioningBy(v -> v.getAge() > 10));

        //规约
        Integer allAge = list.stream().map(StudentVo::getAge).collect(Collectors.reducing(Integer::sum)).get(); //40　　


        //返回 对象集合以类属性一升序排序
        list.stream().sorted(Comparator.comparing(StudentVo::getAge).reversed());

        //先以属性一升序,升序结果进行属性一降序,再进行属性二升序
        list.stream().sorted(Comparator.comparing(StudentVo::getAge).reversed().thenComparing(StudentVo::getId));

        //先以属性一降序,再进行属性二升序
        list.stream().sorted(Comparator.comparing(StudentVo::getAge,Comparator.reverseOrder()).thenComparing(StudentVo::getId));

    }


    //排序map中的key,并且是的key和value使用&=链接
    private static String paramMapSortByKey(Map<String, String> map) {
        TreeMap<String, String> treeMap = map.entrySet().stream()
                .collect(Collectors.toMap(entry-> StringUtils.lowerCase(entry.getKey()), entry->
                        StringUtils.lowerCase(entry.getValue()), (v1, v2) -> v2, TreeMap::new));
        //对 map 中排序后，按 key1=value1&key2=value2 进行字符串拼接
        String mapSortToString = Joiner.on("&").withKeyValueSeparator("=").join(treeMap);
        return mapSortToString;
    }


}
