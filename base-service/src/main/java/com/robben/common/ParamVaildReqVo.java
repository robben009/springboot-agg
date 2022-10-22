package com.robben.common;

import com.robben.annotation.validParam.EnumValidAnnotation;
import com.robben.annotation.ProductTypeEnum;
import com.robben.model.valid.ParamValidAdd;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 常用的标签有
 * @Null 被注释的元素必须为null
 * @NotNull 被注释的元素不能为null
 * @AssertTrue 被注释的元素必须为true
 * @AssertFalse 被注释的元素必须为false
 * @Min(value) 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
 * @Max(value) 被注释的元素必须是一个数字，其值必须小于等于指定的最大值
 * @DecimalMin(value) 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
 * @DecimalMax(value) 被注释的元素必须是一个数字，其值必须小于等于指定的最大值
 * @Size(max,min) 被注释的元素的大小必须在指定的范围内。
 * @Digits(integer,fraction) 被注释的元素必须是一个数字，其值必须在可接受的范围内
 * @Past 被注释的元素必须是一个过去的日期
 * @Future 被注释的元素必须是一个将来的日期
 * @Pattern(value) 被注释的元素必须符合指定的正则表达式。
 * @Email 被注释的元素必须是电子邮件地址
 * @Length 被注释的字符串的大小必须在指定的范围内
 * @NotEmpty 被注释的字符串必须非空
 * @Range 被注释的元素必须在合适的范围内
 */

@Data
public class ParamVaildReqVo {

    @NotNull(message = "年龄不能为空",groups = ParamValidAdd.class)
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "名字")
    private String name;

    @EnumValidAnnotation(message = "商品类型输入错误",target = ProductTypeEnum.class )
    @ApiModelProperty(value = "类型")
    private String type;


}
