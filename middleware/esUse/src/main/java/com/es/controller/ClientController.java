package com.es.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.es.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@Tag(name = "client操作")
@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @PostMapping("/add")
    @Operation(summary = "插入数据")
    public IndexResponse add(@RequestBody Product product) throws IOException {
        IndexRequest<Product> request = IndexRequest.of(i -> i
                .index("products")
                .id(product.getId())
                .document(product)
        );

        IndexResponse response = elasticsearchClient.index(request);
        return response;
    }




}