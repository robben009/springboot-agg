package com.robben.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 开机启动
 */

@Slf4j
@Component
public class SpringBeanTools implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @SneakyThrows
    @Override
    public void setApplicationContext(ApplicationContext var1) throws BeansException {
        SpringBeanTools.applicationContext = var1;
    }


    public static <T> T getBean(String beanName) {
        if (applicationContext.containsBean(beanName)) {
            return (T) applicationContext.getBean(beanName);
        } else {
            return null;
        }
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> baseType) {
        return applicationContext.getBeansOfType(baseType);
    }
}
