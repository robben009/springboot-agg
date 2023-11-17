package com.robben.nacos.ds2.model;

import lombok.Data;


@Data
public class Student2Vo {

    private String name;

    private int id;

    private int age;

    public Student2Vo(String name, int id, int age) {
        this.name = name;
        this.id = id;
        this.age = age;
    }

    public Student2Vo() {

    }

}
