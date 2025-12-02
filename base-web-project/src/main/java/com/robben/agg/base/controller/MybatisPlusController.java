package com.robben.agg.base.controller;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.robben.agg.base.dao.entity.UserInfo;
import com.robben.agg.base.dao.service.UserInfoService;
import com.robben.agg.base.resp.BwpResponse;
import com.robben.agg.base.service.MpUseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
@Tag(name = "mybatis-Plus使用")
@RestController
@RequestMapping("/mybatisPlus")
@RequiredArgsConstructor
public class MybatisPlusController {
    private final MpUseService mpUseService;
    private final UserInfoService userInfoService;


    @Operation(summary = "批量插入")
    @GetMapping("/batchInsertUser")
    public BwpResponse batchInsertUser() {
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        userInfoService.remove(Wrappers.emptyWrapper());

        int count = 100;
        List<UserInfo> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            UserInfo userByCount = mpUseService.createUserByCount(i);
            list.add(userByCount);
        }
        userInfoService.saveBatch(list);
        return BwpResponse.buildSuccess();
    }


    @Operation(summary = "分页使用")
    @GetMapping("/page")
    public BwpResponse page(@RequestParam Long page, @RequestParam Long pageSize) {
        IPage<UserInfo> result = userInfoService.page(new Page<>(page, pageSize), new LambdaQueryWrapper<UserInfo>()
                .gt(UserInfo::getAge, 50));
        return BwpResponse.of(result);
    }

    @Operation(summary = "更新使用", description = "更新用户信息,增加了时间字段的转换、JSON格式数据的使用")
    @PostMapping("/updateUser")
    public BwpResponse updateUser(@RequestBody UserInfo vo) {
        boolean result = userInfoService.updateById(vo);
        userInfoService.update(vo, new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getId, 1));
        return BwpResponse.of(result);
    }


    @Operation(summary = "apply使用", description = "增加最后sql语句的拼接")
    @GetMapping("/getUserByName")
    public BwpResponse getUserByName(@RequestParam String name) {
        return BwpResponse.of(userInfoService.getOne(new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getName, name)));
    }


    @Operation(summary = "执行任何sql")
    @PostMapping("/handleSql")
    public BwpResponse handleSql(@RequestParam String sqlStr) {
        return BwpResponse.buildSuccess();
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
