```sql
CREATE TABLE `user_info`
(
    `id`             bigint NOT NULL AUTO_INCREMENT,
    `name`           varchar(255) DEFAULT NULL,
    `sex`            tinyint      DEFAULT NULL,
    `age`            int          DEFAULT NULL,
    `groupInfo`      int          DEFAULT NULL,
    `descInfo`       json         DEFAULT NULL,
    `descInfoListVo` json         DEFAULT NULL,
    `descInfoList`   json         DEFAULT NULL,
    `createTime`     datetime     DEFAULT NULL,
    `updateTime`     datetime     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
```

