package com.robben.agg.base.aspect.validParam.self;


import com.robben.agg.base.dao.entity.UserInfo;

public class Test {
    private static CheckParamsParser paramsParser = new CheckParamsParser();

    public static void main(String[] args) throws Exception {
        UserInfo UserInfo = new UserInfo();
        UserInfo.setId(333L);

        paramsParser.check(UserInfo);
    }

}
