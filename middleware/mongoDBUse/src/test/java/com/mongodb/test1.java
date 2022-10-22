package com.mongodb;

import com.alibaba.fastjson.JSON;
import com.mongodb.entity.User;
import com.mongodb.entity.User2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/11/3 15:55
 */
@SpringBootTest
public class test1 {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void test() throws InterruptedException {
        User user = new User();
        user.setId(2);
        user.setAge(2);
        user.setName("robben235235");
        mongoTemplate.insert(user);//插入数据，会自动插入到对应的集合中

        List<User> list = mongoTemplate.findAll(User.class);//查询集合的所有数据
        System.out.println(JSON.toJSONString(list));

        Query query = new Query(Criteria.where("age").is(2));
        List<User> list2 =  mongoTemplate.find(query,User.class);
        System.out.println(JSON.toJSONString(list2));

        Thread.sleep(5*1000);
        List<User> list3 =  mongoTemplate.find(query,User.class);
        System.out.println(JSON.toJSONString(list3));

        Thread.sleep(6*1000);
        List<User> list4 =  mongoTemplate.find(query,User.class);
        System.out.println(JSON.toJSONString(list4));


//
//        Query query=new Query(Criteria.where("name").is("wang").and("age").is("20"));//Query是条件构造器，这个条件代表name是wang,age是20
//        mongoTemplate.find(query,User.class);//条件查询
//        mongoTemplate.find(query.skip((curPage-1)*size).limit(size),User.class);//分页查询，query.skip((curPage-1)*size).limit(size)表示跳过（当前页-1）*每页的大小条数据，输出size条数据
//
//        Update update=new Update();
//        update.set("name","li");
//        UpdateResult upsert=mongoTemplate.upsert(query,update,User.class);//修改，跟别的不同的是修改需要把修改的内容传递给一个Update对象
//        Long count1=upsert.getModifiedCount();  //得到受影响的数据数
//
//
//        DeleteResult remove=mongoTemplate.remove(query,User.class);//删除
//        Long count2=remove.getModifiedCount();//得到受影响的数据数


    }


    @Test
    public void test2() throws InterruptedException {
        User2 user = new User2();
        user.setAge2(2);
        user.setAge(2);
        user.setExpireTime(LocalDateTime.now().plusSeconds(5));
        mongoTemplate.insert(user);//插入数据，会自动插入到对应的集合中

        System.out.println("执行完成");
    }


}
