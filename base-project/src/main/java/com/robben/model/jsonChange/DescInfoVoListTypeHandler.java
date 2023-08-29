package com.robben.model.jsonChange;

import com.alibaba.fastjson2.TypeReference;
import com.robben.entity.DescInfoVo;

import java.util.List;


public class DescInfoVoListTypeHandler extends ListTypeHandler<DescInfoVo> {

    @Override
    protected TypeReference<List<DescInfoVo>> specificType() {
        return new TypeReference<List<DescInfoVo>>() {};
    }

}