package com.robben.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.robben.entity.SuserShardingMonthEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/12/2 10:07
 */
@Repository
@Mapper
public interface SuserShardingMonthMapper extends BaseMapper<SuserShardingMonthEntity> {


}
