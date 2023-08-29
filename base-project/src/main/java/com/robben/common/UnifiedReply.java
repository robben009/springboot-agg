package com.robben.common;

/**
 * Description： 统一回复体
 * Author: robben
 * Date: 2020/8/17 13:37
 */
public abstract class UnifiedReply {

    public <T> ResponseEntityDto<T> buildSuccesResp() {
        return new ResponseEntityDto<>();
    }

    public <T> ResponseEntityDto buildSuccesResp(T t) {
        ResponseEntityDto resp = new ResponseEntityDto<>();
        resp.setData(t);
        return resp;
    }

    public <T> ResponseEntityDto buildSuccesResp(T t, String msg) {
        ResponseEntityDto resp = new ResponseEntityDto<>();
        resp.setData(t);
        resp.setMessage(msg);
        return resp;
    }

    //失败-----
    public <T> ResponseEntityDto buildFailResp() {
        return new ResponseEntityDto<>(false);
    }

    public <T> ResponseEntityDto<T> buildFailResp(String msg) {
        ResponseEntityDto<T> resp = new ResponseEntityDto<>(false);
        resp.setMessage(msg);
        return resp;
    }

    public <T> ResponseEntityDto<T> buildFailResp(Integer code,String msg) {
        ResponseEntityDto<T> resp = new ResponseEntityDto<>(false);
        resp.setCode(code);
        resp.setMessage(msg);
        return resp;
    }

    public <T> ResponseEntityDto buildFailResp(T t) {
        ResponseEntityDto resp = new ResponseEntityDto<>(false);
        resp.setData(t);
        return resp;
    }

    public <T> ResponseEntityDto buildFailResp(T t, String msg) {
        ResponseEntityDto resp = new ResponseEntityDto<>(false);
        resp.setData(t);
        resp.setMessage(msg);
        return resp;
    }

}
