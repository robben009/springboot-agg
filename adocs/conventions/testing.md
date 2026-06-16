---
last_updated: 2026-03-28
status: active          # active | deprecated | draft
owner: @Jack
---

# 测试规范

## 基本要求

新增代码必须包含对应 JUnit 5 测试，行覆盖率不低于 80%。

## 测试类型

### 单元测试

适用于：

- service 业务规则。
- 工具类。
- 参数转换。
- 错误分支。

单元测试应尽量避免启动完整 Spring 容器。

### Web 层测试

适用于 controller 参数校验、路由和响应结构验证。可以使用 Spring MVC 测试工具。

### 数据访问测试

适用于 mapper SQL、分页、条件查询和数据转换验证。测试 SQL 需要兼容 MySQL 5.7。

## 测试命名

测试类命名为 `XxxTest`。测试方法建议表达场景和结果：

```java
void updateStatus_shouldThrowException_whenProjectNotFound()
```

## 覆盖重点

- 正常流程。
- 参数非法。
- 数据不存在。
- 权限不足。
- 外部服务失败。
- 事务回滚。

## 断言要求

测试必须包含明确断言。禁止只调用方法而不验证结果。

## 测试数据

测试数据应在测试内部或测试 fixture 中构造，避免依赖本地开发库中的脏数据。
