package com.robben.agg.base.controller;

import com.robben.agg.customstarter.HelloService;
import com.robben.utils.OtherUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Tag(name = "bean的api")
@RestController
@RequestMapping("/outapi")
public class BeanController {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private OtherUtils otherUtils;
    @Autowired
    private HelloService helloService;


    @Operation(summary = "测试自定义的start")
    @PostMapping("startTest")
    public String startTest(String msg) {
        helloService.say();
        return "success";
    }


    @Operation(summary = "测试修改注入bean的字段值")
    @PostMapping("updateBeanField")
    public Map<String,String> updateBeanField(String benaName, String beanField, String beanFieldValue) {
        log.info("测试修改注入bean的字段值_benaName:{},beanField:{},beanFieldValue:{}",benaName,beanField,beanFieldValue);

        Map<String,String> map = new HashMap<>();
        if(StringUtils.isBlank(benaName) || StringUtils.isBlank(beanField) || StringUtils.isBlank(beanFieldValue)){
            map.put("result","输入参数有误");
            return map;
        }

        Class<?> object = applicationContext.getType(benaName);

        //getFields()：获得某个类的所有的公共（public）的字段，包括父类中的字段。
        //getDeclaredFields()：获得某个类的所有声明的字段，即包括public、private和proteced，但是不包括父类的申明字段。
        Field[] fields = object.getDeclaredFields();

        for (Field field : fields) {
            if(field.getName().equals(beanField)){
                Object bean = applicationContext.getBean(benaName);

                try {
                    otherUtils.setFieldData(field,bean,beanFieldValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        map.put("result","success");
        return map;
    }

}
