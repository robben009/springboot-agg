package com.es.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.RequestConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

@Slf4j
@Component
public class ESConfig {

    @Value("${elasticsearch.user}")
    private  String esUserName;
    @Value("${elasticsearch.password}")
    private  String esPassword;
    @Value("${elasticsearch.host}")
    private  String esHost;
    @Value("${elasticsearch.keystorePath}")
    private  String eskeystorePath;


    public RestHighLevelClient getClient() {
        String[] hosts = esHost.split(",");
        HttpHost[] httpHostsList = new HttpHost[hosts.length];

        for(int i = 0; i < hosts.length; ++i) {
            HttpHost h = new HttpHost(hosts[i].split(":")[0], Integer.parseInt(hosts[i].split(":")[1]), "https");
            httpHostsList[i] = h;
        }

        log.debug("RestHighLevelClient_httpHostsList:{}", JSON.toJSONString(httpHostsList));
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(esUserName, esPassword));
        Path caCertificatePath = Paths.get(eskeystorePath);

        try {
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            InputStream is = Files.newInputStream(caCertificatePath);
            Throwable var11 = null;

            Certificate trustedCa;
            try {
                trustedCa = factory.generateCertificate(is);
            } catch (Throwable var21) {
                var11 = var21;
                throw var21;
            } finally {
                if (is != null) {
                    if (var11 != null) {
                        try {
                            is.close();
                        } catch (Throwable var20) {
                            var11.addSuppressed(var20);
                        }
                    } else {
                        is.close();
                    }
                }

            }

            KeyStore trustStore = KeyStore.getInstance("pkcs12");
            trustStore.load((InputStream)null, (char[])null);
            trustStore.setCertificateEntry("ca", trustedCa);
            SSLContextBuilder sslContextBuilder = SSLContexts.custom().loadTrustMaterial(trustStore, (TrustStrategy)null);
            SSLContext sslContext = sslContextBuilder.build();
            RestClientBuilder builder = RestClient.builder(httpHostsList);
            builder.setHttpClientConfigCallback((httpClientBuilder) -> {
                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider).setSSLContext(sslContext).setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
            });
            builder.setRequestConfigCallback(new RequestConfigCallback() {
                public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                    return requestConfigBuilder.setConnectTimeout(5000000).setSocketTimeout(6000000);
                }
            });
            RestHighLevelClient restHighLevelClient = new RestHighLevelClient(builder);
            return restHighLevelClient;
        } catch (Exception e) {
           e.printStackTrace();
        }

        return null;
    }



    private static void close(RestHighLevelClient client) {
        try {
            client.close();
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }


//    public static void main(String[] args) {
//        try {
//            RestHighLevelClient rc = getClient();
//            Request request = new Request("POST", "http://10.0.81.188:9200/risk-20210327/_search?size=1");
//            try {
//                Response response = rc.getLowLevelClient().performRequest(request);
//                String result = EntityUtils.toString(response.getEntity(), "UTF-8");
//
//                System.out.println(result);
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                rc.close();
//            }
//        } catch (Exception var11) {
//            var11.printStackTrace();
//        }
//
//    }

}

