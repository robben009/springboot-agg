package com.robben.agg.base.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.robben.agg.base.dao.mapper.UserInfoMapper;
import com.robben.agg.base.dao.service.UserInfoService;
import com.robben.agg.base.entity.UserInfoEntity;
import org.springframework.stereotype.Service;

/**
* @author hjz
* @description 针对表【user_info】的数据库操作Service实现
* @createDate 2023-01-31 16:39:50
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfoEntity>
    implements UserInfoService {

}




