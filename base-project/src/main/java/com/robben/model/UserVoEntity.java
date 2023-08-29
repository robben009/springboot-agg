//package com.robben.model;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.robben.annotation.validParam.self.CommonErrorCodeEnum;
//import com.robben.annotation.validParam.self.NotNull;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import java.io.Serializable;
//import java.util.Date;
//
//@Data
//public class UserVoEntity implements Serializable {
//
//    @ApiModelProperty(value = "主键的ID")
//    @NotNull(codeEnum = CommonErrorCodeEnum.P_Common_ParamMissing)
//    private Integer id;
//
//    @ApiModelProperty(value = "名字")
//    private String name;
//
//    @ApiModelProperty(value = "年龄")
//    private Integer age;
//
//    @ApiModelProperty(value = "年龄")
//    private Boolean sex;
//
//    @ApiModelProperty(value = "创建时间")
//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
//    private Date createTime;
//
//    @ApiModelProperty(value = "更新时间")
////    前后到后台的时间格式的转换(作为对象入参的时候传唤)
//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
////    后台到前台的时间格式的转换
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
//    private Date updateTime;
//
//}
