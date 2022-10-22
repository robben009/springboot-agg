package com.robben.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.robben.dao.UserInfoEntityService;
import com.robben.dao.mapper.UserInfoEntityMapper;
import com.robben.entity.UserInfoEntity;
import org.springframework.stereotype.Service;


@Service
public class UserInfoEntityServiceImpl extends ServiceImpl<UserInfoEntityMapper, UserInfoEntity> implements UserInfoEntityService {

}




