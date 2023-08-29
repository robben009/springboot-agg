//package com.robben.config.mybtis;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.plugin.*;
//import org.apache.ibatis.session.ResultHandler;
//import org.apache.ibatis.session.RowBounds;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * Description： 替换特殊字符
// * Author: robben
// * Date: 2021/4/30 13:55
// */
//
////增加了compent就不需要配置了
//@Component
//@Intercepts({
//        @Signature(
//                method = "query",
//                type = Executor.class,
//                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
//        )
//})
//public class MybatisPlusInterceptor implements Interceptor {
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        // 拦截sql
//        Object[] args = invocation.getArgs();
//        MappedStatement statement = (MappedStatement)args[0];
//        Object parameterObject = args[1];
//        BoundSql boundSql = statement.getBoundSql(parameterObject);
//        String sql = boundSql.getSql();
//        // 处理特殊字符
//        modifyLikeSql(sql, parameterObject, boundSql);
//        // 返回
//        return invocation.proceed();
//    }
//
//    @Override
//    public Object plugin(Object target) {
//        return Plugin.wrap(target, this);
//    }
//
//    @SuppressWarnings("unchecked")
//    public static String modifyLikeSql(String sql, Object parameterObject, BoundSql boundSql) {
//        if (parameterObject instanceof HashMap) {
//        } else {
//            return sql;
//        }
//        if (!sql.toLowerCase().contains(" like ") || !sql.toLowerCase().contains("?")) {
//            return sql;
//        }
//        // 获取关键字的个数（去重）
//        String[] strList = sql.split("\\?");
//        Set<String> keyNames = new HashSet<>();
//        for (int i = 0; i < strList.length; i++) {
//            if (strList[i].toLowerCase().contains(" like ")) {
//                String keyName = boundSql.getParameterMappings().get(i).getProperty();
//                keyNames.add(keyName);
//            }
//        }
//        // 对关键字进行特殊字符“清洗”，如果有特殊字符的，在特殊字符前添加转义字符（\）
//        for (String keyName : keyNames) {
//            HashMap parameter = (HashMap)parameterObject;
//            if (keyName.contains("ew.paramNameValuePairs.") && sql.toLowerCase().contains(" like ?")) {
//                // 第一种情况：在业务层进行条件构造产生的模糊查询关键字
//                QueryWrapper wrapper = (QueryWrapper)parameter.get("ew");
//                parameter = (HashMap)wrapper.getParamNameValuePairs();
//
//                String[] keyList = keyName.split("\\.");
//                // ew.paramNameValuePairs.MPGENVAL1，截取字符串之后，获取第三个，即为参数名
//                Object a = parameter.get(keyList[2]);
//                if (a instanceof String &&
//                        (a.toString().contains("_") || a.toString().contains("\\") || a.toString().contains("%"))) {
//                    parameter.put(keyList[2], "%" + escapeChar(a.toString().substring(1, a.toString().length() - 1)) + "%");
//                }
//            } else if (!keyName.contains("ew.paramNameValuePairs.") && sql.toLowerCase().contains(" like ?")) {
//                // 第二种情况：未使用条件构造器，但是在service层进行了查询关键字与模糊查询符`%`手动拼接
//                Object a = parameter.get(keyName);
//                if (a instanceof String &&
//                        (a.toString().contains("_") || a.toString().contains("\\") || a.toString().contains("%"))) {
//                    parameter.put(keyName, "%" + escapeChar(a.toString().substring(1, a.toString().length() - 1)) + "%");
//                }
//            } else {
//                // 第三种情况：在Mapper类的注解SQL中进行了模糊查询的拼接
//                Object a = parameter.get(keyName);
//                if (a instanceof String &&
//                        (a.toString().contains("_") || a.toString().contains("\\") || a.toString().contains("%"))) {
//                    parameter.put(keyName, escapeChar(a.toString()));
//                }
//            }
//        }
//        return sql;
//    }
//
//
//    //mysql的模糊查询时特殊字符转义
//    public static String escapeChar(String before){
//        if(StringUtils.isNotBlank(before)){
//            before = before.replaceAll("\\\\", "\\\\\\\\");
//            before = before.replaceAll("_", "\\\\_");
//            before = before.replaceAll("%", "\\\\%");
//        }
//        return before ;
//    }
//
//}