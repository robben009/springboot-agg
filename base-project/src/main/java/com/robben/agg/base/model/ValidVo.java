package com.robben.agg.base.model;

import com.robben.annotation.validParam.anno.ValidGroup;
import com.robben.entity.UserInfoEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;


/**
 * @AssertFalse 可以为null, 如果不为null的话必须为false
 * @AssertTrue 可以为null, 如果不为null的话必须为true
 * @DecimalMax 设置不能超过最大值
 * @DecimalMin 设置不能超过最小值
 * @Digits 设置必须是数字且数字整数的位数和小数的位数必须在指定范围内
 * @Future 日期必须在当前日期的未来
 * @Past 日期必须在当前日期的过去
 * @Max 最大不得超过此最大值
 * @Min 最大不得小于此最小值
 * @NotNull 不能为null，可以是空
 * @Null 必须为null
 * @Pattern 必须满足指定的正则表达式
 * @Size 集合、数组、map等的size()值必须在指定范围内
 * @Email 必须是email格式
 * @Length 长度必须在指定范围内
 * @NotBlank 字符串不能为null, 字符串trim()后也不能等于“”
 * @NotEmpty 不能为null，集合、数组、map等size()不能为0；字符串trim()后可以等于“”
 * @Range 值必须在指定范围内
 * @URL 必须是一个URL
 * ————————————————
 * 版权声明：本文为CSDN博主「飘渺Jam」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/jianzhang11/article/details/119632467
 */

@Data
public class ValidVo {
    private String id;

    @Length(min = 6,max = 12,message = "appId长度必须位于6到12之间")
    private String appId;

    @NotBlank(message = "名字为必填项")
    private String name;

    @Email(message = "请填写正确的邮箱地址")
    private String email;

    @NotEmpty(message = "级别不能为空")
    private String level;

    private String type;

    //分组有个弊端,其他的不能校验,所以其分组必须继承默认的分组,才能保持实现功能
    @Null(groups = ValidGroup.noParam.class, message = "param必须为空")
    @NotNull(groups = ValidGroup.param.class, message = "param不能为空")
    private String param;

    @NotEmpty(message = "list不能为空")
    private List<String> list;

    //若需要校验嵌套的对象中的属性,则需要加上@Valid
    @Valid
    @NotEmpty(message = "list不能为空")
    private UserInfoEntity userInfoEntity;


}

