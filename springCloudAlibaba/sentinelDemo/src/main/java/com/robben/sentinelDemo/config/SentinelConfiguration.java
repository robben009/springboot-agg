package com.robben.sentinelDemo.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class SentinelConfiguration {

    @Value("${sentinelCount}")
    private Integer sentinelCount;

    @Value("${spring.cloud.nacos.config.server-addr}")
    private String nacosServerHost;


    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }


    @PostConstruct
    private void initRules() {
        //这中修改仅仅适用于静态修改,是在类初始化的时候设置的规则无法动态修改。动态修改的话需要设置数据源
        //=============================规则1=========================
        FlowRule rule1 = new FlowRule();
        rule1.setResource("getAppValue");//规则名称
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);//如果设置0则按照线程数限流，如果设置1则按照QPS（每秒查询率）限流
        rule1.setCount(sentinelCount);   // 每秒调用最大次数为 10 次
        rule1.setControlBehavior(0);  //0快速失败，1预警，2排队等候
        rule1.setMaxQueueingTimeMs(1000);//排队超时阈值

        List<FlowRule> rules = new ArrayList<>();
        rules.add(rule1);
        // 将控制规则载入到 Sentinel
        FlowRuleManager.loadRules(rules);

        /**
         * 注意事项：
         * 1：上面的方法是从nacos配置中心获取@Value的值
         * 2：增加动态规则配置数据源(上面的和此方法只能有一个生效的)
         * 3：由于控制台放到了腾讯云服务器上,所有只有将本项目打包到服务器上才能看见控制台效果。一开始启动控制台是没有信息的,需要调用下接口才能显示
         * 4：对于动态数据源,下方的方法可以直接修改nacos的配置实现限流。但是修改控制台后没办法同步到nacos的配置。这一点可以使用控制台作为临时动态修改方案
         * 5: 动态数据源可以有多种方式,可以选择apollo作为配置。
         */
        loadRules();
    }


    private  void loadRules() {
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(nacosServerHost, "DEFAULT_GROUP", "sentinel-dynamic-sentinel-flow",
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                }));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
    }



}
