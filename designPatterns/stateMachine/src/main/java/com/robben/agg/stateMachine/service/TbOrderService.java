package com.robben.agg.stateMachine.service;

import com.robben.agg.stateMachine.dao.domain.TbOrder;

/**
* @author hjz
* @description 针对表【tb_order(订单表)】的数据库操作Service
* @createDate 2023-09-25 10:52:10
*/
public interface TbOrderService{

    TbOrder create(TbOrder tbOrder);

    TbOrder pay(Long id);

    TbOrder deliver(Long id);

    TbOrder receive(Long id);
    
}
