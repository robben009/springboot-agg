package com.robben.agg.base.annotation.validParam.self;

public class ParamException extends RuntimeException {

    private String errCode;
    private String errMsg;

    public ParamException(String errCode,String errMessage) {
        super(errMessage);
        this.errCode = errCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
