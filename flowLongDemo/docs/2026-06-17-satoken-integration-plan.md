# Sa-Token 集成实现计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 在 flowLongDemo 中集成 Sa-Token 权限认证，实现登录/登出接口，并对角色资源查询接口增加超级管理员权限限制。

**Architecture:** 新建 AuthController 处理认证接口，新建 SaTokenConfig 配置拦截器，新建 StpInterfaceImpl 实现角色查询。通过 Sa-Token 的 @SaCheckRole 注解实现接口级权限控制。

**Tech Stack:** Spring Boot 2.7, MyBatis-Plus 3.5.x, Sa-Token 1.45.0, Lombok, MySQL 5.7, BCrypt

---

## 文件映射

| 操作 | 文件路径 | 职责 |
|------|----------|------|
| 创建 | `src/main/java/com/hjz/flowlong/dao/entity/SysUser.java` | 用户表 Entity |
| 创建 | `src/main/java/com/hjz/flowlong/dao/entity/SysRole.java` | 角色表 Entity |
| 创建 | `src/main/java/com/hjz/flowlong/dao/entity/SysUserRole.java` | 用户角色关联表 Entity |
| 创建 | `src/main/java/com/hjz/flowlong/dao/mapper/SysUserMapper.java` | 用户 Mapper |
| 创建 | `src/main/java/com/hjz/flowlong/dao/mapper/SysRoleMapper.java` | 角色 Mapper |
| 创建 | `src/main/java/com/hjz/flowlong/dao/service/SysUserService.java` | 用户 Service |
| 创建 | `src/main/java/com/hjz/flowlong/dao/service/impl/SysUserServiceImpl.java` | 用户 Service 实现 |
| 创建 | `src/main/java/com/hjz/flowlong/controller/AuthController.java` | 登录/登出 REST 接口 |
| 创建 | `src/main/java/com/hjz/flowlong/config/SaTokenConfig.java` | Sa-Token 拦截器配置 |
| 创建 | `src/main/java/com/hjz/flowlong/config/StpInterfaceImpl.java` | Sa-Token 角色/权限查询实现 |
| 创建 | `src/test/java/com/hjz/flowlong/controller/AuthControllerTest.java` | AuthController 单元测试 |
| 创建 | `src/test/java/com/hjz/flowlong/config/StpInterfaceImplTest.java` | StpInterfaceImpl 单元测试 |
| 修改 | `src/main/java/com/hjz/flowlong/controller/RoleResourceController.java` | 添加 @SaCheckRole 注解和登录校验 |
| 修改 | `src/main/resources/application.yml` | 添加 Sa-Token 配置 |
| 修改 | `pom.xml` | 添加 spring-security-crypto 依赖（BCrypt） |

---

### Task 1: 创建 User/Role/UserRole Entity

**Files:**
- Create: `src/main/java/com/hjz/flowlong/dao/entity/SysUser.java`
- Create: `src/main/java/com/hjz/flowlong/dao/entity/SysRole.java`
- Create: `src/main/java/com/hjz/flowlong/dao/entity/SysUserRole.java`

参照现有 `SysResource.java` 和 `SysRoleResource.java` 的风格（@Data + @TableName + @TableId(type = IdType.AUTO) + Serializable + LocalDateTime）。

**SysUser.java** - 对应 `sys_user` 表：
```java
package com.hjz.flowlong.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("sys_user")
public class SysUser implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String email;

    private String phone;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
```

**SysRole.java** - 对应 `sys_role` 表：
```java
package com.hjz.flowlong.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("sys_role")
public class SysRole implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String roleCode;

    private String roleName;

    private String description;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
```

**SysUserRole.java** - 对应 `sys_user_role` 表：
```java
package com.hjz.flowlong.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_user_role")
public class SysUserRole implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long roleId;
}
```

- [ ] **Step 1: 创建三个 Entity 文件**

按照上方代码创建文件。

- [ ] **Step 2: 编译验证**

