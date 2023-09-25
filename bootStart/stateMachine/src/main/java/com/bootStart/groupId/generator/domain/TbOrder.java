package com.bootStart.groupId.generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 订单表
 * @TableName tb_order
 */
@TableName(value ="tb_order")
@Data
public class TbOrder implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单编码
     */
    private String orderCode;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 订单名称
     */
    private String name;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 删除标记，0未删除  1已删除
     */
    private Integer deleteFlag;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String createUserCode;

    /**
     * 更新人
     */
    private String updateUserCode;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 备注
     */
    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}