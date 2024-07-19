package com.robben.agg.sharding.database.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.robben.agg.sharding.database.entity.OrderEntity;
import com.robben.agg.sharding.database.mapper.OrderMapper;
import com.robben.agg.sharding.database.service.OrderService;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderEntity> implements OrderService {

}




