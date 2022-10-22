package com.es.dao;

import com.es.config.ESConfig;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ESBaseDaoImpl implements ESBaseDao {
    @Autowired
    private ESConfig eSConfig;

    @Override
    public String getByJsonString(String type,String url, String jsonstring){
        String result = "";
        RestHighLevelClient rc = null;
        try {
            rc = eSConfig.getClient();
            Request request = new Request(type, url);
            request.setJsonEntity(jsonstring);
            Response response =  rc.getLowLevelClient().performRequest(request);
            result = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (rc != null) {
                try {
                    rc.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
