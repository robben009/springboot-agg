---
last_updated: 2026-03-28
status: active          # active | deprecated | draft
owner: @Jack
---

# 编码规范总览

## 基本原则

- 遵守 Java 1.8 语法，不使用 Java 9+ 特性。
- 遵守 Spring Boot 2.7.x 生态，不升级到 Spring Boot 3.x。
- 使用 MyBatis-Plus 和 Flyway，不引入 JPA / Hibernate。
- 保持代码短小、可测试、可审查。

## 分层约束

依赖方向固定为：

```text
domain -> config -> mapper -> service -> controller
```

任何新增代码都需要放入正确层级，不允许为了方便跨层调用。

## 代码规模

- 单个 `.java` 文件不超过 300 行。
- 单个方法不超过 50 行。
- 超出限制时优先拆分职责，而不是压缩可读性。

## 依赖注入

- 禁止字段级 `@Autowired`。
- 必须使用构造器注入。
- 推荐使用 Lombok `@RequiredArgsConstructor`。

## 日志

- 禁止 `System.out.println`。
- 禁止 `e.printStackTrace()`。
- 统一使用 SLF4J `Logger`。

## 外部调用

禁止直接裸用 `RestTemplate` 或 `HttpURLConnection`。所有外部 HTTP 调用必须通过统一 `ApiClient` 抽象。

## 测试

新增代码必须补充 JUnit 5 测试，行覆盖率不低于 80%。测试命名和边界要求见 `docs/conventions/testing.md`。

## 提交信息

提交前缀使用：

- `feat:` 新功能
- `fix:` 修复
- `refactor:` 重构
- `docs:` 文档
- `test:` 测试
