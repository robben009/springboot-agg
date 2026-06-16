# 架构总览

## 包结构

```
src/main/java/com/example/taskapp/
├── domain/         # 领域模型与 DTO（不依赖任何业务包）
│   ├── model/      # MyBatis-Plus Entity（@TableName / @TableId / @TableField）/ Value Object
│   └── dto/        # Request / Response（Lombok @Value 不可变 POJO）
├── config/         # Spring 配置类、@ConfigurationProperties、@MapperScan、MybatisPlusInterceptor
├── mapper/         # MyBatis-Plus Mapper 接口（extends BaseMapper<T>）
├── service/        # 业务逻辑（@RequiredArgsConstructor 构造器注入）
├── controller/     # REST Controller、@ControllerAdvice 全局异常处理
└── infrastructure/ # 横切关注点：ApiClient、日志、指标、安全
```

## 依赖规则
- domain          → 不依赖任何业务包
- config          → 仅依赖 domain
- mapper          → 仅依赖 domain、config、infrastructure
- service         → 仅依赖 domain、config、mapper、infrastructure
- controller      → 仅依赖 domain、config、service、infrastructure
- controller      → **禁止**直接依赖 mapper
- infrastructure  → 仅依赖 domain、config

## Bean 装配
- 严格构造器注入（禁用字段级 @Autowired）
- 推荐使用 Lombok @RequiredArgsConstructor + private final 字段

## DTO 风格（Java 8 没有 record）
```java
import lombok.Value;

@Value
public class UserResponse {
    Long id;
    String name;
    String email;
}
```
