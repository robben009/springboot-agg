package com.robben.utils;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.zip.ZipOutputStream;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/2/7 21:02
 */
@Slf4j
public class FileTools {


    public static boolean upload(MultipartFile file, String filePath){
        try {
            File f = multipartFileToFile(file);
            String result = HttpRequest.post("ConstantMp.cloudServerPath")
                    .header(Header.CONTENT_TYPE, "multipart/form-data; boundary=<calculated when request is sent>")
                    .header(Header.AUTHORIZATION,"Basic *****")
                    .form("file",f)
                    .form("filePath",filePath)
                    .execute().body();
            log.info("上传文件filePath:{}结果:{} ",filePath,result);
            f.delete();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传文件filePath:{}异常:",filePath,e);
            return false;
        }
        return true;
    }


    //MultipartFile转File(此方法会生成临时文件,完事后需要手动删除文件 f.delete())
    public static File multipartFileToFile(MultipartFile file) throws Exception {
        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        String taskId = "asdfasdfasdf";
        File tempDir = createTempDir(taskId);
        File srcZipFile = new File(tempDir, taskId + ".zip");
        ZipOutputStream zipOut = createZipOut(srcZipFile);

        File file = new File(taskId+".job");
        // FileWriter writer = new FileWriter(file, true);
        BufferedWriter writer =new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(file), "UTF-8"));
        System.out.println(1111);
    }

    public static File createTempDir(final String parent) throws IOException {
        final File tempDir = new File(System.getProperty("java.io.tmpdir"), parent);
        if (tempDir.exists() && tempDir.isDirectory()) {
//            FileUtils.deleteDirectory(tempDir);
        }else{
            tempDir.mkdir();
            tempDir.deleteOnExit();
        }
        return tempDir;
    }

    public static ZipOutputStream createZipOut(final File file) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(file);
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(outputStream));
        return out;
    }

}
