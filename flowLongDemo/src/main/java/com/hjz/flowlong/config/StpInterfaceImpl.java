package com.hjz.flowlong.config;

import cn.dev33.satoken.stp.StpInterface;
import com.hjz.flowlong.dao.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class StpInterfaceImpl implements StpInterface {

    private final SysUserService sysUserService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return new ArrayList<>();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        Long userId = Long.valueOf(loginId.toString());
        List<String> roles = sysUserService.findRoleCodesByUserId(userId);
        return roles != null ? roles : new ArrayList<>();
    }
}
