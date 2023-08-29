package com.robben;

import com.alibaba.fastjson2.JSONObject;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/7/2 15:19
 */
@SpringBootTest
public class JsonPathUseTest {

    @Autowired
    @Qualifier("JsonPathConf")
    private Configuration jsonPathConf;

    @Test
    public void test1(){
        String a = "{\n" +
                "      \"name\" : \"john\",\n" +
                "      \"gender\" : \"male\"\n" +
                "   }";
        JSONObject d = JSONObject.parseObject(a);

        ReadContext ctx = JsonPath.using(jsonPathConf).parse(d);

        String gender0 = ctx.read("$.name");
        System.out.println(gender0);
        String gender1 = ctx.read("$.name1");
        System.out.println(gender1 == null);

        JsonPath.parse(d).put("$.store.book[0]", "category", "111111111");

    }

}
