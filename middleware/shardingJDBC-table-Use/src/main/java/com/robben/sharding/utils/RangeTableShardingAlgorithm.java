package com.robben.sharding.utils;

/**
 * Description： TODO
 * Author: hujingzheng
 * Date: 2021/11/16 16:59
 */

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


public class RangeTableShardingAlgorithm implements RangeShardingAlgorithm<Long> {
    

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> rangeShardingValue) {
        Range<Long> range = rangeShardingValue.getValueRange();
        Long startSecond = range.lowerEndpoint();
        Long endSecond = range.upperEndpoint();

        List<String> tableResult = new ArrayList<>();
        String sm = DateUtil.format(new Date(startSecond),"yyyyMM");
        String em = DateUtil.format(new Date(endSecond),"yyyyMM");
        List<String> list = AdDateUtil.getMonthBetween(sm, em);

        String tableName = "user";
        for (String s : list) {
            tableResult.add(tableName + "_" + s);
        }

        //校验表是否存在
        boolean resultFlag = true;
        for (String s : tableResult) {
            boolean flag = false;
            for (String a : availableTargetNames) {
                if(s.equals(a)){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                resultFlag = false;
                break;
            }
        }

        if(resultFlag){
            return tableResult;
        }
        throw new UnsupportedOperationException("RangeTableShardingAlgorithm_error:" + JSON.toJSONString(tableResult));
    }


}

