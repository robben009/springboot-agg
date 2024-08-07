package com.robben.agg.dubbo.api;


import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/3/9 19:54
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentVo implements Serializable {

    private Integer age;
    private String name;
    private JSONObject extInfo;

}
