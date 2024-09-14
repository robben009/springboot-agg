//package com.robben.agg.base.interceptor.mybtis;
//
//import cn.hutool.core.util.ReflectUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.mapping.SqlCommandType;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.plugin.Intercepts;
//import org.apache.ibatis.plugin.Invocation;
//import org.apache.ibatis.plugin.Signature;
//import org.apache.ibatis.session.ResultHandler;
//import org.apache.ibatis.session.RowBounds;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Field;
//
///**
// * Description： TODO
// * Author: robben
// * Date: 2021/4/30 14:46
// */
//@Slf4j
//@Component
//@Intercepts({
//        @Signature(
//                method = "query",
//                type = Executor.class,
//                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
//        ),
//        @Signature(
//                type = Executor.class,
//                method = "update",
//                args = {MappedStatement.class, Object.class}
//        )
//})
//public class MybatisInterceptor implements Interceptor {
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
//        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
//        Object parameter = invocation.getArgs()[1];
//
//        if (parameter == null) {
//            return invocation.proceed();
//        }
//        if (SqlCommandType.SELECT == sqlCommandType) {
//            Field[] fields = ReflectUtil.getFields(parameter.getClass());
//            for (Field field : fields) {
//                if (field.getType().equals(String.class)) {
//                    field.setAccessible(true);
//                    Object o = field.get(parameter);
//                    String newVal = escapeChar(String.valueOf(o));
//                    field.set(parameter, newVal);
//                }
//            }
//        }
//
//        return invocation.proceed();
//    }
//
//
//    public static String escapeChar(String before){
//        if(StringUtils.isNotBlank(before)){
//          before = before.replaceAll("\\\\", "\\\\\\\\");
//          before = before.replaceAll("_", "########");
//          before = before.replaceAll("%", "\\\\%");
//        }
//        return before ;
//    }
//
//}