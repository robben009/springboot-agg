package com.robben.service;

import com.robben.entity.DescInfoListVo;
import com.robben.entity.DescInfoVo;
import com.robben.entity.UserInfoEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class MpUseService {

    public UserInfoEntity createUserByCount(int count) {
        UserInfoEntity vo = new UserInfoEntity();
        vo.setAge(count);
        vo.setName("name-" + count);
        vo.setCreateTime(new Date());
        vo.setSex(count%2 == 0 ? false : true);
        vo.setUpdateTime(new Date());
        vo.setWorkInfo("workinfo" + count);

        //赋值一个json对象,这个对象中还有数组集合
        DescInfoVo dv = new DescInfoVo();
        dv.setAge(count);
        dv.setName("descInfoVoName-" + count);
        dv.setPhone(Arrays.asList("phone-"+count,"tel-" + count));
        vo.setDescInfo(dv);

        //赋值一个json集合对象
        List<DescInfoVo> ld = new ArrayList<>();
        ld.add(dv);
        vo.setDescInfoList(ld);

        //赋值一个对象,对象中有list
        DescInfoListVo descInfoListVo = new DescInfoListVo();
        descInfoListVo.setDlist(ld);
        vo.setDescInfoListVo(descInfoListVo);

        return vo;
    }




}
