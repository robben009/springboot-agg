package com.hjz.flowlong.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 资源表：存储页面和按钮资源，支持父子层级关系
 */
@Data
@TableName("sys_resource")
public class SysResource implements Serializable {

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 资源名称 */
    private String name;

    /** 资源类型：1-页面，2-按钮 */
    private Integer type;

    /** 父资源ID，0表示顶级资源 */
    private Long parentId;

    /** 资源路径，页面为路由地址，按钮为接口地址或权限标识 */
    private String path;

    /** 排序号，数值越小越靠前 */
    private Integer sortOrder;

    /** 状态：0-禁用，1-启用 */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createdAt;

    /** 更新时间 */
    private LocalDateTime updatedAt;
}
