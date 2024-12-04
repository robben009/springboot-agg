package com.robben.agg.cola.stateMachine.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class OrderEventRecord implements Serializable {


    private String orderId;

    /**
     * 起始状态
     */
    private String fromStatus;

    /**
     * 目标状态
     */
    private String toStatus;

    /**
     * 事件名
     */
    private String eventCode;

    /**
     * 事件结果，成功or失败
     */
    private Boolean eventResult;

    /**
     * 发起事件人ID
     */
    private String createBy;

    /**
     * 发起事件时间
     */
    private Date createDate;

}