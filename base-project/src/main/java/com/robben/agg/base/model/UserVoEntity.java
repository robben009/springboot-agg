package com.robben.agg.base.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.robben.agg.base.annotation.validParam.anno.MyNotNull;
import com.robben.agg.base.enums.CommonErrorCodeEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserVoEntity implements Serializable {

    @MyNotNull(codeEnum = CommonErrorCodeEnum.P_Common_ParamMissing)
    private Integer id;

    private String name;

    private Integer age;

    private Boolean sex;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

//    前后到后台的时间格式的转换(作为对象入参的时候传唤)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
//    后台到前台的时间格式的转换
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
