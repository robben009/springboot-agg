package com.hjz.flowlong.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjz.flowlong.dao.entity.SysResource;
import com.hjz.flowlong.dao.mapper.SysResourceMapper;
import com.hjz.flowlong.dao.mapper.SysRoleResourceMapper;
import com.hjz.flowlong.dao.service.SysResourceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource>
        implements SysResourceService {

    private final SysRoleResourceMapper sysRoleResourceMapper;

    @Override
    public List<SysResource> getResourcesByRoleId(Long roleId) {
        return sysRoleResourceMapper.selectResourcesByRoleId(roleId);
    }
}
