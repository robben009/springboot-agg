package com.hjz.flowlong.config;

import com.hjz.flowlong.dao.service.SysUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StpInterfaceImplTest {

    @Mock
    private SysUserService sysUserService;

    @InjectMocks
    private StpInterfaceImpl stpInterface;

    @Test
    void getRoleList_returnsRoleCodes() {
        when(sysUserService.findRoleCodesByUserId(1L))
                .thenReturn(Arrays.asList("ROLE_ADMIN", "ROLE_EMPLOYEE"));

        List<String> roles = stpInterface.getRoleList(1L, "login");

        assertEquals(2, roles.size());
        assertTrue(roles.contains("ROLE_ADMIN"));
        verify(sysUserService).findRoleCodesByUserId(1L);
    }

    @Test
    void getRoleList_returnsEmptyWhenNull() {
        when(sysUserService.findRoleCodesByUserId(2L)).thenReturn(null);

        List<String> roles = stpInterface.getRoleList(2L, "login");

        assertTrue(roles.isEmpty());
    }

    @Test
    void getRoleList_returnsEmptyWhenEmptyList() {
        when(sysUserService.findRoleCodesByUserId(3L)).thenReturn(Collections.emptyList());

        List<String> roles = stpInterface.getRoleList(3L, "login");

        assertTrue(roles.isEmpty());
    }

    @Test
    void getPermissionList_alwaysEmpty() {
        List<String> permissions = stpInterface.getPermissionList(1L, "login");

        assertTrue(permissions.isEmpty());
    }
}
