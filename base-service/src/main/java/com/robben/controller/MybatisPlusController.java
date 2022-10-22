package com.robben.controller;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

/**
 * 对于2
 * commit 1
 * commit 2
 * commit 3
 */

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



    @ApiOperation(value = "插入用户信息",notes = "插入用户信息详情")
    @GetMapping("/insertUser")
    public ResponseEntityDto insertUser(){
        UserInfoEntity vo = new UserInfoEntity();
        vo.setAge(1);
        vo.setName("lalalalal");
        vo.setCreateTime(new Date());
        vo.setSex(false);
        vo.setUpdateTime(new Date());
        vo.setWorkInfo("workinfo");

        //赋值一个json对象,这个对象中还有数组集合
        DescInfoVo dv = new DescInfoVo();
        dv.setAge(1);
        dv.setName("name");
        dv.setPhone(Arrays.asList("123","456"));
        vo.setDescInfo(dv);

        //赋值一个json集合对象
        List<DescInfoVo> ld = new ArrayList<>();
        ld.add(dv);
        vo.setDescInfoList(ld);

        //赋值一个对象,对象中有list
        DescInfoListVo descInfoListVo = new DescInfoListVo();
        descInfoListVo.setDlist(ld);
        vo.setDescInfoListVo(descInfoListVo);

        userInfoEntityMapper.insert(vo);
        return buildSuccesResp(vo);
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


    @ApiOperation(value = "根据用户名查信息")
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
