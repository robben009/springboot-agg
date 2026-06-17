package com.hjz.flowlong.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.hjz.flowlong.dao.entity.SysUser;
import com.hjz.flowlong.dao.service.SysUserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private SysUserService sysUserService;

    private AuthController authController;

    private BCryptPasswordEncoder realPasswordEncoder;

    private MockedStatic<StpUtil> stpUtilMockedStatic;

    @BeforeEach
    void setUp() {
        realPasswordEncoder = new BCryptPasswordEncoder();
        authController = new AuthController(sysUserService);
        stpUtilMockedStatic = mockStatic(StpUtil.class);
    }

    @AfterEach
    void tearDown() {
        stpUtilMockedStatic.close();
    }

    @Test
    void login_success() {
        SysUser user = new SysUser();
        user.setId(1L);
        user.setUsername("admin");
        user.setPassword(realPasswordEncoder.encode("test123"));
        user.setStatus(1);

        when(sysUserService.findByUsername("admin")).thenReturn(user);
        stpUtilMockedStatic.when(StpUtil::getTokenName).thenReturn("satoken");
        stpUtilMockedStatic.when(StpUtil::getTokenValue).thenReturn("mock-token-value-123");

        Map<String, Object> result = authController.login("admin", "test123");

        assertTrue((Boolean) result.get("success"));
        assertEquals("登录成功", result.get("message"));
        assertEquals(1L, result.get("userId"));
        assertEquals("admin", result.get("username"));
        assertEquals("satoken", result.get("tokenName"));
        assertEquals("mock-token-value-123", result.get("tokenValue"));
        stpUtilMockedStatic.verify(() -> StpUtil.login(1L));
        verify(sysUserService).findByUsername("admin");
    }

    @Test
    void login_userNotFound() {
        when(sysUserService.findByUsername("unknown")).thenReturn(null);

        Map<String, Object> result = authController.login("unknown", "test123");

        assertFalse((Boolean) result.get("success"));
        assertEquals("用户名或密码错误", result.get("message"));
        stpUtilMockedStatic.verify(() -> StpUtil.login(anyLong()), never());
    }

    @Test
    void login_wrongPassword() {
        SysUser user = new SysUser();
        user.setId(2L);
        user.setUsername("zhangsan");
        user.setPassword(realPasswordEncoder.encode("correctPassword"));
        user.setStatus(1);

        when(sysUserService.findByUsername("zhangsan")).thenReturn(user);

        Map<String, Object> result = authController.login("zhangsan", "wrongPassword");

        assertFalse((Boolean) result.get("success"));
        assertEquals("用户名或密码错误", result.get("message"));
        stpUtilMockedStatic.verify(() -> StpUtil.login(anyLong()), never());
    }

    @Test
    void login_disabledAccount() {
        SysUser user = new SysUser();
        user.setId(5L);
        user.setUsername("zhaoliu");
        user.setPassword(realPasswordEncoder.encode("test123"));
        user.setStatus(0);

        when(sysUserService.findByUsername("zhaoliu")).thenReturn(user);

        Map<String, Object> result = authController.login("zhaoliu", "test123");

        assertFalse((Boolean) result.get("success"));
        assertEquals("账号已被禁用", result.get("message"));
        stpUtilMockedStatic.verify(() -> StpUtil.login(anyLong()), never());
    }

    @Test
    void logout_success() {
        Long userId = 1L;
        stpUtilMockedStatic.when(() -> StpUtil.isLogin(userId)).thenReturn(true);

        Map<String, Object> result = authController.logout(userId);

        assertTrue((Boolean) result.get("success"));
        assertEquals("登出成功", result.get("message"));
        stpUtilMockedStatic.verify(() -> StpUtil.logout(userId));
    }

    @Test
    void logout_notLoggedIn() {
        Long userId = 999L;
        stpUtilMockedStatic.when(() -> StpUtil.isLogin(userId)).thenReturn(false);

        Map<String, Object> result = authController.logout(userId);

        assertFalse((Boolean) result.get("success"));
        assertEquals("用户未登录", result.get("message"));
        stpUtilMockedStatic.verify(() -> StpUtil.logout(userId), never());
    }
}
