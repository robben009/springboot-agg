package com.robben.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/12/2 10:06
 */
@Data
@TableName("suser")
public class SuserEntity {

//    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String name;

}