```bash
cd /Users/hjz/openProjects/springboot-agg/flowLongDemo && mvn compile -q
```
Expected: BUILD SUCCESS

---

### Task 2: 创建 Mapper 接口

**Files:**
- Create: `src/main/java/com/hjz/flowlong/dao/mapper/SysUserMapper.java`
- Create: `src/main/java/com/hjz/flowlong/dao/mapper/SysRoleMapper.java`

参照 `SysRoleResourceMapper.java` 风格（extends BaseMapper<T> + @Repository + @Mapper）。

**SysUserMapper.java:**
```java
package com.hjz.flowlong.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjz.flowlong.dao.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("SELECT r.role_code FROM sys_role r " +
            "INNER JOIN sys_user_role ur ON r.id = ur.role_id " +
            "WHERE ur.user_id = #{userId}")
    List<String> selectRoleCodesByUserId(@Param("userId") Long userId);
}
```

**SysRoleMapper.java:**
```java
package com.hjz.flowlong.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjz.flowlong.dao.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
}
```

- [ ] **Step 1: 创建两个 Mapper 接口**

- [ ] **Step 2: 编译验证**

```bash
cd /Users/hjz/openProjects/springboot-agg/flowLongDemo && mvn compile -q
```
Expected: BUILD SUCCESS

---

### Task 3: 创建 SysUserService 及实现类

**Files:**
- Create: `src/main/java/com/hjz/flowlong/dao/service/SysUserService.java`
- Create: `src/main/java/com/hjz/flowlong/dao/service/impl/SysUserServiceImpl.java`

参照 `SysResourceService.java` / `SysResourceServiceImpl.java` 风格。使用构造器注入 + Lombok `@AllArgsConstructor`。

**SysUserService.java:**
```java
package com.hjz.flowlong.dao.service;

import com.hjz.flowlong.dao.entity.SysUser;

import java.util.List;

public interface SysUserService {

    SysUser findByUsername(String username);

    List<String> findRoleCodesByUserId(Long userId);
}
```

**SysUserServiceImpl.java:**
```java
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
```

- [ ] **Step 1: 创建 Service 接口和实现类**

- [ ] **Step 2: 编译验证**

```bash
cd /Users/hjz/openProjects/springboot-agg/flowLongDemo && mvn compile -q
```
Expected: BUILD SUCCESS

---

### Task 4: 添加 BCrypt 依赖到 pom.xml

**Files:**
- Modify: `pom.xml`

Sa-Token 本身不提供密码加密工具，需要使用 Spring Security 的 BCrypt 来比对数据库中的加密密码。

在 `<dependencies>` 中添加：
```xml
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-crypto</artifactId>
</dependency>
```

- [ ] **Step 1: 添加 spring-security-crypto 依赖**

注意：`version` 由 spring-boot-dependencies BOM 管理，不需要显式指定。

- [ ] **Step 2: 编译验证**

```bash
cd /Users/hjz/openProjects/springboot-agg/flowLongDemo && mvn compile -q
```
Expected: BUILD SUCCESS

---

### Task 5: 实现 StpInterfaceImpl（角色查询）

**Files:**
- Create: `src/main/java/com/hjz/flowlong/config/StpInterfaceImpl.java`

Sa-Token 的 `@SaCheckRole` 需要通过 `StpInterface` 获取用户角色列表。实现该类从数据库查询用户角色。

**StpInterfaceImpl.java:**
```java
package com.hjz.flowlong.config;

import cn.dev33.satoken.stp.StpInterface;
import com.hjz.flowlong.dao.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class StpInterfaceImpl implements StpInterface {

    private final SysUserService sysUserService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return List.of();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        Long userId = Long.valueOf(loginId.toString());
        return sysUserService.findRoleCodesByUserId(userId);
    }
}
```

注意：`List.of()` 是 Java 9+ 语法，但 pom.xml 中 maven-compiler-plugin 配置了 source/target 为 1.8，所以需要改用：

