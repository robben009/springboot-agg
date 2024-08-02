package com.robben.agg.base.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.RandomUtil;
import com.robben.agg.base.req.FileResp;
import com.robben.agg.base.resp.MapEnumUserReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Tag(name = "swagger示例区")
@RestController
@RequestMapping("/swg")
public class SwaggerController extends UnifiedReply {

    @Operation(summary = "枚举可用值")
    @PostMapping("/mo")
    public ResponseEntity<MapEnumUserReq> mo(@RequestBody MapEnumUserReq mapEnumUserReq){
        return ResponseEntity.ok(mapEnumUserReq);
    }

    @Operation(summary = "文件上传-带参数")
    @Parameters({
            @Parameter(name = "token",description = "请求token",required = true,in = ParameterIn.HEADER),
            @Parameter(name = "id",description = "文件id",in = ParameterIn.PATH),
            @Parameter(name = "file",description = "文件",required = true,in=ParameterIn.DEFAULT,ref = "file"),
            @Parameter(name = "name",description = "文件名称",required = true),
    })
    @PostMapping("/uploadParamHeader")
    public ResponseEntity<FileResp> uploadParamHeader(@RequestHeader("token") String token,
                                                      @PathVariable("id")String id,
                                                      @RequestParam("file")MultipartFile file,
                                                      @RequestParam("name") String name){
        FileResp fileResp=new FileResp();
        fileResp.setSize(file.getSize());
        fileResp.setName(file.getOriginalFilename());
        fileResp.setSuccess(true);
        fileResp.setRandom(RandomUtil.randomString(12)+",receiveName:"+name+",id:"+id);
        return ResponseEntity.ok(fileResp);
    }


    @Operation(summary = "图片预览" )
    @GetMapping(value = "/image",produces = "image/jpg")
    public void image(HttpServletResponse response) throws IOException {
        //创建临时文件
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(150, 40, 4, 80);
        response.addHeader("Content-Type",MediaType.IMAGE_PNG_VALUE);
        lineCaptcha.write(response.getOutputStream());
    }

}
