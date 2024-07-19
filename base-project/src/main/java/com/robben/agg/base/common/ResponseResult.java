package com.robben.agg.base.common;

import com.robben.model.ResultEnum;
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

    public ResponseResult(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMessage();
    }

}
