package com.robben.agg.base.service;

import com.robben.agg.base.entity.UserInfoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CacheService {

//    @Autowired
//    private UserInfoEntityMapper userInfoEntityMapper;

    //目前缓存必须带value
    @Cacheable(value = "test",keyGenerator = "cacheKeyGenerator")
    public UserInfoEntity getUserByRedis(int id) {
        log.info("~~~~~~~~handle-DB~~~~~~~~~~~~~~");
//        return userInfoEntityMapper.getUserById(id);
        return null;
    }

    @Cacheable(value = "UserService_getUserByRedisValue")
    public UserInfoEntity getUserByRedisValue(int id) {
//        return userInfoEntityMapper.getUserById(id);
        return null;
    }


    @Cacheable(value = "UserService_getUserByRedisTime#s#100")
    public UserInfoEntity getUserByRedisTime(int id, int id2) {
//        return userInfoEntityMapper.getUserById(id);
        return null;
    }

    @Cacheable(value = "UserService_getUserNull#s#100")
    public UserInfoEntity UserServicegetUserNull(int id) {
        System.out.println(1);
        return null;
    }


    @Cacheable(value = "UserService_getUserByRedisTimeObject#s#100",key = "#p0.id +'::'+#p0.name")
    public UserInfoEntity getUserByRedisTimeObject(UserInfoEntity vo) {
        log.info("~~~~~~~~handle-DB~~~~~~~~~~~~~~");
        return vo;
    }

}
