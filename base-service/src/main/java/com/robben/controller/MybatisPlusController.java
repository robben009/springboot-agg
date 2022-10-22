package com.robben.controller;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.robben.common.ResponseEntityDto;
import com.robben.common.UnifiedReply;
import com.robben.dao.UserInfoEntityService;
import com.robben.dao.mapper.UserInfoEntityMapper;
import com.robben.entity.UserInfoEntity;
import com.robben.entity.DescInfoListVo;
import com.robben.entity.DescInfoVo;
import com.robben.service.MpUseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Slf4j
@Api(tags = "mybatis-Plus使用")
@RestController
@RequestMapping("/mybatisPlus")
public class MybatisPlusController extends UnifiedReply {
    @Autowired
    private UserInfoEntityMapper userInfoEntityMapper;
    @Autowired
    private MpUseService mpUseService;
    @Autowired
    private UserInfoEntityService userInfoEntityService;

    @ApiOperation(value = "批量插入用户信息")
    @GetMapping("/batchInsertUser")
    public ResponseEntityDto batchInsertUser(){
        int count = 100;

        List<UserInfoEntity> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            UserInfoEntity userByCount = mpUseService.createUserByCount(i);
            list.add(userByCount);
        }
        userInfoEntityService.saveBatch(list);
        return buildSuccesResp();
    }

    @ApiOperation(value = "批量插入用户信息2",notes = "使用insertBatchSomeColumn")
    @GetMapping("/batchInsertUser2")
    public ResponseEntityDto batchInsertUser2(){
        userInfoEntityService.remove(Wrappers.emptyWrapper());

        int count = 100;
        List<UserInfoEntity> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            UserInfoEntity userByCount = mpUseService.createUserByCount(i);
            list.add(userByCount);
        }
        userInfoEntityMapper.insertBatchSomeColumn(list);
        return buildSuccesResp();
    }


    @ApiOperation(value = "插入用户信息",notes = "插入用户信息详情")
    @GetMapping("/insertUser")
    public ResponseEntityDto insertUser(){
        UserInfoEntity userInfoEntity = mpUseService.createUserByCount(999);
        userInfoEntityMapper.insert(userInfoEntity);
        return buildSuccesResp(userInfoEntity);
    }

    @ApiOperation(value = "分页查询用户信息")
    @GetMapping("/page")
    public ResponseEntityDto page(@ApiParam Long page,@ApiParam Long pageSize){
        IPage<UserInfoEntity> pageParam = new Page<>(page,pageSize);

        IPage<UserInfoEntity> result = userInfoEntityService.page(pageParam);
        IPage<UserInfoEntity> result2 = userInfoEntityService.page(pageParam,new LambdaQueryWrapper<UserInfoEntity>()
                .gt(UserInfoEntity::getAge,50));

        return buildSuccesResp(JSON.toJSONString(result));
    }


    @ApiOperation(value = "更新用户信息",notes = "更新用户信息,增加了时间字段的转换、JSON格式数据的使用")
    @PostMapping("/updateUser")
    public ResponseEntityDto updateUser(@RequestBody UserInfoEntity vo){
        int result = userInfoEntityMapper.updateById(vo);

        userInfoEntityMapper.update(vo,new LambdaQueryWrapper<UserInfoEntity>().eq(UserInfoEntity::getId,1));

        return buildSuccesResp(result);
    }

    @ApiOperation(value = "根据用户ID")
    @GetMapping("/getUserById")
    public ResponseEntityDto getUserById(@ApiParam int id){
        return buildSuccesResp(userInfoEntityMapper.selectById(id));
    }


    @ApiOperation(value = "根据用户名查信息",notes = "增加最后sql语句的拼接")
    @GetMapping("/getUserByName")
    public ResponseEntityDto getUserByName(@ApiParam String name){
        return buildSuccesResp(userInfoEntityMapper.selectOne(new LambdaQueryWrapper<UserInfoEntity>()
                .eq(UserInfoEntity::getName,name).apply(" limt 1")));
    }


    @ApiOperation(value = "执行任何sql")
    @PostMapping("/handleSql")
    public ResponseEntityDto handleSql(@RequestParam String sqlStr){
        userInfoEntityMapper.handleSql(sqlStr);
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
