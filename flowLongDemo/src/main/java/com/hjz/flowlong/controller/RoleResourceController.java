package com.hjz.flowlong.controller;

import com.hjz.flowlong.dao.service.impl.RoleResourceServiceImpl;
import com.hjz.flowlong.model.RoleResourceResp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "角色资源")
@RestController
@RequestMapping("/role")
@AllArgsConstructor
@Slf4j
public class RoleResourceController {

    private final RoleResourceServiceImpl roleResourceService;

    @Operation(summary = "查询角色拥有的资源列表")
    @GetMapping("/resources/{roleId}")
    public Map<String, Object> getRoleResources(@PathVariable Long roleId) {
        Map<String, Object> result = new HashMap<>();
        List<RoleResourceResp> tree = roleResourceService.getResourceTreeByRoleId(roleId);
        result.put("success", true);
        result.put("roleId", roleId);
        result.put("count", tree.size());
        result.put("resources", tree);
        return result;
    }
}
