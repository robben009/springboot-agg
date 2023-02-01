package com.robben.sharding.controller;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/11/16 17:01
 */

import cn.hutool.core.date.SystemClock;
import com.robben.sharding.dao.UserMapper;
import com.robben.sharding.entity.UserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "用户服务", value="用户服务2")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Operation("创建表")
    @GetMapping("/createTable")
    public boolean createTable() {
        userMapper.createTable();
        return true;
    }


    @Operation("存储用户")
    @GetMapping("/add")
    public boolean add() {
        UserEntity u = new UserEntity();
        u.setSId(SystemClock.now());
        u.setName("haha");
        userMapper.insert(u);
        return true;
    }


    @GetMapping("/get")
    public UserEntity get(@RequestParam Long id) {
        return userMapper.selectById(id);
    }


}
