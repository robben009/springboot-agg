package com.hjz.flowlong.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hjz.flowlong.dao.entity.SysResource;

import java.util.List;

public interface SysResourceService extends IService<SysResource> {

    /**
     * 根据角色ID查询资源列表
     */
    List<SysResource> getResourcesByRoleId(Long roleId);
}
