---
last_updated: 2026-03-28
status: active          # active | deprecated | draft
owner: @Jack
---

# 模块边界和依赖规则

## 依赖方向

代码依赖必须保持单向：

```text
domain -> config -> mapper -> service -> controller
```

禁止 controller 直接访问 mapper，禁止 mapper 依赖 service，禁止 domain 依赖 Spring Web、MyBatis、数据库或 HTTP 框架。

## domain

domain 层表达业务核心概念。

允许：

- 实体类、值对象、枚举、常量。
- 与框架弱相关的基础校验逻辑。

禁止：

- 注入 Spring Bean。
- 访问数据库。
- 发起 HTTP 调用。
- 读取环境变量或配置文件。

## config

config 层负责组件装配和框架配置。

允许：

- Spring `@Configuration`。
- Bean 定义。
- 拦截器、过滤器、切面、序列化配置。
- `ApiClient`、日志、认证、遥测等横切能力装配。

禁止：

- 承载业务流程。
- 直接写复杂 SQL。

## mapper

mapper 层负责数据库访问。

允许：

- MyBatis-Plus Mapper 接口。
- MyBatis XML。
- 简单的数据读写转换。

禁止：

- 写业务编排逻辑。
- 调用 service。
- 处理 HTTP 请求或响应对象。

## service

service 层是业务用例入口。

允许：

- 编排多个 mapper。
- 定义事务边界。
- 调用领域逻辑。
- 调用 `ApiClient` 访问外部服务。

禁止：

- 直接依赖 controller。
- 输出 Web 层响应结构。
- 绕过 `ApiClient` 直接创建 HTTP 客户端。

## controller

controller 层只处理 HTTP 协议边界。

允许：

- 参数接收和校验。
- 调用 service。
- 统一响应封装。

禁止：

- 写业务规则。
- 直接调用 mapper。
- 手动处理数据库事务。

新建的接口方法必须增加中文注释说明

## 注入规则

禁止字段级 `@Autowired`，必须使用构造器注入。推荐使用 Lombok `@RequiredArgsConstructor`。

错误示例：

```java
@Autowired
private UserService userService;
```

推荐示例：

```java
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
}
```
