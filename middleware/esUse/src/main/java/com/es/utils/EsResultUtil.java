package com.es.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class EsResultUtil {

    public static List<JSONObject> listResult(JSONObject data) {
        JSONArray jsonArray = data.getJSONObject("hits").getJSONArray("hits");
        List<JSONObject> dataList = new ArrayList<>();
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;

            JSONObject source = jsonObject.getJSONObject("_source");
            source.put("_id", jsonObject.get("_id"));
            source.put("_index", jsonObject.get("_index"));
            source.put("_type", jsonObject.get("_type"));

            dataList.add(source);
        }
        return dataList;
    }

    public static JSONObject oneResult(JSONObject data) {
        List<JSONObject> jsonObjects = listResult(data);
        return jsonObjects.get(0);
    }

}
