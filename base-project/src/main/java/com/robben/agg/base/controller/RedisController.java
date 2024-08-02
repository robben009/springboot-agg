package com.robben.agg.base.controller;

import cn.hutool.core.thread.ThreadUtil;
import com.robben.agg.base.contants.Contants;
import com.robben.agg.base.resp.ResponseEntityDto;
import com.robben.agg.base.config.RedisConfig.RedisMQChannels;
import com.robben.agg.base.dao.entity.UserInfoEntity;
import com.robben.agg.base.service.CacheService;
import com.robben.agg.base.service.LocalCacheService;
import com.robben.agg.base.utils.RedisUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RFuture;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Slf4j
@Tag(name = "redis使用")
@RestController
@RequestMapping("/redis")
public class RedisController extends UnifiedReply {
    @Autowired
    private CacheService cacheService;
    @Autowired
    private LocalCacheService localCacheService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RedissonClient redissonClient;
    @Qualifier(Contants.MY_EXECUTOR)
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;


    private int testValue = 0;


    @Operation(summary = "redis注解缓存", description = "可自定义缓存失效时间和key生成器")
    @GetMapping(value = "/getUser")
    public ResponseEntityDto<UserInfoEntity> getUser(@RequestParam int id) {
        cacheService.getUserByRedis(id);
        cacheService.getUserByRedisValue(id);
        cacheService.getUserByRedisTime(id, id);
        cacheService.UserServicegetUserNull(id);
        return buildSuccesResp();
    }

    @Operation(summary = "redis注解缓存2", description = "可自定义缓存失效时间和key生成器")
    @GetMapping(value = "/getUser2")
    public ResponseEntityDto<UserInfoEntity> getUser2() {
        UserInfoEntity vo = new UserInfoEntity();
        vo.setAge(1);
        vo.setId(2L);
        vo.setName("kkkkk");
        return buildSuccesResp(cacheService.getUserByRedisTimeObject(vo));
    }

    //接受消息的方法见com.robben.redisMsg.RCMHandler
    @Operation(summary = "测试redis发布订阅消息")
    @GetMapping(value = "/sendRedisMsg")
    public String sendRedisMsg(@RequestParam String msg) {
        redisUtils.convertAndSend(RedisMQChannels.redisChannelTest1, msg);
        redisUtils.convertAndSend(RedisMQChannels.redisChannelTest2, msg);

        return msg;
    }

    @Operation(summary = "测试hash的使用")
    @GetMapping(value = "/testHash")
    public String testHash() {
        redisTemplate.opsForHash().put("bigKey", "k1", "v1");
        redisTemplate.opsForHash().put("bigKey", "k1", "v2");
        redisTemplate.opsForHash().put("bigKey", "k3", "v3");

        //设置bigkey的过期时间
        redisTemplate.opsForHash().getOperations().expire("bigKey", 1, TimeUnit.HOURS);

        Map<String, String> bigKey = redisTemplate.opsForHash().entries("bigKey");
        for (Map.Entry<String, String> s : bigKey.entrySet()) {
            System.out.println(s.getKey());
            System.out.println(s.getValue());
        }

        return "msg";
    }


    @Operation(summary = "分布式锁的使用-测试阻塞")
    @GetMapping(value = "/testTryLock")
    public boolean testTryLock(String lockKey) {
        RLock lock = redissonClient.getLock("testTryLock::" + lockKey);
        try {
            if (lock.tryLock(3, 10, TimeUnit.MINUTES)) {
                log.info("获取到锁:{}", lockKey);
                ThreadUtil.sleep(2 * 60 * 1000);
                log.info("获取到锁:{} 业务逻辑处理完成", lockKey);
            } else {
                log.info("未获取到锁");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return true;
    }


    @Operation(summary = "分布式锁的使用-测试异步非阻塞")
    @GetMapping(value = "/testTryAsyncLock")
    public boolean testTryAsyncLock(String lockKey) {
        RLock lock = redissonClient.getLock("testTryAsyncLock::" + lockKey);
        try {
            RFuture<Boolean> booleanRFuture = lock.tryLockAsync(3, 10, TimeUnit.SECONDS);
            if (booleanRFuture.get()) {
                log.info("获取到锁:{}", lockKey);
                ThreadUtil.sleep(2 * 60 * 1000);
                log.info("获取到锁:{} 业务逻辑处理完成", lockKey);
            } else {
                log.info("未获取到锁");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return true;
    }


    @Operation(summary = "分布式锁的使用-同步锁")
    @GetMapping(value = "/clusterLock")
    public boolean clusterLock() {
        RLock lock = redissonClient.getLock("anyLock");
        try {
            // 1. 最常见的使用方法
            //lock.lock();

            // 2. 支持过期解锁功能,10秒钟以后自动解锁, 无需调用unlock方法手动解锁
            //lock.lock(10, TimeUnit.SECONDS);

            // 3. 尝试加锁，最多等待3秒，上锁以后10秒自动解锁(该方法会阻塞)
            boolean res = lock.tryLock(3, 10, TimeUnit.SECONDS);
            boolean res2 = lock.tryLock(10, TimeUnit.SECONDS);

            if (res) {
                // do your business
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return true;
    }


    @Operation(summary = "分布式锁的使用-异步锁")
    @GetMapping(value = "/clusterLock2")
    public boolean clusterLock2() {
        RLock lock = redissonClient.getLock("anyLock");
        try {
            RFuture<Void> res = lock.lockAsync();
            RFuture<Void> res2 = lock.lockAsync(10, TimeUnit.SECONDS);
            Future<Boolean> res3 = lock.tryLockAsync(3, 10, TimeUnit.SECONDS);

            if (res3.get()) {
                // do your business
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return true;
    }


    @Operation(summary = "测试并发问题")
    @GetMapping(value = "/testLock")
    public int testLock() {
        testValue = testValue + 1;

//        RLock lock = redissonClient.getLock("anyLock");
//        try{
//            lock.lockAsync();
//            lock.lockAsync(10, TimeUnit.SECONDS);
//            Future<Boolean> res = lock.tryLockAsync(3, 10, TimeUnit.SECONDS);
//
//            if(res.get()){
//                // do your business
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } finally {
//            lock.unlock();
//        }
        return testValue;
    }

}
