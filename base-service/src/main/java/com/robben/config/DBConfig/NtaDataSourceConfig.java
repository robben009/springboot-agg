package com.robben.config.DBConfig;//package com.robben.config.DBConfig;
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
//@MapperScan(basePackages = "com.***.auth.server.dao.nta", sqlSessionTemplateRef  = "ntaSqlSessionTemplate")
//public class NtaDataSourceConfig {
//
//    @Bean
//    @ConfigurationProperties("spring.datasource.nta")
//    @Primary
//    public DataSource ntaDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean
//    @Primary
//    public SqlSessionFactory ntaSqlSessionFactory(@Qualifier("ntaDataSource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:ntaMapper/*.xml"));
//        return bean.getObject();
//    }
//
//    @Bean(name="ntaMapperScannerConfigurer")
//    public MapperScannerConfigurer ntaMapperScannerConfigurer(){
//        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
//        configurer.setSqlSessionFactoryBeanName("ntaSqlSessionFactory");
//        configurer.setBasePackage("com.***.auth.server");
//        configurer.setAnnotationClass(NtaDao.class);
//        return configurer;
//    }
//
//    @Bean(name = "ntaTransactionManager")
//    @Primary
//    public DataSourceTransactionManager ntaTransactionManager(@Qualifier("ntaDataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean(name = "ntaSqlSessionTemplate")
//    @Primary
//    public SqlSessionTemplate ntaSqlSessionTemplate(@Qualifier("ntaSqlSessionFactory") SqlSessionFactory sqlSessionFactory)
//            throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//
//
//}