```java
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
```

- [ ] **Step 1: 创建 StpInterfaceImpl**

- [ ] **Step 2: 编译验证**

```bash
cd /Users/hjz/openProjects/springboot-agg/flowLongDemo && mvn compile -q
```
Expected: BUILD SUCCESS

---

### Task 6: 实现 AuthController（登录/登出）

**Files:**
- Create: `src/main/java/com/hjz/flowlong/controller/AuthController.java`

**AuthController.java:**
```java
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
```

- [ ] **Step 1: 创建 AuthController**

- [ ] **Step 2: 编译验证**

```bash
cd /Users/hjz/openProjects/springboot-agg/flowLongDemo && mvn compile -q
```
Expected: BUILD SUCCESS

---

### Task 7: 实现 SaTokenConfig（拦截器配置）

**Files:**
- Create: `src/main/java/com/hjz/flowlong/config/SaTokenConfig.java`

**SaTokenConfig.java:**
```java
package com.hjz.flowlong.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/auth/login");
    }
}
```

- [ ] **Step 1: 创建 SaTokenConfig**

- [ ] **Step 2: 编译验证**

```bash
cd /Users/hjz/openProjects/springboot-agg/flowLongDemo && mvn compile -q
```
Expected: BUILD SUCCESS

---

### Task 8: 修改 application.yml 添加 Sa-Token 配置

**Files:**
- Modify: `src/main/resources/application.yml`

在现有配置末尾添加：
```yaml
# Sa-Token Config
sa-token:
  token-name: satoken
  timeout: 86400
  active-timeout: -1
  is-concurrent: true
  token-style: uuid
```

完整文件内容（保留原有配置）：
```yaml
server:
  port: 8000
spring:
  application:
    name: example
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://119.29.194.246:53306/flowlong?characterEncoding=utf8&useSSL=false
    username: root
    password: JZh2019@nodejsjava

# FlowLong Config
flowlong:
  remind:
    cron: "*/5 * * * * ?"
  eventing:
    task: true

# Logger Config
logging:
  level:
    mybatis.mate: debug

knife4j:
  enable: true
  production: false

# Sa-Token Config
sa-token:
  token-name: satoken
  timeout: 86400
  active-timeout: -1
  is-concurrent: true
  token-style: uuid
```

- [ ] **Step 1: 修改 application.yml**

- [ ] **Step 2: 编译验证**

```bash
cd /Users/hjz/openProjects/springboot-agg/flowLongDemo && mvn compile -q
```
Expected: BUILD SUCCESS

---

### Task 9: 修改 RoleResourceController 添加权限限制

**Files:**
- Modify: `src/main/java/com/hjz/flowlong/controller/RoleResourceController.java`

添加 `@SaCheckRole("ROLE_ADMIN")` 注解到 `getRoleResources` 方法上。

修改后的完整文件：
```java
package com.hjz.flowlong.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
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
    @SaCheckRole("ROLE_ADMIN")
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
```

变更说明：
- 新增 import：`cn.dev33.satoken.annotation.SaCheckRole`
- 在 `getRoleResources` 方法上添加 `@SaCheckRole("ROLE_ADMIN")` 注解（放在 `@GetMapping` 之前）

- [ ] **Step 1: 修改 RoleResourceController**

- [ ] **Step 2: 编译验证**

```bash
cd /Users/hjz/openProjects/springboot-agg/flowLongDemo && mvn compile -q
```
Expected: BUILD SUCCESS

---

### Task 10: 编写 AuthController 单元测试

**Files:**
- Create: `src/test/java/com/hjz/flowlong/controller/AuthControllerTest.java`
- Create: `src/test/java/com/hjz/flowlong/config/StpInterfaceImplTest.java`

项目使用 Java 8（pom.xml 中 source/target = 1.8），使用 JUnit 5 + Mockito。

