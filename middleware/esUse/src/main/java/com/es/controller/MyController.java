package com.es.controller;

import com.alibaba.fastjson.JSONObject;
import com.es.config.ESConfig;
import com.es.model.SearchParam;
import com.es.service.MyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Api(tags = "ElasticSearch")
@RestController
@RequestMapping("/es")
public class MyController {

    @Autowired
    private MyService service;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;
    @Autowired
    private RestHighLevelClient rClient;
    @Autowired
    private ESConfig eSConfig;

    @PostMapping("/search1")
    @ApiOperation("根据id查询ES对应的数据")
    public void searchMatch(String key, String value) throws Exception {
        SearchRequest searchRequest = new SearchRequest("indexName");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery(key,value));
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = eSConfig.getClient().search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(JSONObject.toJSON(response));

        SearchHit[] hits = response.getHits().getHits();
        for(SearchHit hit: hits){
            Object player = JSONObject.parseObject(hit.getSourceAsString(),Object.class);
        }
    }


    @PostMapping("/search")
    @ApiOperation("根据id查询ES对应的数据")
    public JSONObject getDataById(@RequestBody SearchParam param) throws IOException {
        GetRequest getRequest = new GetRequest("indexName","id");
        GetResponse response = eSConfig.getClient().get(getRequest,RequestOptions.DEFAULT);

        return service.getDataById(param);
    }

    @PostMapping("/add")
    @ApiOperation("往ES里插入数据")
    public ResponseEntity add(@RequestBody SearchParam param) throws IOException {
        IndexRequest request = new IndexRequest("index_name").id("id").source("param");
        IndexResponse response = eSConfig.getClient().index(request, RequestOptions.DEFAULT);
        return service.add(param);
    }

    @PostMapping("/update")
    @ApiOperation("根据id更新文档内容")
    public ResponseEntity update(@RequestBody SearchParam param) throws IOException {
        UpdateRequest request = new UpdateRequest("indexName","id").doc("doc");
        UpdateResponse response = eSConfig.getClient().update(request,RequestOptions.DEFAULT);

        return service.update(param);
    }

    @PostMapping("/delete")
    @ApiOperation("根据id更新文档内容")
    public ResponseEntity delete(@RequestBody SearchParam param) throws IOException {
        DeleteRequest request = new DeleteRequest("indexName","id");
        DeleteResponse response = eSConfig.getClient().delete(request, RequestOptions.DEFAULT);

        return service.delete(param);
    }


}