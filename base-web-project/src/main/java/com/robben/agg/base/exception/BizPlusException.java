package com.robben.agg.base.exception;

import com.alibaba.cola.exception.BizException;
import com.robben.agg.base.contants.BbResultEnum;


public class BizPlusException extends BizException {
    public BizPlusException(String errMessage) {
        super(errMessage);
    }

    public BizPlusException(String errCode, String errMessage) {
        super(errCode, errMessage);
    }

    public BizPlusException(String errMessage, Throwable e) {
        super(errMessage, e);
    }

    public BizPlusException(String errorCode, String errMessage, Throwable e) {
        super(errorCode, errMessage, e);
    }

    public BizPlusException(BbResultEnum b) {
        super(b.getCode(), b.getMessage());
    }


}
