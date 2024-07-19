package com.robben.agg.base.annotation.validParam.self;


import com.robben.agg.base.entity.UserInfoEntity;

public class Test {
    private static CheckParamsParser paramsParser = new CheckParamsParser();

    public static void main(String[] args) throws Exception {
        UserInfoEntity UserInfoEntity = new UserInfoEntity();
        UserInfoEntity.setId(333L);

        paramsParser.check(UserInfoEntity);
    }

}
