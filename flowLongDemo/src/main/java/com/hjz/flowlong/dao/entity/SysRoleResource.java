package com.hjz.flowlong.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色资源关系表：记录每个角色拥有哪些资源的访问权限
 */
@Data
@TableName("sys_role_resource")
public class SysRoleResource implements Serializable {

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 角色ID */
    private Long roleId;

    /** 资源ID */
    private Long resourceId;

    /** 创建时间 */
    private LocalDateTime createdAt;
}
