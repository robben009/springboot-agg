---
last_updated: 2026-03-28
status: active          # active | deprecated | draft
owner: @Jack
---

# 命名规范

## 包命名

包名全部小写，按业务模块和分层组织。

推荐结构：

```text
com.example.project
  .domain
  .config
  .mapper
  .service
  .controller
```

## 类命名

- controller: `XxxController`
- service 接口: `XxxService`
- service 实现: `XxxServiceImpl`
- mapper: `XxxMapper`
- request DTO: `XxxRequest`
- response DTO: `XxxResponse`
- 配置类: `XxxConfig`
- 异常类: `XxxException`
- 枚举类: `XxxEnum`

## 方法命名

方法名使用动宾结构，表达明确意图。

推荐：

- `createProject`
- `updateProjectStatus`
- `listProjects`
- `getProjectDetail`
- `deleteProject`

避免：

- `doIt`
- `handle`
- `processData`
- `deal`

## 数据库命名

- 表名使用小写下划线，例如 `sys_user`。
- 字段名使用小写下划线，例如 `create_time`。
- 主键建议使用 `id` 或带业务前缀的 `xxx_id`。
- 状态字段应配套注释说明枚举含义。

## 测试命名

测试类命名为 `XxxTest`。测试方法表达场景和期望。

示例：

```java
void createProject_shouldReturnProjectId_whenRequestIsValid()
```
