package com.robben.agg.base.resp;

import com.alibaba.cola.dto.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class BbResponse<T> extends Response implements Serializable {

    /**
     * 返回数据
     */
    private T data;

    /**
     * 分布式链路请求ID
     */
    protected String traceId;


    public static <T> BbResponse<T> of(T data) {
        BbResponse<T> response = new BbResponse<>();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    public static <T> BbResponse<T> build(Boolean result, String errCode, String errMsg, T data) {
        BbResponse<T> response = new BbResponse<>();
        response.setSuccess(result);
        response.setErrCode(errCode);
        response.setErrMessage(errMsg);
        response.setData(data);
        return response;
    }


    public static BbResponse buildSuccess() {
        BbResponse<?> response = new BbResponse<>();
        response.setSuccess(true);
        return response;
    }


    public static BbResponse buildFailure(String errCode, String errMessage) {
        BbResponse response = new BbResponse();
        response.setSuccess(false);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }


}
