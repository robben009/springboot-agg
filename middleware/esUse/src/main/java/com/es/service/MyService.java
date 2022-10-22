package com.es.service;

import com.alibaba.fastjson.JSONObject;
import com.es.model.Person;
import com.es.model.SearchParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class MyService {

    @Autowired
    private RestClient client;

    /**
     * 根据id查询文档内容
     *
     * @param param
     * @return
     */
    public JSONObject getDataById(SearchParam param) {
        //拼接查询内容
        String body = param.getIndex() + "/" + param.getType() + "/" + param.getId();
        Request request = new Request("GET", body);
        JSONObject jsonObject = new JSONObject();
        try {
            Response response = client.performRequest(request);
            String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
            JSONObject json = JSONObject.parseObject(responseBody);
            //获取我们需要的内容
            Object source = json.get("_source");
            jsonObject = JSONObject.parseObject(source.toString());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return jsonObject;
    }

    /**
     * 添加文档
     *
     * @param param
     * @return
     */
    public ResponseEntity add(SearchParam param) {
        Person person = new Person();
        person.setName("朱元璋");
        person.setAddr("安徽凤阳");
        person.setBirthday("1328-10-29");
        person.setId("1234567890");
        //以bean的id为该文档的id,以便查询
        String body = param.getIndex() + "/" + param.getType() + "/" + person.getId();
        String responseBody = "";
        try {
            Request request = new Request("POST", body);
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(person);
            //设置请求体并指定ContentType和编码格式
            request.setEntity(new NStringEntity(jsonObject.toString(), "UTF-8"));
            Response response = client.performRequest(request);
            //获取响应体
            responseBody = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    /**
     * 根据id更新文档内容
     *
     * @param param
     * @return
     */
    public ResponseEntity update(SearchParam param) {
        Person person = new Person();
        person.setName("朱棣");
        person.setAddr("南京");
        person.setBirthday("1360-05-02");
        //需要更新对应的id
        String body = param.getIndex() + "/" + param.getType() + "/" + param.getId() + "/_update";
        String responseBody = "";
        try {
            //构造http请求
            Request request = new Request("POST", body);
            JSONObject json = (JSONObject) JSONObject.toJSON(person);
            JSONObject jsonObject = new JSONObject();
            //将数据由"doc"包住，以便内部识别
            jsonObject.put("doc", json);
            request.setEntity(new NStringEntity(jsonObject.toString(), "UTF-8"));
            Response response = client.performRequest(request);
            //获取响应体
            responseBody = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }


    /**
     * 根据id删除文档
     *
     * @param param
     * @return
     */
    public ResponseEntity delete(SearchParam param) {
        String body = param.getIndex() + "/" + param.getType() + "/" + param.getId();
        String responseBody = "";
        try {
            Request request = new Request("DELETE", body);
            // 执行HTTP请求
            Response response = client.performRequest(request);
            // 获取结果
            responseBody = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }


}