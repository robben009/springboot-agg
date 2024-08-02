/*
 * Copyright (C) 2022 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */
package com.robben.agg.base.contants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @since:knife4j-spring-boot27-demo
 * @auth <a href="xiaoymin@foxmail.com">xiaoymin@foxmail.com</a>
 * 2022/12/10 16:19
 */
@Getter
@AllArgsConstructor
public enum CourseTypeEnum {
    /**
     * 图文
     */
    PICTURE(102, "图文"),
    /**
     * 音频
     */
    AUDIO(103, "音频"),
    /**
     * 视频
     */
    VIDEO(104, "视频"),
    /**
     * 外链
     */
    URL(105, "外链");

    private final int type;
    private final String desc;

    @Override
    public String toString() {
        return this.type+":"+this.desc;
    }
}
