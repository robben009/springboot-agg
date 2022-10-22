package com.robben.sharding.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class UserEntity {

    private Long id;

    private Long sId;

    private String name;

}
