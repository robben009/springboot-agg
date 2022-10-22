package com.codegenerate.dao;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codegenerate.entity.UserInfoEntity;


@Mapper
public interface UserInfoDao extends BaseMapper<UserInfoEntity> {

}