-- 资源表：存储页面和按钮资源，支持父子层级关系
CREATE TABLE `sys_resource` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` VARCHAR(100) NOT NULL COMMENT '资源名称',
  `type` TINYINT NOT NULL COMMENT '资源类型：1-页面，2-按钮',
  `parent_id` BIGINT NOT NULL DEFAULT 0 COMMENT '父资源ID，0表示顶级资源',
  `path` VARCHAR(200) DEFAULT NULL COMMENT '资源路径，页面为路由地址，按钮为接口地址或权限标识',
  `sort_order` INT NOT NULL DEFAULT 0 COMMENT '排序号，数值越小越靠前',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源表';

-- 角色资源关系表：记录每个角色拥有哪些资源的访问权限
CREATE TABLE `sys_role_resource` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `resource_id` BIGINT NOT NULL COMMENT '资源ID',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_resource` (`role_id`, `resource_id`),
  KEY `idx_resource_id` (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色资源关系表';