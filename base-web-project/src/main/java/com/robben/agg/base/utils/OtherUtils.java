package com.robben.agg.base.utils;

import cn.hutool.core.date.SystemClock;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Date;

@Service
public class OtherUtils {
    ThreadLocal<String> t = new ThreadLocal<>();

    //修改bean的属性值
    public void setFieldData(Field field, Object bean, String data) throws Exception {
        field.setAccessible(true);
        Class<?> type = field.getType();

        if(type.equals(String.class)){
            field.set(bean,data);
        }
        else if(type.equals(Integer.class)){
            field.set(bean,Integer.valueOf(data));
        }
        else if(type.equals(Long.class)){
            field.set(bean,Long.valueOf(data));
        }
        else if(type.equals(Double.class)){
            field.set(bean,Double.valueOf(data));
        }
        else if(type.equals(Short.class)){
            field.set(bean,Short.valueOf(data));
        }
        else if(type.equals(Byte.class)){
            field.set(bean,Byte.valueOf(data));
        }
        else if(type.equals(Boolean.class)){
            field.set(bean,Boolean.valueOf(data));
        }
        else if(type.equals(Date.class)){
            field.set(bean,new Date(Long.valueOf(data)));
        }
    }

    public String getTime() {
        if(t.get() == null){
            t.set(SystemClock.now() + "#" + Thread.currentThread().getName());
        }
        return t.get();
    }

}
