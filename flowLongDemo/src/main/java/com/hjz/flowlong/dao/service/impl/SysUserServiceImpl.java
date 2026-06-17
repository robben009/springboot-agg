package com.hjz.flowlong.dao.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hjz.flowlong.dao.entity.SysUser;
import com.hjz.flowlong.dao.mapper.SysUserMapper;
import com.hjz.flowlong.dao.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper sysUserMapper;

    @Override
    public SysUser findByUsername(String username) {
        return sysUserMapper.selectOne(
                Wrappers.<SysUser>lambdaQuery()
                        .eq(SysUser::getUsername, username)
        );
    }

    @Override
    public List<String> findRoleCodesByUserId(Long userId) {
        return sysUserMapper.selectRoleCodesByUserId(userId);
    }
}
