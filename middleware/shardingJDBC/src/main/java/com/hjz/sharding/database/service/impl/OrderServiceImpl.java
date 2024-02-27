package com.hjz.sharding.database.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjz.sharding.database.entity.OrderEntity;
import com.hjz.sharding.database.mapper.OrderMapper;
import com.hjz.sharding.database.service.OrderService;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderEntity> implements OrderService {

}




