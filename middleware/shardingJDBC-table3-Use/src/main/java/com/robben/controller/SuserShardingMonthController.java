package com.robben.controller;


import com.robben.entity.SuserShardingMonthEntity;
import com.robben.mapper.SuserShardingMonthMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/12/2 10:04
 */
@Tag(name = "分表用户服务2")
@RestController
@RequestMapping("/feature3")
public class SuserShardingMonthController {

    @Autowired
    private SuserShardingMonthMapper suserShardingMonthMapper;



    @Operation("分库分表存储用户")
    @GetMapping("/user/save")
    public String save() {
        for (long i = 0; i <2; i++) {
            SuserShardingMonthEntity user = new SuserShardingMonthEntity();
            user.setName("test"+i);
            user.setShardingIndex(i);
            suserShardingMonthMapper.insert(user);
        }
        return "success";
    }


    @Operation("分库分表获取用户")
    @GetMapping("/user/get")
    public SuserShardingMonthEntity get(@RequestParam Long id) {
        return suserShardingMonthMapper.selectById(id);
    }


}