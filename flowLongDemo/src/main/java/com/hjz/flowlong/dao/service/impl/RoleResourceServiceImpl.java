package com.hjz.flowlong.dao.service.impl;

import com.hjz.flowlong.dao.entity.SysResource;
import com.hjz.flowlong.dao.mapper.SysRoleResourceMapper;
import com.hjz.flowlong.dao.service.SysResourceService;
import com.hjz.flowlong.model.RoleResourceResp;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class RoleResourceServiceImpl {

    private final SysRoleResourceMapper sysRoleResourceMapper;
    private final SysResourceService sysResourceService;

    /**
     * 根据角色ID查询资源树
     */
    public List<RoleResourceResp> getResourceTreeByRoleId(Long roleId) {
        List<SysResource> resources = sysRoleResourceMapper.selectResourcesByRoleId(roleId);
        return buildResourceTree(resources);
    }

    private List<RoleResourceResp> buildResourceTree(List<SysResource> resources) {
        Map<Long, RoleResourceResp> nodeMap = new HashMap<>();
        for (SysResource r : resources) {
            RoleResourceResp node = new RoleResourceResp();
            node.setId(r.getId());
            node.setName(r.getName());
            node.setType(r.getType());
            nodeMap.put(r.getId(), node);
        }

        List<RoleResourceResp> roots = new ArrayList<>();
        for (SysResource r : resources) {
            RoleResourceResp node = nodeMap.get(r.getId());
            if (r.getParentId() == 0) {
                roots.add(node);
            } else {
                RoleResourceResp parent = nodeMap.get(r.getParentId());
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(node);
                }
            }
        }
        return roots;
    }
}
