package com.mongodb.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/11/3 15:54
 */
@Data
@Document("User2")//指明实体类对应mongodb的哪个集合
public class User2 {
    @Field
    private Integer age2;

    @Field
    private Integer age;

    @Field
    @Indexed(expireAfterSeconds = 0)
    private LocalDateTime expireTime;

}