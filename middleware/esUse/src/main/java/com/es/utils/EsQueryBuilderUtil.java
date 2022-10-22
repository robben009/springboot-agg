package com.es.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class EsQueryBuilderUtil {
    //limit
    private int size;
    //offset
    private int from;
    //select
    private Object source;
    //script
    private Object script;
    //折叠
    private Object queryString;
    //条件
    private List<Object> mustList;
    //条件
    private List<Object> mustNotList;
    //排序
    private List<Object> sort;
    //折叠
    private Object collapse;
    //聚合
    private String agg1StrFlag;
    private String agg2StrFlag;
    private String agg3StrFlag;
    private HashMap<String, Object> agg1Map;
    private HashMap<String, Object> agg2Map;
    private HashMap<String, Object> agg3Map;

    public EsQueryBuilderUtil() {
        init();
    }

    //初始化变量
    private void init() {
        mustList = new ArrayList<>();
        mustNotList = new ArrayList<>();
        sort = new ArrayList<>();
        size = 0;
        from = 0;
        source = null;
        collapse = null;
        queryString = null;
        agg1StrFlag = "";
        agg2StrFlag = "";
        agg3StrFlag = "";
        agg1Map = new HashMap<>();
        agg2Map = new HashMap<>();
        agg3Map = new HashMap<>();
    }

    /**
     * description: 分页
     *
     * @Param: int limit
     * @Return: EsQueryBuilderUtil
     */
    public EsQueryBuilderUtil setLimit(int limit) {
        if (limit >= 0) {
            size = limit;
        }
        return this;
    }

    /**
     * description: 分页
     *
     * @Param: int page
     * @Return: EsQueryBuilderUtil
     */
    public EsQueryBuilderUtil setPage(int page) {
        if (page > 0) {
            from = size * (page - 1);
        }
        return this;
    }

    /**
     * description: select
     *
     * @Param: [field 查询字段]
     * @Return: EsQueryBuilderUtil
     */
    public EsQueryBuilderUtil setSource(String field) {
        if ("".equals(field)) {
            return this;
        }
        source = field;
        return this;
    }

    public EsQueryBuilderUtil setSource(List<String> field) {
        if (field.size() == 0) {
            return this;
        }
        source = field;
        return this;
    }

    public EsQueryBuilderUtil setSourceIncludes(List<String> field) {
        if (field.size() == 0) {
            return this;
        }
        JSONObject object = new JSONObject();
        object.put("includes", field);
        source = object;
        return this;
    }

    public EsQueryBuilderUtil setSourceExcludes(List<String> field) {
        if (field.size() == 0) {
            return this;
        }
        JSONObject object = new JSONObject();
        object.put("excludes", field);
        source = object;
        return this;
    }

    /**
     * description: 折叠
     *
     * @Param: [field]
     * @Return: EsQueryBuilderUtil
     */
    public EsQueryBuilderUtil setCollapse(String field) {
        if ("".equals(field)) {
            return this;
        }
        JSONObject object = new JSONObject();
        object.put("field", field);
        collapse = object;
        return this;
    }

    /**
     * description: 排序
     *
     * @Param: [field 排序字段, type 升序降序]
     * @Return: EsQueryBuilderUtil
     */
    public EsQueryBuilderUtil setSort(String field, String type) {
        if ("".equals(field)) {
            return this;
        }
        String finalType = "desc";
        if (type.equals("asc")) {
            finalType = type;
        }
        JSONObject object = new JSONObject();
        object.put(field, finalType);
        ArrayList<Object> list = new ArrayList<>();
        list.add(object);
        sort = list;
        return this;
    }

    /**
     * description: 增加 range 条件
     *
     * @Param: [field es字段, min, max, isMust 是否是must]
     * @Return: EsQueryBuilderUtil
     */
    public EsQueryBuilderUtil setQueryRange(String field, long min, long max, boolean isMust) {
        if (min < 0 || max < 0 || "".equals(field)) {
            return this;
        }
        HashMap<String, Long> map = new HashMap<>();
        map.put("gte", min);
        map.put("lte", max);
        JSONObject object = new JSONObject();
        JSONObject object2 = new JSONObject();
        object2.put(field, map);
        object.put("range", object2);
        if (isMust) {
            mustList.add(object);
        } else {
            mustNotList.add(object);
        }
        return this;
    }

    /**
     * description: 增加 range 条件
     *
     * @Param: [field es字段, min, isMust 是否是must]
     * @Return: EsQueryBuilderUtil
     */
    public EsQueryBuilderUtil setQueryRange(String field, int min, boolean isMust) {
        if (min < 0 || "".equals(field)) {
            return this;
        }
        HashMap<String, Integer> map = new HashMap<>();
        map.put("gte", min);
        JSONObject object = new JSONObject();
        JSONObject object2 = new JSONObject();
        object2.put(field, map);
        object.put("range", object2);
        if (isMust) {
            mustList.add(object);
        } else {
            mustNotList.add(object);
        }
        return this;
    }

    /**
     * description: 增加 term 条件
     *
     * @Param: [field es字段, value 条件, isMust 是否是must]
     * @Return: EsQueryBuilderUtil
     */
    public EsQueryBuilderUtil setQueryTerm(String field, Boolean value, boolean isMust) {
        return setTermBySingValue(field, value, isMust);
    }

    public EsQueryBuilderUtil setQueryTerm(String field, String value, boolean isMust) {
        if ("".equals(value)) {
            return this;
        }
        return setTermBySingValue(field, value, isMust);
    }

    public EsQueryBuilderUtil setQueryTerm(String field, int value, boolean isMust) {
        if (value < 0) {
            return this;
        }
        return setTermBySingValue(field, value, isMust);
    }

    private EsQueryBuilderUtil setTermBySingValue(String field, Object value, boolean isMust) {
        JSONObject object = new JSONObject();
        JSONObject object2 = new JSONObject();
        JSONObject object3 = new JSONObject();
        object3.put("value", value);
        object2.put(field, object3);
        object.put("term", object2);
        if (isMust) {
            mustList.add(object);
        } else {
            mustNotList.add(object);
        }
        return this;
    }

    /**
     * description: 增加 terms 条件
     *
     * @Param: [field es字段, value 条件, isMust 是否是must]
     * @Return: EsQueryBuilderUtil
     */
    public EsQueryBuilderUtil setQueryTerm(String field, List<?> value, boolean isMust) {
        if (value.size() == 0) {
            return this;
        }
        JSONObject object = new JSONObject();
        JSONObject object2 = new JSONObject();
        //列表查询 - terms
        object2.put(field, value);
        object.put("terms", object2);
        if (isMust) {
            mustList.add(object);
        } else {
            mustNotList.add(object);
        }
        return this;
    }

    public EsQueryBuilderUtil setQueryString(String q) {
        JSONObject object = new JSONObject();
        JSONObject object2 = new JSONObject();
        //列表查询 - terms
        object2.put("query", q);
        object.put("query_string", object2);
        queryString = object;
        return this;
    }

    /**
     * description: 增加 regexp 条件
     *
     * @Param: [field es字段, value 条件, isMust 是否是must]
     * @Return: EsQueryBuilderUtil
     */
    public EsQueryBuilderUtil setQueryRegexp(String field, String value, boolean isMust) {
        if ("".equals(value)) {
            return this;
        }
        JSONObject object = new JSONObject();
        JSONObject object2 = new JSONObject();
        object2.put(field, String.format(".*%s.*", value));
        object.put("regexp", object2);
        if (isMust) {
            mustList.add(object);
        } else {
            mustNotList.add(object);
        }
        return this;
    }

    /**
     * description: 增加 wildcard 条件
     *
     * @Param: [field es字段, value 条件, isMust 是否是must]
     * @Return: EsQueryBuilderUtil
     */
    public EsQueryBuilderUtil setQueryWildcard(String field, String value, boolean isMust) {
        if ("".equals(value)) {
            return this;
        }
        JSONObject object = new JSONObject();
        JSONObject object2 = new JSONObject();
        JSONObject object3 = new JSONObject();
        object3.put("value", String.format("*%s*", value));
        object2.put(field, object3);
        object.put("wildcard", object2);
        if (isMust) {
            mustList.add(object);
        } else {
            mustNotList.add(object);
        }
        return this;
    }

    /************************************ 聚合 *******************
     * description: 按时间段聚合
     */
    public EsQueryBuilderUtil set1AggsHist(String field, String unit, String aggStr) {
        JSONObject object2 = new JSONObject();
        HashMap<String, String> map = new HashMap<>();
        map.put("field", field);
        map.put("fixed_interval", unit);
        // map.put("time_zone", "+08:00");
        // map.put("format", "yyyy-MM-dd HH:mm:ss");
        object2.put("date_histogram", map);
        agg1Map.put(aggStr, object2);
        agg1StrFlag = aggStr;
        return this;
    }
    public EsQueryBuilderUtil set1AggsHist(String field, String unit, String aggStr, boolean fixTimeZone) {
        JSONObject object2 = new JSONObject();
        HashMap<String, String> map = new HashMap<>();
        map.put("field", field);
        map.put("fixed_interval", unit);
        if(fixTimeZone){
            map.put("time_zone", "+08:00");
        }
        // map.put("format", "yyyy-MM-dd HH:mm:ss");
        object2.put("date_histogram", map);
        agg1Map.put(aggStr, object2);
        agg1StrFlag = aggStr;
        return this;
    }

    public EsQueryBuilderUtil set1AggsCalendar(String field, String unit, String aggStr) {
        JSONObject object2 = new JSONObject();
        HashMap<String, String> map = new HashMap<>();
        map.put("field", field);
        map.put("calendar_interval", unit);
        map.put("time_zone", "+08:00");
        map.put("format", "yyyy-MM-dd HH:mm:ss");
        object2.put("date_histogram", map);
        agg1Map.put(aggStr, object2);
        agg1StrFlag = aggStr;
        return this;
    }

    /**
     * description: 按唯一性聚合
     */
    public EsQueryBuilderUtil set1AggsCardinality(String field, String aggStr) {
        agg1StrFlag = aggStr;
        return getCardinalityObj(field, aggStr, agg1Map);
    }

    public EsQueryBuilderUtil set2AggsCardinality(String field, String aggStr) {
        agg2StrFlag = aggStr;
        return getCardinalityObj(field, aggStr, agg2Map);
    }

    public EsQueryBuilderUtil set3AggsCardinality(String field, String aggStr) {
        agg3StrFlag = aggStr;
        return getCardinalityObj(field, aggStr, agg3Map);
    }

    private EsQueryBuilderUtil getCardinalityObj(String field, String aggStr, HashMap<String, Object> aggList) {
        JSONObject object2 = new JSONObject();
        HashMap<String, String> map = new HashMap<>();
        map.put("field", field);
        object2.put("cardinality", map);
        aggList.put(aggStr, object2);
        return this;
    }

    /**
     * description: 按min/max/avg统计聚合
     */
    public EsQueryBuilderUtil set1AggsStats(String field, String aggStr) {
        agg1StrFlag = aggStr;
        return getStatsObj(field, aggStr, agg1Map);
    }

    public EsQueryBuilderUtil set2AggsStats(String field, String aggStr) {
        agg2StrFlag = aggStr;
        return getStatsObj(field, aggStr, agg2Map);
    }

    public EsQueryBuilderUtil set3AggsStats(String field, String aggStr) {
        agg3StrFlag = aggStr;
        return getStatsObj(field, aggStr, agg3Map);
    }

    private EsQueryBuilderUtil getStatsObj(String field, String aggStr, HashMap<String, Object> aggList) {
        JSONObject object2 = new JSONObject();
        HashMap<String, String> map = new HashMap<>();
        map.put("field", field);
        object2.put("stats", map);
        aggList.put(aggStr, object2);
        return this;
    }

    /**
     * description: 按 sum 统计
     */
    public EsQueryBuilderUtil set1AggsSum(String field, String aggStr) {
        agg1StrFlag = aggStr;
        return getSumObj(field, aggStr, agg1Map);
    }

    public EsQueryBuilderUtil set2AggsSum(String field, String aggStr) {
        agg2StrFlag = aggStr;
        return getSumObj(field, aggStr, agg2Map);
    }

    public EsQueryBuilderUtil set3AggsSum(String field, String aggStr) {
        agg3StrFlag = aggStr;
        return getSumObj(field, aggStr, agg3Map);
    }

    private EsQueryBuilderUtil getSumObj(String field, String aggStr, HashMap<String, Object> aggList) {
        JSONObject object2 = new JSONObject();
        HashMap<String, String> map = new HashMap<>();
        map.put("field", field);
        object2.put("sum", map);
        aggList.put(aggStr, object2);
        return this;
    }

    /**
     * description: 按分组统计（group by）
     */
    public EsQueryBuilderUtil set1AggsTerms(String field, int size, String aggStr) {
        agg1StrFlag = aggStr;
        return getTermsObj(field, size, aggStr, agg1Map);
    }

    public EsQueryBuilderUtil set2AggsTerms(String field, int size, String aggStr) {
        agg2StrFlag = aggStr;
        return getTermsObj(field, size, aggStr, agg2Map);
    }

    public EsQueryBuilderUtil set3AggsTerms(String field, int size, String aggStr) {
        agg3StrFlag = aggStr;
        return getTermsObj(field, size, aggStr, agg3Map);
    }

    private EsQueryBuilderUtil getTermsObj(String field, int size, String aggStr, HashMap<String, Object> aggList) {
        JSONObject object2 = new JSONObject();
        HashMap<String, Object> map = new HashMap<>();
        map.put("field", field);
        map.put("size", size);
        // map.put("exclude", "");
        object2.put("terms", map);
        aggList.put(aggStr, object2);
        return this;
    }

    public EsQueryBuilderUtil set1AggsTermsNotEmpty(String field, int size, String aggStr) {
        agg1StrFlag = aggStr;
        return getTermsObjNotEmpty(field, size, aggStr, agg1Map);
    }

    public EsQueryBuilderUtil set2AggsTermsNotEmpty(String field, int size, String aggStr) {
        agg2StrFlag = aggStr;
        return getTermsObjNotEmpty(field, size, aggStr, agg2Map);
    }

    public EsQueryBuilderUtil set3AggsTermsNotEmpty(String field, int size, String aggStr) {
        agg3StrFlag = aggStr;
        return getTermsObjNotEmpty(field, size, aggStr, agg3Map);
    }

    private EsQueryBuilderUtil getTermsObjNotEmpty(String field, int size, String aggStr,
                                                   HashMap<String, Object> aggList) {
        JSONObject object2 = new JSONObject();
        HashMap<String, Object> map = new HashMap<>();
        map.put("field", field);
        map.put("size", size);
        map.put("exclude", "");
        object2.put("terms", map);
        aggList.put(aggStr, object2);
        return this;
    }

    public EsQueryBuilderUtil set1AggsTermsNotEmpty(String field, int size, String aggStr, String excludeValue) {
        agg1StrFlag = aggStr;
        return getTermsObjNotEmpty(field, size, aggStr, excludeValue, agg1Map);
    }

    public EsQueryBuilderUtil set2AggsTermsNotEmpty(String field, int size, String aggStr, String excludeValue) {
        agg2StrFlag = aggStr;
        return getTermsObjNotEmpty(field, size, aggStr, excludeValue, agg2Map);
    }

    public EsQueryBuilderUtil set3AggsTermsNotEmpty(String field, int size, String aggStr, String excludeValue) {
        agg3StrFlag = aggStr;
        return getTermsObjNotEmpty(field, size, aggStr, excludeValue, agg3Map);
    }

    private EsQueryBuilderUtil getTermsObjNotEmpty(String field, int size, String aggStr, String excludeValue, HashMap<String, Object> aggList) {
        JSONObject object2 = new JSONObject();
        HashMap<String, Object> map = new HashMap<>();
        map.put("field", field);
        map.put("size", size);
        ArrayList<String> exclude = new ArrayList<>();
        exclude.add("");
        exclude.add(excludeValue);
        map.put("exclude", exclude);
        object2.put("terms", map);
        aggList.put(aggStr, object2);
        return this;
    }

    /**
     * description: 按分组统计（group by）+ minDocCount
     */
    public EsQueryBuilderUtil set1AggsTerms(String field, int size, int minDocCount, String aggStr) {
        agg1StrFlag = aggStr;
        return getTermsObj(field, size, minDocCount, aggStr, agg1Map);
    }

    public EsQueryBuilderUtil set2AggsTerms(String field, int size, int minDocCount, String aggStr) {
        agg2StrFlag = aggStr;
        return getTermsObj(field, size, minDocCount, aggStr, agg2Map);
    }

    public EsQueryBuilderUtil set3AggsTerms(String field, int size, int minDocCount, String aggStr) {
        agg3StrFlag = aggStr;
        return getTermsObj(field, size, minDocCount, aggStr, agg3Map);
    }

    private EsQueryBuilderUtil getTermsObj(String field, int size, int minDocCount, String aggStr, HashMap<String, Object> aggList) {
        JSONObject object2 = new JSONObject();
        HashMap<String, Object> map = new HashMap<>();
        map.put("field", field);
        map.put("size", size);
        map.put("min_doc_count", minDocCount);
        object2.put("terms", map);
        aggList.put(aggStr, object2);
        return this;
    }

    public EsQueryBuilderUtil updateByScript(String field, String value) {
        JSONObject object = new JSONObject();
        JSONObject object2 = new JSONObject();
        //列表查询 - terms
        object2.put(field, value);
        object.put("params", object2);
        object.put("lang", "painless");
        object.put("source", String.format("ctx._source.%s=params.%s",field,field));
        script = object;
        return this;
    }


    /**
     * description: 返回查询 body
     *
     * @Return: java.lang.String
     */
    public String getEsBody() {
        return getFinalBody(true);
    }

    public String getEsBody(boolean needPage) {
        return getFinalBody(needPage);
    }

    private String getFinalBody(boolean needPage) {
        JSONObject object = new JSONObject();
        //增加分页
        if (needPage && size >= 0) {
            object.put("size", size);
        }
        if (needPage && from >= 0) {
            object.put("from", from);
        }
        //排序
        if (sort.size() > 0) {
            object.put("sort", sort);
        }
        //指定字段
        if (source != null) {
            object.put("_source", source);
        }
        //折叠
        if (collapse != null) {
            object.put("collapse", collapse);
        }
        //script
        if (script != null) {
            object.put("script", script);
        }
        //============ aggs ============//
        // System.out.println("agg3Map:" + agg3Map);
        // System.out.println("agg2Map:" + agg2Map);
        // System.out.println("agg1Map:" + agg1Map);
        if (agg3Map.size() > 0) {
            JSONObject o2 = (JSONObject) agg2Map.get(agg2StrFlag);
            o2.put("aggs", agg3Map);
            agg2Map.put(agg2StrFlag, o2);
        }
        if (agg2Map.size() > 0) {
            JSONObject o1 = (JSONObject) agg1Map.get(agg1StrFlag);
            o1.put("aggs", agg2Map);
            agg1Map.put(agg1StrFlag, o1);
        }
        if (agg1Map.size() > 0) {
            object.put("aggs", agg1Map);
        }
        //============ query ============//
        JSONObject object2 = new JSONObject();
        JSONObject object3 = new JSONObject();
        if (mustList.size() > 0) {
            object3.put("must", mustList);
        }
        if (mustNotList.size() > 0) {
            object3.put("must_not", mustNotList);
        }
        if (object3.containsKey("must") || object3.containsKey("must_not")) {
            object2.put("bool", object3);
            object.put("query", object2);
        }
        //queryString
        if(queryString != null){
            object.put("query", queryString);
        }
        return object.toJSONString();
    }


}
