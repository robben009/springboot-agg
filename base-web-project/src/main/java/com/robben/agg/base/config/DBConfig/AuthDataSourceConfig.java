package com.robben.agg.base.config.DBConfig;//package com.robben.agg.base.config.DBConfig;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.mybatis.spring.mapper.MapperScannerConfigurer;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//
///**
// * Description： TODO
// * Author: robben
// * Date: 2020/9/1 16:26
// */
//
//@Configuration
//@MapperScan(basePackages = "com.***.auth.com.chat.server.dao.auth", sqlSessionTemplateRef  = "authSqlSessionTemplate")
//public class AuthDataSourceConfig {
//
//    @Bean
//    @ConfigurationProperties("spring.datasource.auth")
//    @Primary
//    public DataSource authDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean
//    @Primary
//    public SqlSessionFactory authSqlSessionFactory(@Qualifier("authDataSource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:authMapper/*.xml"));
//        return bean.getObject();
//    }
//
//    @Bean(name="authMapperScannerConfigurer")
//    public MapperScannerConfigurer authMapperScannerConfigurer(){
//        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
//        configurer.setSqlSessionFactoryBeanName("authSqlSessionFactory");
//        configurer.setBasePackage("com.***.auth.com.chat.server");
//        configurer.setAnnotationClass(AuthDao.class);
//        return configurer;
//    }
//
//    @Bean(name = "authfTransactionManager")
//    @Primary
//    public DataSourceTransactionManager authTransactionManager(@Qualifier("authDataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean(name = "authSqlSessionTemplate")
//    @Primary
//    public SqlSessionTemplate authSqlSessionTemplate(@Qualifier("authSqlSessionFactory") SqlSessionFactory sqlSessionFactory)
//            throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//
//
//}
