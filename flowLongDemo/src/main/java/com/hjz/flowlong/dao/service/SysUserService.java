package com.hjz.flowlong.dao.service;

import com.hjz.flowlong.dao.entity.SysUser;

import java.util.List;

public interface SysUserService {

    SysUser findByUsername(String username);

    List<String> findRoleCodesByUserId(Long userId);
}
