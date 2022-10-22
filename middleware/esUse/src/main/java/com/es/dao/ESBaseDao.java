package com.es.dao;

public interface ESBaseDao<T> {


     /**
      * 自定义ES的curl请求
      * @param type (http的请求方式,POST/GET/DELETE....)
      * @param url (操作ES的url,例如"test_anno/_search")
      * @param jsonstring (请求的参数,JSON字符串的形式)
      * @return
      * @throws Exception
      */
     String getByJsonString(String type, String url, String jsonstring);

}
