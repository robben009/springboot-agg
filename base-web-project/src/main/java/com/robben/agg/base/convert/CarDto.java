package com.robben.agg.base.convert;

import lombok.Data;

import java.util.Date;

/**
 * Description： TODO
 * Author: robben
 * Date: 2020/8/14 11:03
 */

@Data
public class CarDto {

    private Integer id;
    private String name;
    private String color;
    private Date createTime;

}
