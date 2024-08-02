package com.robben.agg.base.resp;

import com.robben.agg.base.contants.BbResultEnum;
import lombok.Data;

@Data
public class ResponseResult {
    private Integer code;
    private String msg;

    public ResponseResult() {
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(BbResultEnum bbResultEnum) {
        this.code = bbResultEnum.getCode();
        this.msg = bbResultEnum.getMessage();
    }

}
