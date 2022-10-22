package com.robben.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("suser_sharding_month")
public class SuserShardingMonthEntity {


    private Long id;

    private Long shardingIndex;

    private String name;

}
