package com.robben.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("suser_sharding")
public class SuserShardingEntity {


    private Long id;

    private Long shardingIndex;

    private String name;

}
