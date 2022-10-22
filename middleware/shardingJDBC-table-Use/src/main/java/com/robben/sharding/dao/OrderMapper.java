package com.robben.sharding.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.robben.sharding.entity.MyOrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/11/16 17:03
 */
@Mapper
public interface OrderMapper extends BaseMapper<MyOrderEntity> {


}
