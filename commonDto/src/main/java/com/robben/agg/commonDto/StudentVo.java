package com.robben.agg.commonDto;

import lombok.Data;


@Data
public class StudentVo {

    private String name;

    private int id;

    private int age;

    public StudentVo(String name, int id, int age) {
        this.name = name;
        this.id = id;
        this.age = age;
    }

    public StudentVo() {

    }

}
