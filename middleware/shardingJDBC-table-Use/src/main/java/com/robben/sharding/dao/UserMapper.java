package com.robben.sharding.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.robben.sharding.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/11/16 17:03
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

    void createTable();


}
