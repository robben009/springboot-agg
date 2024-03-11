package com.es.controller;

import com.es.model.Person;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Tag(name = "template操作")
@RestController
@RequestMapping("/template")
public class TemplateController {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    //不仅仅是插入还可以更新操作,只要指明了字段值就行
    @PostMapping("/add")
    @Operation(summary = "插入数据")
    public Person add(@RequestBody Person person) {
        person.setBirthday(new Date());
        Person response = elasticsearchTemplate.save(person);
        return response;
    }


}