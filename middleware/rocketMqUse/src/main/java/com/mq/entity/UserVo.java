package com.mq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/7/3 19:51
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo implements Serializable {
    String name;
    int age;

    @Override
    public String toString() {
        return "UserVo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
