package com.robben.controller;


import com.robben.mapper.SuserMapper;
import com.robben.entity.SuserEntity;
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
@Api(tags = "用户服务", value="用户服务2")
@RestController
@RequestMapping("/feature1")
public class SuserController {

    @Autowired
    private SuserMapper userMapper;


    @ApiOperation("存储用户")
    @GetMapping("/user/save")
    public String save() {
        for (int i = 0; i <2 ; i++) {
            SuserEntity user=new SuserEntity();
            user.setName("test"+i);
            userMapper.insert(user);
        }
        return "success";
    }


    @ApiOperation("获取用户")
    @GetMapping("/user/get")
    public SuserEntity get(@RequestParam Long id) {
        SuserEntity user =  userMapper.selectById(id);
        System.out.println(user.getId());
        return user;
    }
}