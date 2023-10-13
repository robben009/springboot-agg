package com.robben.nacos.dc.controller;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NacosGateWay {
    @Autowired
    private NacosServiceManager nacosServiceManager;
    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    public String getHostUrl(String instanceName) {
        NamingService namingService = nacosServiceManager.getNamingService(nacosDiscoveryProperties.getNacosProperties());
        Instance instance = null;
        try {
            instance = namingService.selectOneHealthyInstance(instanceName, nacosDiscoveryProperties.getGroup());
        } catch (NacosException e) {
            log.error("NacosUrlService_getHostUrl error={}",instanceName,e);
            e.printStackTrace();
        }

        return "http://" + instance.getIp() + ":" + instance.getPort() + "/";
    }

}