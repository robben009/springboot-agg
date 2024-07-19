package com.robben.agg.base.config;

import com.alibaba.fastjson2.JSONObject;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.ReadContext;
import org.springframework.context.annotation.Bean;

/**
 * Description： 用来判断json中是否包含某个值,以前是抛异常现在是返回null
 * 见测试类
 * Author: robben
 * Date: 2021/4/7 16:02
 */
@org.springframework.context.annotation.Configuration
public class JsonPathConfig {

    @Bean("JsonPathConf")
    public Configuration configuration() {
        return Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
    }


    public static void main(String[] args) {
        String a = "{\n" +
                "      \"name\" : \"john\",\n" +
                "      \"gender\" : \"male\"\n" +
                "   }";
        JSONObject d = JSONObject.parseObject(a);

//        ReadContext ctx = JsonPath.using(jsonPathConf).parse(d);
        ReadContext ctx = JsonPath.parse(d);
        String gender0 = ctx.read("$.name");
        System.out.println(gender0);
        String gender1 = ctx.read("$.name1");
        System.out.println(gender1);
    }

}
