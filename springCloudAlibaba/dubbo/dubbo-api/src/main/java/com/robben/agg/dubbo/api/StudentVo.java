package com.robben.agg.dubbo.api;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/3/9 19:54
 */
@NoArgsConstructor
@Data
public class StudentVo implements Serializable {

    private int age;
    private String name;

    public StudentVo(int age, String name) {
        this.age = age;
        this.name = name;
    }
}
