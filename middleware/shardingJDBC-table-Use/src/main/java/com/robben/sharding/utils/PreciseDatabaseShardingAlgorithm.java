package com.robben.sharding.utils;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/11/16 16:58
 */
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;


public class PreciseDatabaseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {


    /**
     * 精确分片算法
     *
     * @param availableTargetNames 所有配置的库列表
     * @param shardingValue        分片值，也就是save_time_com的值
     * @return                     所匹配库的结果
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {

        Long value = shardingValue.getValue();
        // 库后缀
        String yearStr = SDateUtil.getYearByMillisecond(value);

        if (value <= 0) {
            throw new UnsupportedOperationException("preciseShardingValue is null");
        }

        for (String availableTargetName : availableTargetNames) {
            if (availableTargetName.endsWith(yearStr)) {
                return availableTargetName;
            }
        }
        throw new UnsupportedOperationException();
    }

}
