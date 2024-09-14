package com.robben.agg.base.service;

import com.robben.agg.base.dao.entity.UserInfoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CacheService {

    //目前缓存必须带value
    @Cacheable(value = "test", keyGenerator = "cacheKeyGenerator")
    public UserInfoEntity getUserByRedis(Integer id) {
        log.info("~~~~~~~~handle-DB~~~~~~~~~~~~~~");
        UserInfoEntity u = new UserInfoEntity();
        u.setId(Long.valueOf(id));
        u.setName("kkkkk" + id);
        return u;
    }

    @Cacheable(value = "UserService_getUserByRedisValue")
    public UserInfoEntity getUserByRedisValue(int id) {
        UserInfoEntity u = new UserInfoEntity();
        u.setId(Long.valueOf(id));
        u.setName("kkkkk2" + id);
        return u;
    }


    @Cacheable(value = "UserService_getUserByRedisTime#s#100")
    public UserInfoEntity getUserByRedisTime(int id) {
        UserInfoEntity u = new UserInfoEntity();
        u.setId(Long.valueOf(id));
        u.setName("kkkkk3" + id);
        return u;
    }


    @Cacheable(value = "UserService_getUserByRedisTimeObject#s#100", key = "#p0.id +'::'+#p0.name")
    public UserInfoEntity getUserByRedisTimeObject(UserInfoEntity vo) {
        log.info("~~~~~~~~handle-DB~~~~~~~~~~~~~~");
        UserInfoEntity u = new UserInfoEntity();
        u.setId(vo.getId() + 100);
        u.setName(vo.getName() + "hhhhhh");
        return u;
    }


}
