package com.robben.agg.base.annotation.validParam.self;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ParamExceptionHandler {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = {ParamException.class})
    public JSONObject handleValidatedException(ParamException e) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Ret",e.getErrCode());
        jsonObject.put("Msg",e.getMessage());

        return jsonObject;
    }


}
