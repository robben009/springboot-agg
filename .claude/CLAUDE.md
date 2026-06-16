# AGENTS.md

## 项目简介
[一句话] 基于 Spring Boot + Java  + MySQL 项目。

## 技术栈基线（不允许擅自升级）
- JDK: 21，不可使用 Java 9+ 语法（record/var/text blocks）
- Spring Boot: 2.7.x，不可升 3.x（Spring 6 要求 JDK 17）
- Maven: 3.6.3，由 enforcer 强制
- 数据库: MySQL 5.7（utf8mb4），不可升 8.x（生产环境为 5.7）
- 持久化: MyBatis-Plus 3.5.x（基于 MyBatis 3.5）+ Flyway（含 flyway-mysql 子模块），不引入 JPA / Hibernate

## 快速导航
| 你想做什么       | 去哪里看 |
|-------------|---------|
| 了解系统架构      | adocs/architecture/overview.md |
| 了解模块边界和依赖规则 | adocs/architecture/boundaries.md |
| 了解编码规范      | adocs/conventions/README.md |
| 了解当前迭代任务    | adocs/plans/current-sprint.md |
| 了解 API 规范   | adocs/reference/api-spec.yaml |
| 了解错误码       | adocs/reference/error-codes.md |
| 了解测试规范      | adocs/conventions/testing.md |

## 硬性规则（必须遵守，CI 会验证）
1. 依赖方向：domain → config → mapper → service → controller
2. 横切关注点（auth/log/telemetry）只能通过 Spring 注入，禁止 `new` 实例化
3. 单文件（.java） ≤ 300 行；单方法 ≤ 50 行
4. 禁止 `System.out.println` / `e.printStackTrace()`，统一使用 SLF4J `Logger`
5. 禁止裸 `RestTemplate` / `HttpURLConnection`，统一通过 `ApiClient` 抽象
6. 禁止字段级 `@Autowired`，必须构造器注入（推荐 Lombok `@RequiredArgsConstructor`）
7. 新增代码必须有对应 JUnit 5 测试，行覆盖率 ≥ 80%

## 提交规范
- feat: 新功能
- fix: 修复
- refactor: 重构
- adocs: 文档
- test: 测试
  但暂时没有git,所以不需要提交只需保存在本地即可

## 工作流参考文档
如果是在flowLongDemo项目中询问关于flowLong相关的问题,可以参考在/Users/hjz/out-charge/flowlong
查看对应的源码信息

## 回答规范
尽可能使用中文回答问题