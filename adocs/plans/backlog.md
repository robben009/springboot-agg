---
last_updated: 2026-03-28
status: active          # active | deprecated | draft
owner: @Jack
---

# Backlog

## 工程基础

- 初始化 Spring Boot 2.7.x 工程骨架。
- 固定 JDK 1.8 和 Maven 3.6.3。
- 配置 Maven enforcer。
- 配置 JUnit 5。
- 配置覆盖率统计，行覆盖率门槛不低于 80%。

## 数据库

- 将 `sql/ry_20260319.sql` 拆分为 Flyway 迁移脚本。
- 为核心查询补充索引。
- 确认 MySQL 5.7 utf8mb4 字符集配置。
- 建立本地数据库初始化说明。

## 架构治理

- 增加依赖方向检查。
- 禁止字段级 `@Autowired`。
- 禁止 `System.out.println` 和 `e.printStackTrace()`。
- 禁止直接裸用 `RestTemplate` 和 `HttpURLConnection`。

## 功能模块

- 认证授权模块。
- 项目管理模块。
- 搜索模块。
- 计费模块。
- 审计日志模块。

## 文档

- 补充 API 示例。
- 补充错误码变更流程。
- 补充本地启动说明。
- 补充发布和回滚流程。
