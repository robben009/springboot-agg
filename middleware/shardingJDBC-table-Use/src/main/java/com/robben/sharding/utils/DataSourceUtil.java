package com.robben.sharding.utils;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/11/16 16:58
 */
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;


public class DataSourceUtil {

    public static DataSource createDataSource(final String driverClass, final String url, String userName, String passWord) {
        final HikariDataSource result = new HikariDataSource();
        result.setDriverClassName(driverClass);
        result.setJdbcUrl(url);
        result.setUsername(userName);
        result.setPassword(passWord);
//        result.setInitialSize(5);
//        result.setMinIdle(5);
//        result.setMaxActive(20);
//        result.setMaxWait(60000);
//        result.setTimeBetweenEvictionRunsMillis(60000);
//        result.setMinEvictableIdleTimeMillis(30000);

        return result;
    }

}
