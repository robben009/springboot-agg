---
last_updated: 2026-03-28
status: active          # active | deprecated | draft
owner: @Jack
---

# 数据流说明

## 请求处理链路

典型 HTTP 请求的数据流如下：

```text
Client
  -> Controller
  -> Service
  -> Mapper
  -> MySQL
```

## Controller 输入

controller 接收请求参数，负责基础格式校验和协议适配。controller 不直接访问数据库，不承载业务分支。

输入对象建议按场景拆分，例如：

- `CreateProjectRequest`
- `UpdateProjectRequest`
- `ProjectQueryRequest`

## Service 编排

service 负责业务规则、事务边界和跨资源编排。需要写入多个表时，事务注解应放在 service 方法上。

service 对外部系统的调用必须通过 `ApiClient` 抽象，避免业务代码绑定具体 HTTP 实现。

## Mapper 访问

mapper 使用 MyBatis-Plus 和 MyBatis XML 访问 MySQL。复杂 SQL 优先放入 XML，并为关键查询补充单元测试或集成测试。

## 响应输出

controller 将 service 返回的业务结果转换为 API 响应。错误响应必须使用统一错误码，不允许直接向前端暴露堆栈信息。

## 日志和追踪

请求入口、关键业务状态变化、外部调用失败、异常分支需要记录结构化日志。日志中禁止输出明文密码、token、密钥、身份证号等敏感信息。
