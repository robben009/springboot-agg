package com.hjz.flowlong.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjz.flowlong.dao.entity.SysResource;
import com.hjz.flowlong.dao.entity.SysRoleResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysRoleResourceMapper extends BaseMapper<SysRoleResource> {

    /**
     * 根据角色ID查询资源列表（包含资源详情）
     */
    @Select("SELECT r.* FROM sys_resource r " +
            "INNER JOIN sys_role_resource rr ON r.id = rr.resource_id " +
            "WHERE rr.role_id = #{roleId} AND r.status = 1 " +
            "ORDER BY r.parent_id, r.sort_order")
    List<SysResource> selectResourcesByRoleId(@Param("roleId") Long roleId);
}
