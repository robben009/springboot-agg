package com.robben.agg.base.example;

import com.robben.model.StudentVo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description： LambdaDemo
 * Author: robben
 * Date: 2021/6/8 15:59
 */
public class LambdaDemo {

    public static void main(String[] args) {

        //list ===> HashMap
        List<StudentVo> padeList = Arrays.asList(
                new StudentVo("name1",1,1),
                new StudentVo("name2",2,2),
                new StudentVo("name3",3,3)
        );
        Map<String, StudentVo> dbpadeMap = padeList.stream().collect(Collectors.toMap(StudentVo::getName, m -> m));
        Map<String, StudentVo> dbpadeMap2 = padeList.stream().collect(Collectors.toMap(m -> m.getName(), m -> m));
        //介绍stream使用  https://www.cnblogs.com/owenma/p/12207330.html

        //过滤使用
        List<StudentVo> padeList2 = padeList.stream().filter(
                person -> "name1".equals(person.getName())//只保留男性
        ).collect(Collectors.toList());

    }


}
