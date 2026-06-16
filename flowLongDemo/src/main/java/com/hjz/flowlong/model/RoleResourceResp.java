package com.hjz.flowlong.model;

import lombok.Data;

import java.util.List;

/**
 * 角色资源响应对象
 */
@Data
public class RoleResourceResp {

    /** 资源ID */
    private Long id;

    /** 资源名称 */
    private String name;

    /** 资源类型：1-页面，2-按钮 */
    private Integer type;

    /** 子资源列表 */
    private List<RoleResourceResp> children;
}
