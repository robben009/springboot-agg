package com.robben.agg.base.controller;

import cn.hutool.core.io.FileUtil;
import com.alibaba.excel.EasyExcel;
import com.robben.agg.base.common.ResponseEntityDto;
import com.robben.agg.base.common.UnifiedReply;
import com.robben.agg.base.model.DownloadData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description： TODO
 * Author: robben
 * Date: 2020/8/5 19:48
 */
@Slf4j
@Tag(name = "文件处理")
@RestController
@RequestMapping("/file")
public class FileController extends UnifiedReply {


    @Value("classpath:ak.json")
    private Resource ak;


    @Operation(summary = "上传文件到nginx", description = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping("/uploadFileToNginx")
    public ResponseEntityDto<?> uploadFileToNginx(@RequestPart MultipartFile file) throws IOException {
        log.info("上传文件名字:{}",file.getOriginalFilename());
        file.transferTo(new File("/root/downFile/" + file.getOriginalFilename()));
        return buildSuccesResp();
    }


    @Operation(summary = "上传文件", description = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping("/uploadFile")
    public ResponseEntityDto<?> uploadFile(@RequestParam String fileName, @RequestParam String fileDesc, @RequestPart MultipartFile file) throws IOException {
        // 处理上传逻辑
        log.info("文件大小为:{}",file.getSize());

        //如果是上传CSV文件的话,改行代码直接获取csv文件内容,并数据之间用逗号分隔
        String fileContent = new BufferedReader(new InputStreamReader(file.getInputStream())).lines()
                .collect(Collectors.joining(System.lineSeparator())).replaceAll("\n", ",");

        log.info("文件内容:{}",fileContent);
        return buildSuccesResp(file.getSize());
    }


    @Operation(summary = "下载cvs文件",description = "根据id下载")
    @GetMapping("/downLoadColdData")
    public ResponseEntity<?> downLoadColdData(@RequestParam String id){
        log.info("下载cvs文件:{}",id);
        String path = "/root/temp/" + id + ".csv";

        //根据路径 api返回文件
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", "attachment; filename=" + FileUtil.newFile(path).getName());
        headers.add("filename", FileUtil.newFile(path).getName());

        InputStreamResource resource = new InputStreamResource(FileUtil.getInputStream(path));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(FileUtil.file(path).length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }


    //通过Id下载文件信息记录
    @Operation(summary = "通过Id下载文件信息记录")
    @GetMapping("/downloadFileById")
    public void downloadFileById(@RequestParam int id, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/csv;charset=UTF-8");
        resp.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("file"+id+".csv", "UTF-8"));
        resp.setCharacterEncoding("UTF-8");

        String str = "aaa,bbb,ccc,ddd";
        String[] strMap = str.split(",");

        List<List<Object>> datalist = new ArrayList<List<Object>>();
        List<Object> data = null;
        for (String s : strMap) {
            data = new ArrayList<Object>();
            data.add(s);
            datalist.add(data);
        }

        createCSVFile(datalist,resp);
    }

    //如果本地可以下载但是线上不行请查看官网解决方法,是没安装字体库。可以直接内存修改也可以安装字体库
    @Operation(summary = "下载xls",description = "依赖alibaba-easyexcel")
    @PostMapping("/downLogInfoMj")
    public void downLogInfoMj(HttpServletResponse resp) throws IOException {
        String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");

        resp.setContentType("application/vnd.ms-excel");
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        EasyExcel.write(resp.getOutputStream(), DownloadData.class).sheet("模板").doWrite(data());
    }


    @Operation(summary = "自定义目录文件上传保存",description = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping("uploadSelf")
    public ResponseEntityDto<?> uploadSelf(@RequestPart MultipartFile file,@RequestParam String filePath) {
        if (!FileUtil.isDirectory(filePath)) {
            FileUtil.mkdir(filePath);
        }

        try (InputStream in = file.getInputStream()) {
            FileUtil.writeFromStream(in, filePath + "/" + file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buildSuccesResp();
    }


    @Operation(summary = "获取自定义路径下文件列表")
    @GetMapping("/catalogFiles")
    public ResponseEntityDto<?> catalogFiles(@RequestParam String filePath) {
        // 获取压缩包中所有模块的信息
        List<String> moduleNames = Arrays.stream(FileUtil.ls(filePath))
                .filter(File::isFile).map(File::getName).collect(Collectors.toList());
        return buildSuccesResp(moduleNames);
    }


    @Operation(summary = "自定义下载文件")
    @GetMapping("/downSelf")
    public ResponseEntity<?> downSelf(@RequestParam String filePath) {
        //根据路径 api返回文件
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", "attachment; filename=" + FileUtil.newFile(filePath).getName());
        headers.add("filename", FileUtil.newFile(filePath).getName());

        InputStreamResource resource = new InputStreamResource(FileUtil.getInputStream(filePath));
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(FileUtil.file(filePath).length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }


    @Operation(summary = "获取配置文件信息")
    @GetMapping("/getResourcesFile")
    public String downSelf() {
        Resource resource = new ClassPathResource("static/aaa.txt");
        return resource.getFilename();
    }

    private List<String> data() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add(i+"");
        }
        return list;
    }

    public static void createCSVFile(List<List<Object>> dataList, HttpServletResponse response) {
        BufferedWriter csvWtriter = null;
        try {
            // GB2312使正确读取分隔符","
            csvWtriter = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "GB2312"), 1024);

            // 写入文件内容
            for (List<Object> row : dataList) {
                writeRow(row, csvWtriter);
            }
            csvWtriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvWtriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void writeRow(List<Object> row, BufferedWriter csvWriter) throws IOException {
        if(row == null){
            return;
        }
        // 写入文件头部
        for (Object data : row) {
            StringBuffer sb = new StringBuffer();
            String rowStr = sb.append("\"").append(data).append("\",").toString();
            csvWriter.write(rowStr);
        }
        csvWriter.newLine();
    }

}
