package com.robben.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.robben.entity.UserInfoEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author hjz
 * @version 1.0
 * @date 2022/7/11 11:50
 */
@Repository
@Mapper
public interface UserInfoEntityMapper extends BaseMapper<UserInfoEntity> {

    void handleSql(String sqlStr);

    @Insert("insert into user_info (`age`,`name`,`sex`,`create_time`,`update_time`) values (#{age},#{name},#{sex},now(),now())")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int insertUser(UserInfoEntity vo);

    //驼峰命名需要设置
    @Select("select `id`,`age`,`name`,`sex`,`create_time` as createTime,`update_time` from user_info where id = #{id}")
    UserInfoEntity getUserById(int id);

    @Update("update user_info set `name` = #{name} where sex = #{id}")
    int updateUser(UserInfoEntity vo);

    @Delete("delete from user_info where id = #{id}")
    int delUserById(int id);

}
