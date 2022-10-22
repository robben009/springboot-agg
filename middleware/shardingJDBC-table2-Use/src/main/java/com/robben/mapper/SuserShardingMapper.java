package com.robben.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.robben.entity.SuserEntity;
import com.robben.entity.SuserShardingEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/12/2 10:07
 */
@Repository
@Mapper
public interface SuserShardingMapper extends BaseMapper<SuserShardingEntity> {


}
