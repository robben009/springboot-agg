package com.robben.agg.sharding.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingValue;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class OrderHitShardingRules implements HintShardingAlgorithm<Integer> {

    @Override
    public Collection<String> doSharding(Collection<String> allTableList, HintShardingValue<Integer> shardingValue) {
        Collection<String> result = new ArrayList<>();
        for (String t : allTableList) {
            String num = getRealTableNum(t);
            for (Integer value : shardingValue.getValues()) {
                if (num.equals(value.toString())) {
                    result.add(t);
                }
            }
        }

        log.info("hitTableShardingName={}", result);
        return result;
    }

    //获取表下划线的序号
    public static String getRealTableNum(String allTableVo) {
        if (allTableVo == null || allTableVo.isEmpty()) {
            return "";
        }
        String[] parts = allTableVo.split("_");
        return parts[parts.length - 1];
    }

}