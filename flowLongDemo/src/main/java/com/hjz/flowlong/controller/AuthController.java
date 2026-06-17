package com.hjz.flowlong.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.hjz.flowlong.dao.entity.SysUser;
import com.hjz.flowlong.dao.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "认证")
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final SysUserService sysUserService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam String username, @RequestParam String password) {
        Map<String, Object> result = new HashMap<>();

        SysUser user = sysUserService.findByUsername(username);
        if (user == null) {
            result.put("success", false);
            result.put("message", "用户名或密码错误");
            return result;
        }

        if (user.getStatus() != null && user.getStatus() == 0) {
            result.put("success", false);
            result.put("message", "账号已被禁用");
            return result;
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            result.put("success", false);
            result.put("message", "用户名或密码错误");
            return result;
        }

        StpUtil.login(user.getId());

        result.put("success", true);
        result.put("message", "登录成功");
        result.put("userId", user.getId());
        result.put("username", user.getUsername());
        result.put("tokenName", StpUtil.getTokenName());
        result.put("tokenValue", StpUtil.getTokenValue());
        return result;
    }

    @Operation(summary = "用户登出")
    @PostMapping("/logout")
    public Map<String, Object> logout(@RequestParam Long userId) {
        Map<String, Object> result = new HashMap<>();

        if (!StpUtil.isLogin(userId)) {
            result.put("success", false);
            result.put("message", "用户未登录");
            return result;
        }

        StpUtil.logout(userId);
        result.put("success", true);
        result.put("message", "登出成功");
        return result;
    }
}
