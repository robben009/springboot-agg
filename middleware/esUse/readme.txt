es的java客户端有两种

一种是和spring集成的ElasticsearchTemplate,一般是java对象映射成索引文档，以便于操作对象来更新文档
(https://spring.io/projects/spring-data-elasticsearch#overview)


一种是es原生的客户端ElasticsearchClient
(https://www.elastic.co/guide/en/elasticsearch/com.chat.client/java-api-com.chat.client/current/indexing.html
官网下拉ELASTIC STACK,找Elasticsearch Clients)

还有一种是直接使用easy-es这个开源框架来使用
创建索引模版
