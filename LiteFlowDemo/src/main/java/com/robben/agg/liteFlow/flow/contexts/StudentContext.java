package com.robben.agg.liteFlow.flow.contexts;

import lombok.Data;

import java.io.Serializable;

@Data
public class StudentContext implements Serializable {

    private String name;

    private Integer age;

}
