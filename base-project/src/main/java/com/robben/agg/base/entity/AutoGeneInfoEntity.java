package com.robben.agg.base.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * (AutoGeneInfo)实体类
 *
 * @author xiaoG
 * @since 2020-10-25 17:46:54
 */
@Data
public class AutoGeneInfoEntity {

    private Integer id;

    /**
     * 姓名
     */
    private String name;


    private Boolean sex;


    private Integer age;


    private String workInfo;


    private Integer typeFlag;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //前端到后端的格式转换--接收前台数据
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")   //DB到前端的格式转换,注意是否需要增加时区timezone="GMT+8"
    private Date createTime;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


}