**AuthControllerTest.java:**
```java
package com.hjz.flowlong.controller;

import com.hjz.flowlong.dao.entity.SysUser;
import com.hjz.flowlong.dao.service.SysUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private SysUserService sysUserService;

    @InjectMocks
    private AuthController authController;

    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    void login_success() {
        SysUser user = new SysUser();
        user.setId(1L);
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("test123"));
        user.setStatus(1);
        when(sysUserService.findByUsername("admin")).thenReturn(user);

        Map<String, Object> result = authController.login("admin", "test123");

        assertTrue((Boolean) result.get("success"));
        assertEquals("登录成功", result.get("message"));
        assertEquals(1L, result.get("userId"));
        assertEquals("admin", result.get("username"));
        assertNotNull(result.get("tokenValue"));
        verify(sysUserService).findByUsername("admin");
    }

    @Test
    void login_userNotFound() {
        when(sysUserService.findByUsername("unknown")).thenReturn(null);

        Map<String, Object> result = authController.login("unknown", "test123");

        assertFalse((Boolean) result.get("success"));
        assertEquals("用户名或密码错误", result.get("message"));
    }

    @Test
    void login_wrongPassword() {
        SysUser user = new SysUser();
        user.setId(2L);
        user.setUsername("zhangsan");
        user.setPassword(passwordEncoder.encode("correctPassword"));
        user.setStatus(1);
        when(sysUserService.findByUsername("zhangsan")).thenReturn(user);

        Map<String, Object> result = authController.login("zhangsan", "wrongPassword");

        assertFalse((Boolean) result.get("success"));
        assertEquals("用户名或密码错误", result.get("message"));
    }

    @Test
    void login_disabledAccount() {
        SysUser user = new SysUser();
        user.setId(5L);
        user.setUsername("zhaoliu");
        user.setPassword(passwordEncoder.encode("test123"));
        user.setStatus(0);
        when(sysUserService.findByUsername("zhaoliu")).thenReturn(user);

        Map<String, Object> result = authController.login("zhaoliu", "test123");

        assertFalse((Boolean) result.get("success"));
        assertEquals("账号已被禁用", result.get("message"));
    }

    @Test
    void logout_success() {
        // 先登录
        SysUser user = new SysUser();
        user.setId(1L);
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("test123"));
        user.setStatus(1);
        when(sysUserService.findByUsername("admin")).thenReturn(user);

        authController.login("admin", "test123");

        // 再登出
        Map<String, Object> result = authController.logout(1L);

        assertTrue((Boolean) result.get("success"));
        assertEquals("登出成功", result.get("message"));
    }

    @Test
    void logout_notLoggedIn() {
        Map<String, Object> result = authController.logout(999L);

        assertFalse((Boolean) result.get("success"));
        assertEquals("用户未登录", result.get("message"));
    }
}
```

**StpInterfaceImplTest.java:**
```java
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
```

- [ ] **Step 1: 创建测试文件**

- [ ] **Step 2: 运行测试**

```bash
cd /Users/hjz/openProjects/springboot-agg/flowLongDemo && mvn test -q
```
Expected: All tests pass

- [ ] **Step 3: 提交**

所有代码和测试完成。

---

## 自审检查

| 检查项 | 状态 |
|--------|------|
| 覆盖设计文档所有需求 | ✅ 登录/登出接口、权限限制、Sa-Token 配置、角色查询全部有对应 Task |
| 无 TBD/TODO 占位符 | ✅ 每个步骤都有具体代码 |
| 无模糊描述 | ✅ 每个步骤都有完整代码块和命令 |
| 类型一致性 | ✅ 所有 Entity/Service/Mapper 名称在各 Task 中一致 |
| Java 8 兼容 | ✅ 使用 new ArrayList<>() 而非 List.of() |
| 构造器注入 | ✅ 所有 @Autowired 均为构造器注入 + Lombok |
| 测试覆盖 | ✅ AuthController 6 个测试 + StpInterfaceImpl 4 个测试 |
