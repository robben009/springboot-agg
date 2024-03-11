package com.es.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ESConfig {
    @Value("${spring.elasticsearch.uris}")
    private String uris;
//    @Value("${spring.elasticsearch.username}")
//    private String username;
//    @Value("${spring.elasticsearch.password}")
//    private String password;


    @Bean
    public ElasticsearchClient esRestClient() {
        int urlIndex = uris.lastIndexOf(":");
        String hostUrl = uris.substring(0, urlIndex).replaceAll("http://","");
        Integer port = Integer.parseInt(uris.substring(urlIndex + 1, uris.length()));

        RestClient restClient = RestClient.builder(new HttpHost(hostUrl, port)).build();

        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        ElasticsearchClient client = new ElasticsearchClient(transport);
        return client;
    }


    //如果存在密码,则用一下配置获取ElasticsearchClient
//    @Bean
//    public ElasticsearchClient esRestClient() {
//        int urlIndex = uris.lastIndexOf(":");
//        String hostUrl = uris.substring(0, urlIndex).replaceAll("http://","");
//        Integer port = Integer.parseInt(uris.substring(urlIndex + 1, uris.length()));
//
//        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
//
//        RestClient restClient = RestClient.builder(new HttpHost(hostUrl, port))
//                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
//                .build();
//
//        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
//
//        ElasticsearchClient client = new ElasticsearchClient(transport);
//        return client;
//    }

}

