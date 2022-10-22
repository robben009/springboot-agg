package com.robben.sharding.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("my_order")
public class MyOrderEntity {

    private Long id;

    private Long sId;

    private String name;

}
