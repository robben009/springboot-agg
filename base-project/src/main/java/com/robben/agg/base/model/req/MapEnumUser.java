/*
 * Copyright (C) 2022 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */
package com.robben.agg.base.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class MapEnumUser {

    @Schema(description = "姓名",allowableValues = "张飞,关羽,赵云")
    private String name;

    @Schema(description = "枚举类型")
    private CourseTypeEnum courseTypeEnum;
}
