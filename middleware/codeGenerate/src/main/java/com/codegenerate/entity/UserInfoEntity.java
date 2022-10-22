package com.codegenerate.entity;

import java.util.Date;
import lombok.Data;
                
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (user_info)实体类
 *
 * @author hjz
 * @since 2022-01-06 11:30:11
 */
@Data
@TableName("user_info")
public class UserInfoEntity {

    /**
    * 主键
    */private Long id;

    /**
    * 姓名
    */private String name;

    /**
    * 创建时间
    */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;


}