package com.hjz.flowlong.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjz.flowlong.dao.entity.SysResource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SysResourceMapper extends BaseMapper<SysResource> {
}
