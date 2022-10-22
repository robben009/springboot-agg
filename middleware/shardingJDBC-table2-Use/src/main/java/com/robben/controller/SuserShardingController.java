package com.robben.controller;


import com.robben.mapper.SuserShardingMapper;
import com.robben.entity.SuserShardingEntity;
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
@Api(tags = "分表用户服务", value="用户服务2")
@RestController
@RequestMapping("/feature2")
public class SuserShardingController {

    @Autowired
    private SuserShardingMapper suserShardingMapper;


    @ApiOperation("分表存储用户")
    @GetMapping("/user/save")
    public String save() {
        for (int i = 0; i <2 ; i++) {
            SuserShardingEntity user = new SuserShardingEntity();
            user.setName("test"+i);
            user.setShardingIndex(123l + i);
            suserShardingMapper.insert(user);
        }
        return "success";
    }


    @ApiOperation("分表获取用户")
    @GetMapping("/user/get")
    public SuserShardingEntity get(@RequestParam Long id) {
        SuserShardingEntity user =  suserShardingMapper.selectById(id);
        System.out.println(user.getId());
        return user;
    }

}