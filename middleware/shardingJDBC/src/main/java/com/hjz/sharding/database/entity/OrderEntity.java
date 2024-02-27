package com.hjz.sharding.database.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_order")
public class OrderEntity {

    @TableId(type = IdType.AUTO)
    private Long orderId;

    private String orderName;

}
