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
public final class BwpResponse<T> extends Response implements Serializable {

    /**
     * 返回数据
     */
    private T data;

    /**
     * 分布式链路请求ID
     */
    protected String traceId;


    public static <T> BwpResponse<T> of(T data) {
        BwpResponse<T> response = new BwpResponse<>();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    public static <T> BwpResponse<T> build(Boolean result, String errCode, String errMsg, T data) {
        BwpResponse<T> response = new BwpResponse<>();
        response.setSuccess(result);
        response.setErrCode(errCode);
        response.setErrMessage(errMsg);
        response.setData(data);
        return response;
    }


    public static BwpResponse buildSuccess() {
        BwpResponse<?> response = new BwpResponse<>();
        response.setSuccess(true);
        return response;
    }


    public static BwpResponse buildFailure(String errCode, String errMessage) {
        BwpResponse response = new BwpResponse();
        response.setSuccess(false);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }


}
