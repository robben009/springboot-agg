package com.hjz.sharding.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.driver.jdbc.core.driver.ShardingSphereURLProvider;

import java.util.Properties;

/**
 * @author hanWang
 * @Title:
 * @Package
 * @Description:
 * @date 2024/1/274:07 AM
 */
public class NacosDriverURLProvider implements ShardingSphereURLProvider {
    private static final String NACOS_TYPE = "nacos:";

    public NacosDriverURLProvider() {
    }

    public boolean accept(String url) {
//        return StringUtils.isNotBlank(url) && url.contains(NACOS_TYPE);
        return true;
    }

    @Override
    public byte[] getContent(String s, String s1) {
        ConfigService configService;
        String resultConfig = "";
        try {
            Properties properties = new Properties();
            properties.put(PropertyKeyConst.SERVER_ADDR, "150.158.121.165:8848");
            configService = NacosFactory.createConfigService(properties);
            resultConfig = configService.getConfig("shardingJDBC1-sharding", "DEFAULT_GROUP", 6000);
        } catch (Exception e) {

        }
        return resultConfig.getBytes();
    }


}

