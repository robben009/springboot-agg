package com.robben.sharding.utils;

/**
 * Description： TODO
 * Author: hujingzheng
 * Date: 2021/11/16 16:59
 */

import cn.hutool.core.date.DateUtil;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;
import java.util.Date;

public class PreciseTableShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    /**
     * 精确分片算法
     *
     * @param availableTargetNames 所有配置的表列表，这里代表所匹配到库的所有表
     * @param shardingValue        分片值，也就是dau_id的值
     * @return                     所匹配表的结果
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        Long value = shardingValue.getValue();
        if (value <= 0){
            throw new UnsupportedOperationException("preciseShardingValue is null");
        }

        String tableName = "user";
        tableName += "_" + DateUtil.format(new Date(value),"yyyyMM");

        for (String availableTargetName : availableTargetNames) {
            if (availableTargetName.equals(tableName)) {
                return availableTargetName;
            }
        }

        throw new UnsupportedOperationException("PreciseTableShardingAlgorithm_error:" + tableName);
    }

}
