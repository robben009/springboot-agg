---
last_updated: 2026-03-28
status: active          # active | deprecated | draft
owner: @Jack
---

# 错误处理规范

## 基本原则

- 业务错误使用明确错误码。
- 系统异常统一转换为标准错误响应。
- 禁止向客户端暴露堆栈、SQL、服务器路径等内部信息。
- 日志记录异常详情，响应返回可理解的错误信息。

## 错误码

错误码定义见 `docs/reference/error-codes.md`。新增错误场景必须同步维护错误码文档。

错误码建议按模块分段：

- `AUTH_xxx`: 认证和授权。
- `USER_xxx`: 用户。
- `PROJECT_xxx`: 项目。
- `BILLING_xxx`: 计费。
- `SYS_xxx`: 系统通用。

## 异常分类

推荐异常类型：

- `BusinessException`: 可预期业务错误。
- `UnauthorizedException`: 未登录或登录失效。
- `ForbiddenException`: 无权限。
- `NotFoundException`: 资源不存在。
- `ExternalServiceException`: 外部服务调用失败。

## Controller 异常处理

使用全局异常处理器统一转换响应，不在每个 controller 中重复 try-catch。

全局异常处理器职责：

- 捕获业务异常并返回对应错误码。
- 捕获参数校验异常并返回字段错误。
- 捕获未知异常，记录错误日志，返回通用系统错误。

## 禁止事项

- 禁止 `e.printStackTrace()`。
- 禁止吞掉异常后返回成功。
- 禁止直接返回异常 message 给前端。
- 禁止在业务代码中拼接非标准错误响应。
