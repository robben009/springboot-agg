# Sa-Token 集成设计文档

**日期：** 2026-06-17
**模块：** flowLongDemo
**状态：** 待实施

## 概述

在 flowLongDemo 项目中集成 Sa-Token 权限认证框架，实现登录/登出接口，并对角色资源查询接口增加超级管理员权限限制。

## 架构设计

### 请求流程

```
Client → POST /auth/login (username + password)
         ↓
     AuthController.login()
         ↓
     查询 sys_user 表验证用户名密码（BCrypt 比对）
         ↓
     StpUtil.login(userId)     ← Sa-Token 创建 Session
         ↓
     返回 { success, tokenName, tokenValue, userId, username }

Client → GET /role/resources/{roleId}
         ↓
     Sa-Token 拦截器检查是否已登录
         ↓
     @SaCheckRole("ROLE_ADMIN") 检查角色
         ↓
     RoleResourceController.getRoleResources()

Client → POST /auth/logout (userId)
         ↓
     AuthController.logout()
         ↓
     StpUtil.logout(userId)    ← Sa-Token 注销 Session
         ↓
     返回 { success, message }
```

### 文件变更

**新增：**
- `AuthController.java` - 登录/登出 REST 接口
- `SaTokenConfig.java` - Sa-Token 拦截器配置
- `StpInterfaceImpl.java` - Sa-Token 角色/权限查询实现

**修改：**
- `RoleResourceController.java` - 添加 `@SaCheckRole("ROLE_ADMIN")` 注解
- `application.yml` - 添加 Sa-Token 基础配置

## 接口设计

### 1. 登录接口

| 属性 | 值 |
|------|-----|
| 路径 | `POST /auth/login` |
| 参数 | `username` (String, 必填), `password` (String, 必填) |

**成功响应 (200)：**
```json
{
  "success": true,
  "message": "登录成功",
  "userId": 1,
  "username": "admin",
  "tokenName": "satoken",
  "tokenValue": "xxxx-xxxx-xxxx"
}
```

**失败响应：**
- `400`：`{ "success": false, "message": "用户名或密码错误" }`
- `400`：`{ "success": false, "message": "账号已被禁用" }`

### 2. 登出接口

| 属性 | 值 |
|------|-----|
| 路径 | `POST /auth/logout` |
| 参数 | `userId` (Long, 必填) |

**成功响应 (200)：**
```json
{ "success": true, "message": "登出成功" }
```

**失败响应：**
- `400`：`{ "success": false, "message": "用户未登录" }`

### 3. 角色资源查询（增加权限限制）

| 属性 | 值 |
|------|-----|
| 路径 | `GET /role/resources/{roleId}` |
| 前置条件 | 已登录 + 角色为 ROLE_ADMIN |

**失败响应：**
- 未登录：`401`
- 无权限：`403`

## Sa-Token 配置

### application.yml

```yaml
sa-token:
  token-name: satoken
  timeout: 86400
  active-timeout: -1
  is-concurrent: true
  token-style: uuid
```

### 拦截器配置

- 全局拦截：所有请求需要登录
- 白名单：`/auth/login`（登录接口不需要校验）

### 角色校验实现

通过 `StpInterface` 接口实现：
1. 根据 userId 查询 `sys_user_role` 关联 `sys_role`
2. 返回用户的 `role_code` 列表（如 `["ROLE_ADMIN", "ROLE_EMPLOYEE"]`）
3. Sa-Token 自动基于此进行 `@SaCheckRole` 校验

## 数据库依赖

已有表结构，无需新增表：
- `sys_user` - 用户表（username, password 为 BCrypt 加密）
- `sys_role` - 角色表（role_code 如 ROLE_ADMIN）
- `sys_user_role` - 用户角色关联表

测试数据中 admin 用户（id=1）拥有 ROLE_ADMIN 角色，可用于验证超级管理员权限。
