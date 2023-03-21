package com.robben.controller;

import com.robben.common.ParamVaildReqVo;
import com.robben.common.ResponseEntityDto;
import com.robben.common.UnifiedReply;
import com.robben.entity.UserInfoEntity;
import com.robben.model.req.UploadFil2Req;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Tag(name = "swagger示例区")
@RestController
@RequestMapping("/swg")
public class SwaggerController extends UnifiedReply {


    @Operation(summary = "普通body请求+Param+Header+Path")
    @Parameters({
            @Parameter(name = "id",description = "文件id",in = ParameterIn.PATH),
            @Parameter(name = "token",description = "请求token",required = true,in = ParameterIn.HEADER),
            @Parameter(name = "name",description = "文件名称",required = true,in=ParameterIn.QUERY)
    })
    @PostMapping("/bodyParamHeaderPath/{id}")
    public String bodyParamHeaderPath(@PathVariable("id") String id,
                                      @RequestHeader("token") String token,
                                      @RequestParam("name") String name,
                                      @RequestBody UserInfoEntity userInfoEntity){
        return "1234";
    }


    @Operation(summary = "hello2")
    @PostMapping("hello2")
    public String hello2( Integer id, String name, Integer age) {
        log.info("hello2方法执行_id:{},name:{},age:{}",id,name,age);
        return id + "#" + name + "#" + age;
    }


    @Operation(summary = "hello3")
    @PostMapping("/hello3")
    public String hello3(@RequestBody ParamVaildReqVo reqVo) {
        return "1";
    }


    @Operation(summary = "hello4", description = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping
    public ResponseEntityDto<?> hello4( @RequestParam String fileName,
                                              @RequestParam String fileDesc,
                                              @RequestPart("file") MultipartFile file) {
        // 处理上传逻辑
        log.info("文件大小为:{}",file.getSize());
        return buildSuccesResp(file.getSize());
    }


    @Operation(summary = "hello5", description = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping("/hello5")
    public ResponseEntityDto<?> hello5(UploadFil2Req req){
        return buildSuccesResp();
    }

}
