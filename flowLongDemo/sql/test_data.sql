-- ==========================================
-- 测试数据 - 用户、角色、资源及相关关联表
-- 数据库: flowlong
-- ==========================================

-- ---------- 用户数据 ----------
INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `status`) VALUES
(1, 'admin',    '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '系统管理员',   'admin@example.com',    '13800000001', 1),
(2, 'zhangsan', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '张三',       'zhangsan@example.com', '13800000002', 1),
(3, 'lisi',     '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '李四',       'lisi@example.com',     '13800000003', 1),
(4, 'wangwu',   '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '王五',       'wangwu@example.com',   '13800000004', 1),
(5, 'zhaoliu',  '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '赵六',       'zhaoliu@example.com',  '13800000005', 0);

-- ---------- 角色数据 ----------
INSERT INTO `sys_role` (`id`, `role_code`, `role_name`, `description`, `status`) VALUES
(1, 'ROLE_ADMIN',       '超级管理员', '拥有系统全部权限',                       1),
(2, 'ROLE_MANAGER',     '部门经理',   '负责审批流程管理，可查看部门所有数据',     1),
(3, 'ROLE_EMPLOYEE',    '普通员工',   '发起流程、查看个人相关数据',              1),
(4, 'ROLE_HR',          '人事专员',   '负责人事相关流程审批',                   1),
(5, 'ROLE_FINANCE',     '财务专员',   '负责财务相关流程审批',                   1);

-- ---------- 用户角色关联数据 ----------
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`) VALUES
(1, 1, 1),
(2, 2, 2),
(3, 2, 3),
(4, 3, 3),
(5, 4, 4),
(6, 5, 5);

-- ---------- 资源数据 ----------
-- 顶级页面资源
INSERT INTO `sys_resource` (`id`, `name`, `type`, `parent_id`, `path`, `sort_order`, `status`) VALUES
(1,  '首页',       1, 0, '/dashboard',          1, 1),
(2,  '流程管理',   1, 0, '/process',            2, 1),
(3,  '审批中心',   1, 0, '/approval',           3, 1),
(4,  '人事管理',   1, 0, '/hr',                 4, 1),
(5,  '财务管理',   1, 0, '/finance',            5, 1),
(6,  '系统设置',   1, 0, '/settings',           6, 1);

-- 流程管理子页面
INSERT INTO `sys_resource` (`id`, `name`, `type`, `parent_id`, `path`, `sort_order`, `status`) VALUES
(10, '我的流程',   1, 2, '/process/my',         1, 1),
(11, '流程定义',   1, 2, '/process/definition', 2, 1),
(12, '流程监控',   1, 2, '/process/monitor',    3, 1);

-- 审批中心子页面
INSERT INTO `sys_resource` (`id`, `name`, `type`, `parent_id`, `path`, `sort_order`, `status`) VALUES
(20, '待我审批',   1, 3, '/approval/pending',   1, 1),
(21, '我已审批',   1, 3, '/approval/approved',  2, 1),
(22, '我发起的',   1, 3, '/approval/launched',  3, 1);

-- 人事管理子页面
INSERT INTO `sys_resource` (`id`, `name`, `type`, `parent_id`, `path`, `sort_order`, `status`) VALUES
(30, '请假管理',   1, 4, '/hr/leave',           1, 1),
(31, '出差管理',   1, 4, '/hr/travel',          2, 1),
(32, '转正申请',   1, 4, '/hr/regular',         3, 1);

-- 财务管理子页面
INSERT INTO `sys_resource` (`id`, `name`, `type`, `parent_id`, `path`, `sort_order`, `status`) VALUES
(40, '报销管理',   1, 5, '/finance/reimburse',  1, 1),
(41, '付款申请',   1, 5, '/finance/payment',    2, 1);

-- 系统设置子页面
INSERT INTO `sys_resource` (`id`, `name`, `type`, `parent_id`, `path`, `sort_order`, `status`) VALUES
(50, '用户管理',   1, 6, '/settings/user',      1, 1),
(51, '角色管理',   1, 6, '/settings/role',      2, 1),
(52, '资源管理',   1, 6, '/settings/resource',  3, 1);

-- 按钮资源（属于各个页面）
INSERT INTO `sys_resource` (`id`, `name`, `type`, `parent_id`, `path`, `sort_order`, `status`) VALUES
(100, '发起流程',   2, 10, '/process/start',      1, 1),
(101, '撤销流程',   2, 10, '/process/cancel',     2, 1),
(102, '新增流程定义', 2, 11, '/process/def/add',   1, 1),
(103, '编辑流程定义', 2, 11, '/process/def/edit',  2, 1),
(104, '删除流程定义', 2, 11, '/process/def/delete',3, 1),
(105, '查看流程详情', 2, 10, '/process/detail',    3, 1),
(200, '通过审批',   2, 20, '/approval/pass',      1, 1),
(201, '驳回审批',   2, 20, '/approval/reject',    2, 1),
(202, '转交审批',   2, 20, '/approval/transfer',  3, 1),
(300, '新增请假',   2, 30, '/hr/leave/add',       1, 1),
(301, '删除请假',   2, 30, '/hr/leave/delete',    2, 1),
(302, '审批请假',   2, 30, '/hr/leave/approve',   3, 1),
(400, '新增报销',   2, 40, '/finance/add',        1, 1),
(401, '审批报销',   2, 40, '/finance/approve',    2, 1),
(500, '新增用户',   2, 50, '/settings/user/add',  1, 1),
(501, '编辑用户',   2, 50, '/settings/user/edit', 2, 1),
(502, '禁用用户',   2, 50, '/settings/user/disable',3,1),
(503, '删除用户',   2, 50, '/settings/user/delete',4, 1);

-- ---------- 角色资源关联数据 ----------
-- 超级管理员拥有全部资源
INSERT INTO `sys_role_resource` (`role_id`, `resource_id`)
SELECT 1, id FROM `sys_resource`;

-- 部门经理：首页、流程管理全部、审批中心全部、人事管理（查看）、财务管理（查看）
INSERT INTO `sys_role_resource` (`role_id`, `resource_id`) VALUES
(2, 1),   -- 首页
(2, 2),   -- 流程管理
(2, 10),  -- 我的流程
(2, 11),  -- 流程定义
(2, 12),  -- 流程监控
(2, 100), -- 发起流程
(2, 101), -- 撤销流程
(2, 105), -- 查看流程详情
(2, 3),   -- 审批中心
(2, 20),  -- 待我审批
(2, 21),  -- 我已审批
(2, 22),  -- 我发起的
(2, 200), -- 通过审批
(2, 201), -- 驳回审批
(2, 202), -- 转交审批
(2, 4),   -- 人事管理
(2, 30),  -- 请假管理
(2, 31),  -- 出差管理
(2, 32),  -- 转正申请
(2, 5),   -- 财务管理
(2, 40),  -- 报销管理
(2, 41);  -- 付款申请

-- 普通员工：首页、我的流程、审批中心（我发起的）、请假/报销发起
INSERT INTO `sys_role_resource` (`role_id`, `resource_id`) VALUES
(3, 1),   -- 首页
(3, 2),   -- 流程管理
(3, 10),  -- 我的流程
(3, 100), -- 发起流程
(3, 101), -- 撤销流程
(3, 105), -- 查看流程详情
(3, 3),   -- 审批中心
(3, 22),  -- 我发起的
(3, 4),   -- 人事管理
(3, 30),  -- 请假管理
(3, 300), -- 新增请假
(3, 5),   -- 财务管理
(3, 40),  -- 报销管理
(3, 400); -- 新增报销

-- 人事专员：首页、审批中心、人事管理全部（含审批）
INSERT INTO `sys_role_resource` (`role_id`, `resource_id`) VALUES
(4, 1),   -- 首页
(4, 3),   -- 审批中心
(4, 20),  -- 待我审批
(4, 21),  -- 我已审批
(4, 22),  -- 我发起的
(4, 200), -- 通过审批
(4, 201), -- 驳回审批
(4, 4),   -- 人事管理
(4, 30),  -- 请假管理
(4, 31),  -- 出差管理
(4, 32),  -- 转正申请
(4, 300), -- 新增请假
(4, 301), -- 删除请假
(4, 302); -- 审批请假

-- 财务专员：首页、审批中心、财务管理全部（含审批）
INSERT INTO `sys_role_resource` (`role_id`, `resource_id`) VALUES
(5, 1),   -- 首页
(5, 3),   -- 审批中心
(5, 20),  -- 待我审批
(5, 21),  -- 我已审批
(5, 22),  -- 我发起的
(5, 200), -- 通过审批
(5, 201), -- 驳回审批
(5, 5),   -- 财务管理
(5, 40),  -- 报销管理
(5, 41),  -- 付款申请
(5, 400), -- 新增报销
(5, 401); -- 审批报销