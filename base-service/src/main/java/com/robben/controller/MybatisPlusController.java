package com.robben.controller;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.robben.common.ResponseEntityDto;
import com.robben.common.UnifiedReply;
import com.robben.dao.mapper.UserInfoMapper;
import com.robben.dao.service.UserInfoService;
import com.robben.entity.UserInfoEntity;
import com.robben.service.MpUseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Api(tags = "mybatis-Plus使用")
@RestController
@RequestMapping("/mybatisPlus")
public class MybatisPlusController extends UnifiedReply {
    @Autowired
    private MpUseService mpUseService;
    @Autowired
    private UserInfoService userInfoService;
//    @Autowired
//    private UserInfoMapper userInfoMapper;


    @ApiOperation(value = "批量插入用户信息")
    @GetMapping("/batchInsertUser")
    public ResponseEntityDto batchInsertUser(){
        int count = 100;

        List<UserInfoEntity> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            UserInfoEntity userByCount = mpUseService.createUserByCount(i);
            list.add(userByCount);
        }
        userInfoService.saveBatch(list);
        return buildSuccesResp();
    }

    @ApiOperation(value = "批量插入用户信息2",notes = "使用insertBatchSomeColumn")
    @GetMapping("/batchInsertUser2")
    public ResponseEntityDto batchInsertUser2(){
        userInfoService.remove(Wrappers.emptyWrapper());

        int count = 100;
        List<UserInfoEntity> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            UserInfoEntity userByCount = mpUseService.createUserByCount(i);
            list.add(userByCount);
        }
//        userInfoMapper.insertBatchSomeColumn(list);
        return buildSuccesResp();
    }


    @ApiOperation(value = "插入用户信息",notes = "插入用户信息详情")
    @GetMapping("/insertUser")
    public ResponseEntityDto insertUser(){
        UserInfoEntity userInfoEntity = mpUseService.createUserByCount(999);
        userInfoService.save(userInfoEntity);
        return buildSuccesResp(userInfoEntity);
    }

    @ApiOperation(value = "分页查询用户信息")
    @GetMapping("/page")
    public ResponseEntityDto page(@ApiParam Long page,@ApiParam Long pageSize){
        IPage<UserInfoEntity> pageParam = new Page<>(page,pageSize);

        IPage<UserInfoEntity> result = userInfoService.page(pageParam);
        IPage<UserInfoEntity> result2 = userInfoService.page(pageParam,new LambdaQueryWrapper<UserInfoEntity>()
                .gt(UserInfoEntity::getAge,50));

        return buildSuccesResp(JSON.toJSONString(result));
    }


    @ApiOperation(value = "更新用户信息",notes = "更新用户信息,增加了时间字段的转换、JSON格式数据的使用")
    @PostMapping("/updateUser")
    public ResponseEntityDto updateUser(@RequestBody UserInfoEntity vo){
        boolean result = userInfoService.updateById(vo);

        userInfoService.update(vo,new LambdaQueryWrapper<UserInfoEntity>().eq(UserInfoEntity::getId,1));

        return buildSuccesResp(result);
    }


    @ApiOperation(value = "根据用户名查信息",notes = "增加最后sql语句的拼接")
    @GetMapping("/getUserByName")
    public ResponseEntityDto getUserByName(@ApiParam String name){
        return buildSuccesResp(userInfoService.getOne(new LambdaQueryWrapper<UserInfoEntity>()
                .eq(UserInfoEntity::getName,name).apply(" limt 1")));
    }


    @ApiOperation(value = "执行任何sql")
    @PostMapping("/handleSql")
    public ResponseEntityDto handleSql(@RequestParam String sqlStr){
//        userInfoService.handleSql(sqlStr);
        return buildSuccesResp();
    }


    public static void main(String[] args) throws Exception {
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);

        String sqlStr = "select * from user_info";

        String s = aes.encryptHex(sqlStr);
        System.out.println("加密前:" + s);
        System.out.println(aes.decryptStr(s, CharsetUtil.CHARSET_UTF_8));
    }


}
