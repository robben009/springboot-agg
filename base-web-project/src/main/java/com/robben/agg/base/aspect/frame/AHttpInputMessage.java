package com.robben.agg.base.aspect.frame;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


@Slf4j
public class AHttpInputMessage implements HttpInputMessage {
    private HttpInputMessage inputMessage;
    private String charset;


    public AHttpInputMessage(HttpInputMessage inputMessage, String charset) {
        this.inputMessage = inputMessage;
        this.charset = charset;
    }

    @Override
    public InputStream getBody() {
        try {
            String content = IoUtil.read(inputMessage.getBody(), charset);
            JSONObject jsonObject = JSONObject.parseObject(content);
            log.info("获取了请求内容={}", jsonObject.toJSONString());

            String a = "{\"rightsInstanceCode\":\"1\",\"cardCode\":\"2\",\"verifyType\":\"3\"}";

//			signatureCheck(jsonObject, appConfig);
//			String operatorId = jsonObject.getString(IN_OPERATOR_ID);
//			if (!StringUtils.equals(operatorId, appConfig.getSgmwNotifyOperatorId())) {
//				throw new TcecException(PublicErrorEnum.SYSTEM_ERROR, "无效的运营商信息");
//			}
//			String decryptBody = AesUtil.decrypt(jsonObject.getString("data"),
//					appConfig.getSgmwNotifyDataSecret(),
//					appConfig.getSgmwNotifyDataSecretIv());
//			return new ByteArrayInputStream(decryptBody.getBytes(charset));

            return new ByteArrayInputStream(a.getBytes(charset));
        } catch (Exception e) {
            log.warn("error:{}", e.getMessage(), e);
            throw new RuntimeException("");
        }
    }

    @Override
    public HttpHeaders getHeaders() {
        return inputMessage.getHeaders();
    }


}
