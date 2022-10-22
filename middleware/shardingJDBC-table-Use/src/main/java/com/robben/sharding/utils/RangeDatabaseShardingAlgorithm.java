package com.robben.sharding.utils;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/11/16 16:59
 */
import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.ArrayList;
import java.util.Collection;


public class RangeDatabaseShardingAlgorithm implements RangeShardingAlgorithm<Long> {
    /**
     * 范围分片算法
     *
     * @param availableTargetNames 所有配置的库列表
     * @param rangeShardingValue   分片值，也就是save_time_com的值，范围分片算法必须提供开始时间和结束时间
     * @return                     所匹配库的结果
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> rangeShardingValue) {

        final ArrayList<String> result = new ArrayList<>();
        Range<Long> range = rangeShardingValue.getValueRange();
        long startMillisecond = range.lowerEndpoint();
        long endMillisecond = range.upperEndpoint();

        // 起始年和结束年
        int startYear = Integer.parseInt(SDateUtil.getYearByMillisecond(startMillisecond));
        int endYear = Integer.parseInt(SDateUtil.getYearByMillisecond(endMillisecond));

        return startYear == endYear ? theSameYear(String.valueOf(startYear), availableTargetNames, result) : differentYear(startYear, endYear, availableTargetNames, result);
    }

    // 同一年，说明只需要一个库
    private Collection<String> theSameYear(String startTime, Collection<String> availableTargetNames, ArrayList<String> result) {

        for (String availableTargetName : availableTargetNames) {
            if (availableTargetName.endsWith(startTime)) result.add(availableTargetName);
        }
        return result;
    }


    // 跨年
    private Collection<String> differentYear(int startYear, int endYear, Collection<String> availableTargetNames, ArrayList<String> result) {

        for (String availableTargetName : availableTargetNames) {
            for (int i = startYear; i <= endYear; i++) {
                if (availableTargetName.endsWith(String.valueOf(i))) result.add(availableTargetName);
            }
        }
        return result;
    }

}

