//package com.robben.sharding.config;
//
//import com.robben.sharding.utils.*;
//import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
//import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
//import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
//import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
//import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.SpringBootConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
//
///**
// * Description： TODO
// * Author: robben
// * Date: 2021/11/16 16:47
// */
//
//@Configuration
//public class ShardingConfig {
//
//    @Value("${jdbc.username}")
//    private String jdbcUsername;
//    @Value("${jdbc.pwd}")
//    private String jdbcPwd;
//
//    //数据库名称
//    private final static String defaultDataSourcesName="default_dataSource";
//    //逻辑表名称
//    private final String LOGICAL_TABLE = "user";
//    //分片键
//    private final String DATABASE_SHARDING_COLUMN = "sId";
//
//
//    @Bean
//    DataSource getShardingDataSource() throws SQLException {
//        // 分片规则配置对象
//        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
//        // 规则配置
//        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
//
//        // 数据库分片算法（精确、范围），按年分库
//        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration(DATABASE_SHARDING_COLUMN, new PreciseDatabaseShardingAlgorithm(), new RangeDatabaseShardingAlgorithm()));
//
//        // 表分片算法（精确、范围），按月分表
//        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration(DATABASE_SHARDING_COLUMN, new PreciseTableShardingAlgorithm(), new RangeTableShardingAlgorithm()));
//        // 默认库，如果存在广播表和绑定表也可在此配置
//        shardingRuleConfig.setDefaultDataSourceName(defaultDataSourcesName);
//        // 开启日志打印
//        final Properties properties = new Properties();
//        properties.setProperty("sql.show", "true");
//
//        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, properties);
//    }
//
//
//    TableRuleConfiguration getOrderTableRuleConfiguration() {
//        // 暂定为两年，关于此表达式，可查看官方文档 https://shardingsphere.apache.org/document/legacy/4.x/document/cn/features/sharding/other-features/inline-expression/
//        String inLineExpressionStr = defaultDataSourcesName + "." + LOGICAL_TABLE + "_20210${1..9}" + ","
//                                   + defaultDataSourcesName + "." + LOGICAL_TABLE + "_20211${0..2}";
//        final TableRuleConfiguration ruleConfiguration = new TableRuleConfiguration("t_order", inLineExpressionStr);
//        // 设置主键生成策略
//        ruleConfiguration.setKeyGeneratorConfig(getKeyGeneratorConfiguration());
//        return ruleConfiguration;
//    }
//
//    private KeyGeneratorConfiguration getKeyGeneratorConfiguration() {
//        return new KeyGeneratorConfiguration("SNOWFLAKE", "id");
//    }
//
//
//    private Map<String, DataSource> createDataSourceMap() {
//        // key为数据源名称，后面分片算法取得就是这个，value为具体的数据源
//        final HashMap<String, DataSource> shardingDataSourceMap = new HashMap<>();
//        String jdbcUrl = "jdbc:mysql://212.64.18.166:3306/sharding?userUnicode=ture&characterEncoding=utf8&serverTimezone=GMT%2B8";
//        shardingDataSourceMap.put("default_dataSource", DataSourceUtil.createDataSource("com.mysql.cj.jdbc.Driver", jdbcUrl, jdbcUsername, jdbcPwd));
//        return shardingDataSourceMap;
//    }
//
//}