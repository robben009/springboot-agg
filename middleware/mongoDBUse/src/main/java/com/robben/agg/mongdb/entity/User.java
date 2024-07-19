package com.robben.agg.mongdb.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/11/3 15:54
 */
@Data
@Document("User")//指明实体类对应mongodb的哪个集合
public class User{
    @MongoId
    private Integer Id;

    private String name;

    private Integer age;
}