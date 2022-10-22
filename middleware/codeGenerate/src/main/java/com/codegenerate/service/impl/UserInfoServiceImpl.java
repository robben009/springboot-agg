package com.codegenerate.service.impl;

import com.codegenerate.dao.UserInfoDao;
import com.codegenerate.entity.UserInfoEntity;
import com.codegenerate.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (UserInfo)表服务实现类
 *
 * @author makejava
 * @since 2022-01-06 11:30:13
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;
    
    
}