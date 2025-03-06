package com.robben.agg.base.dao.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.robben.agg.base.dao.entity.UserInfo;
import com.robben.agg.base.dao.mapper.UserInfoMapper;
import com.robben.agg.base.dao.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
* @author hjz
* @description 针对表【user_info】的数据库操作Service实现
* @createDate 2025-03-06 17:18:35
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService {


}




