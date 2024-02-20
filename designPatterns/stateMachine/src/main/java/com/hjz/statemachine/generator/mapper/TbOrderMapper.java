package com.hjz.statemachine.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjz.statemachine.generator.domain.TbOrder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author hjz
* @description 针对表【tb_order(订单表)】的数据库操作Mapper
* @createDate 2023-09-25 10:52:10
* @Entity generator.domain.TbOrder
*/
@Repository
@Mapper
public interface TbOrderMapper extends BaseMapper<TbOrder> {

}




