package com.robben.agg.base.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.robben.agg.base.dao.entity.UserInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
* @author hjz
* @description 针对表【user_info】的数据库操作Mapper
* @createDate 2023-01-31 16:39:50
* @Entity generator.domain.UserInfo
*/
//@Repository
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfoEntity> {

    @Select("${sql}")
    void handleSql(String sql);

}




