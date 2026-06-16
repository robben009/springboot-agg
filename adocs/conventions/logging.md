---
last_updated: 2026-03-28
status: active          # active | deprecated | draft
owner: @Jack
---

# 日志规范

## 基本要求

统一使用 SLF4J `Logger`。禁止使用 `System.out.println` 和 `e.printStackTrace()`。

## Logger 定义

推荐 Lombok：

```java
@Slf4j
public class ProjectServiceImpl {
}
```

或显式定义：

```java
private static final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);
```

## 日志级别

- `debug`: 开发排查信息，不用于生产常规观察。
- `info`: 关键业务状态变化和重要流程节点。
- `warn`: 可恢复异常、外部服务短暂失败、业务风险。
- `error`: 系统异常、不可恢复错误、数据一致性风险。

## 记录内容

日志应包含可定位问题的上下文，例如业务 ID、用户 ID、请求 ID、外部服务名称。

推荐：

```java
log.warn("Create project rejected, userId={}, reason={}", userId, reason);
```

## 敏感信息

禁止记录以下信息：

- 明文密码。
- token、session、密钥。
- 身份证号、银行卡号。
- 完整手机号、完整邮箱。
- 第三方接口完整响应中的敏感字段。

## 异常日志

记录异常时应传入异常对象：

```java
log.error("Create project failed, userId={}", userId, ex);
```
