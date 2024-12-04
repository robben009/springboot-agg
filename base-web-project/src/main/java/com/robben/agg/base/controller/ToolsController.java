package com.robben.agg.base.controller;

import com.robben.agg.base.aspect.anno.OpenApiCatch;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


@Slf4j
@Tag(name = "其他用法", description = "一些不常用的controller用法")
@RestController
@RequestMapping("/anno")
@RequiredArgsConstructor
@OpenApiCatch
public class ToolsController {

    @Operation(summary = "自己控制输出内容", description = "接口参数可以直接使用OutputStream或Writer类型的参数，这样你的接口可以不用有任何的返回值，直接通过这2个对象进行输出内容")
    @GetMapping("/index")
    public void index(OutputStream os, HttpServletResponse response) throws Exception {
        response.setContentType("text/plain;charset=utf-8");
        os.write("中国🇨🇳".getBytes());
    }

    @Operation(summary = "自行读取请求body", description = "当你需要自己解析处理请求body内容时，你可以将参数定义为InputStream或Reader类")
    @PostMapping("/index2")
    public void index2(InputStream is, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=utf-8");
        StringBuilder sb = new StringBuilder();
        String line = null;
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        response.getWriter().println(sb.toString());
    }

    @Operation(summary = "表达式参数", description = "通常接口参数都是用来接收用户输入的数据，不过你还可以将参数值\"固定\"了，通过@Value注解来获取配置的数据信息")
    @GetMapping("/index3")
    public Object index3(@Value("${pack.controller.params.version:1}") String version) {
        return version;
    }

    @Operation(summary = "body与header一起获取")
    @PostMapping("/index4")
    public Object index4(HttpEntity<User> entity) {
        System.out.printf("headers: %s%n", entity.getHeaders());
        return entity.getBody();
    }

    public record User(Long id, String name) {
    }


}
