package com.robben.controller;

import com.robben.common.ParamVaildReqVo;
import com.robben.model.req.UploadFil2Req;
import com.robben.common.ResponseEntityDto;
import com.robben.common.UnifiedReply;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Api(tags = "swagger示例区")
@RestController
@RequestMapping("/swg")
public class SwaggerController extends UnifiedReply {
    /**
     * name 指定参数名
     * value 指定参数的文档名 可以直接只写value文档名,参数的名称默认会自动填写
     * @ApiParam("id2")默认填值是参数的文档名称
     */
    @ApiOperation(value = "hello2")
    @PostMapping("hello2")
    public String hello2(@ApiParam("id2") Integer id,
                         @ApiParam(name = "name",value = "名字",required = true) String name,
                         @ApiParam(value = "年龄") Integer age) {
        log.info("hello2方法执行_id:{},name:{},age:{}",id,name,age);
        return id + "#" + name + "#" + age;
    }


    @ApiOperation(value = "hello3")
    @PostMapping("/hello3")
    public String hello3(@RequestBody ParamVaildReqVo reqVo) {
        return "1";
    }


    @ApiOperation(value = "hello4", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping
    public ResponseEntityDto<?> hello4(@ApiParam("文件名") @RequestParam String fileName,
                                              @ApiParam("文件描述") @RequestParam String fileDesc,
                                              @ApiParam("文件") @RequestPart("file") MultipartFile file) {
        // 处理上传逻辑
        log.info("文件大小为:{}",file.getSize());
        return buildSuccesResp(file.getSize());
    }


    @ApiOperation(value = "hello5", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping("/hello5")
    public ResponseEntityDto<?> hello5(UploadFil2Req req){
        return buildSuccesResp();
    }

}
