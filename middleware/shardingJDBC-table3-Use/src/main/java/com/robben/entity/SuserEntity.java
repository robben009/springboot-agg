package com.robben.entity;

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

    private Long id;

    private String name;

}
