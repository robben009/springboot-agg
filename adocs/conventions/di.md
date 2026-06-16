---
last_updated: 2026-03-28
status: active          # active | deprecated | draft
owner: @Jack
---

# 依赖注入规范

## 基本规则

禁止字段级 `@Autowired`，统一使用构造器注入。推荐使用 Lombok `@RequiredArgsConstructor`。

## 推荐写法

```java
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
}
```

## 禁止写法

```java
public class UserService {
    @Autowired
    private UserMapper userMapper;
}
```

## 原因

构造器注入能让依赖关系在对象创建时完整表达，便于单元测试，也能避免运行期才发现 Bean 缺失。

## 校验

架构测试位于 `src/test/java/com/example/app/architecture/LayerDependencyTest.java`，通过 ArchUnit 检查字段级 `@Autowired`。
