package com.hjz.flowlong.model;

import lombok.Data;

@Data
public class ApprovalRequest {
    private String userId;
    private String userName;
    private Long taskId;
    private String action;
    private String remark;
}
