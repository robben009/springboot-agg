package com.robben.agg.base.utils;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.robben.agg.base.config.ThreadPool.ThreadPoolTaskExecutorPlus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.zip.ZipOutputStream;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/2/7 21:02
 */
@Slf4j
public class FileTools {

    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

    public static boolean upload(MultipartFile file, String filePath) {
        try {
            File f = multipartFileToFile(file);
            String result = HttpRequest.post("ConstantMp.cloudServerPath")
                    .header(Header.CONTENT_TYPE, "multipart/form-data; boundary=<calculated when request is sent>")
                    .header(Header.AUTHORIZATION, "Basic *****")
                    .form("file", f)
                    .form("filePath", filePath)
                    .execute().body();
            log.info("上传文件filePath:{}结果:{} ", filePath, result);
            f.delete();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传文件filePath:{}异常:", filePath, e);
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



    public static File createTempDir(final String parent) throws IOException {
        final File tempDir = new File(System.getProperty("java.io.tmpdir"), parent);
        if (tempDir.exists() && tempDir.isDirectory()) {
//            FileUtils.deleteDirectory(tempDir);
        } else {
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


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~下载(支持断点下载)~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * 获取连接
     */
    private static HttpURLConnection getHttpUrlConnection(String netUrl) throws Exception {
        URL url = new URL(netUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        // 设置超时时间为3秒
        httpURLConnection.setConnectTimeout(3 * 1000);

        // 防止屏蔽程序抓取而返回403错误
        httpURLConnection.setRequestProperty(
                "User-Agent",
                "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)"
        );

        return httpURLConnection;
    }

    /**
     * 判断连接是否支持断点下载
     */
    private static boolean isSupportRange(String netUrl) throws Exception {
        HttpURLConnection httpURLConnection = getHttpUrlConnection(netUrl);
        String acceptRanges = httpURLConnection.getHeaderField("Accept-Ranges");

        if (StringUtils.isEmpty(acceptRanges)) {
            return false;
        }

        if ("bytes".equalsIgnoreCase(acceptRanges)) {
            return true;
        }

        return false;
    }

    /**
     * 获取远程文件大小
     */
    private static int getFileContentLength(String netUrl) throws Exception {
        HttpURLConnection httpUrlConnection = getHttpUrlConnection(netUrl);
        int contentLength = httpUrlConnection.getContentLength();
        closeHttpUrlConnection(httpUrlConnection);
        return contentLength;
    }

    private static void closeHttpUrlConnection(HttpURLConnection connection) {
        if (connection != null) {
            try {
                // 关闭连接的输入流
                InputStream inputStream = null;
                try {
                    inputStream = connection.getInputStream();
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    // 有些请求可能没有输入流，忽略异常
                }

                // 关闭连接的错误流
                InputStream errorStream = connection.getErrorStream();
                if (errorStream != null) {
                    errorStream.close();
                }

            } catch (IOException e) {
                // 日志或忽略异常
                e.printStackTrace();
            } finally {
                // 断开HTTP连接
                connection.disconnect();
            }
        }
    }




    /**
     * 单线程串行下载
     * 单线程的断点下载比较简单，直接追加就好了
     * @param totalFileSize 文件总大小
     * @param netUrl        文件地址
     * @param N             串行下载分段次数
     */
    private static void segmentDownload(int totalFileSize, String netUrl, int N) throws Exception {
        // 本地文件目录
        String localFilePath = "F:\\test_single_thread.txt";

        // 计算每段文件大小
        int eachFileSize = totalFileSize / N;

        for (int i = 1; i <= N; i++) {
            // 本地文件对象
            File localFile = new File(localFilePath);

            // 获取本地文件长度作为断点下载起点，如果文件为空则start=0
            long start = localFile.length();
            long end = 0;

            if (i == 1) {
                end = eachFileSize;
            } else if (i == N) {
                end = totalFileSize;
            } else {
                end = eachFileSize * i;
            }

            appendFile(netUrl, localFile, start, end);

            System.out.println(String.format("我是第%s次下载，下载片段范围start=%s,end=%s", i, start, end));
        }

        File localFile = new File(localFilePath);
        System.out.println("本地文件大小：" + localFile.length());
    }

    /**
     * 文件尾部追加
     *
     * @param netUrl    地址
     * @param localFile 本地文件
     * @param start     分段开始位置
     * @param end       分段结束位置
     */
    private static void appendFile(String netUrl, File localFile, long start, long end) throws Exception {
        HttpURLConnection httpURLConnection = getHttpUrlConnection(netUrl);
        httpURLConnection.setRequestProperty("Range", "bytes=" + start + "-" + end);

        // 获取远程文件流信息
        InputStream inputStream = httpURLConnection.getInputStream();

        // 本地文件写入流，支持文件追加
        FileOutputStream fos = FileUtils.openOutputStream(localFile, true);

        // 拷贝数据
        IOUtils.copy(inputStream, fos);

        // 关闭流和连接
        fos.close();
        inputStream.close();
        closeHttpUrlConnection(httpURLConnection);
    }


    /**
     * 多线程分组策略
     *
     * 多线程的文件合并方式与单线程不一样，因为多线程是并行下载，每个子线程下载完成的时间是不确定的。这个时候，我们需要使用到java一个核心类：RandomAccessFile。
     * 这个类可以支持随机的文件读写，其中有一个seek函数，可以将指针指向文件任意位置，然后进行读写。
     * 什么意思呢，举个栗子：假如我们开了10个线程，首先第一个下载完成的是线程X，它下载的数据范围是300-400，那么这时我们调用seek函数将指针拨动到300，
     * 然后调用它的write函数将byte写出，这时候300之前都是NULL，300-400之后就是我们插入的数据。这样就可以实现多线程下载和本地写入了。
     *
     * @param netUrl        网络地址
     * @param totalFileSize 文件总大小
     * @param N             线程池数量
     */
    private static void groupDownload(String netUrl, int totalFileSize, int N) throws Exception {
        // 采用闭锁特性来实现最后的文件校验事件
        CountDownLatch countDownLatch = new CountDownLatch(N);

        // 本地文件目录
        String localFilePath = "/Users/hjz/openProjects/springboot-agg/base-web-project/src/main/resources/123123.xlsx";

        int groupSize = totalFileSize / N;
        int start = 0;
        int end = 0;

        for (int i = 1; i <= N; i++) {
            if (i <= 1) {
                start = groupSize * (i - 1);
                end = groupSize * i;
            } else if (i > 1 && i < N) {
                start = groupSize * (i - 1) + 1;
                end = groupSize * i;
            } else {
                start = groupSize * (i - 1) + 1;
                end = totalFileSize;
            }

            System.out.println(String.format("线程%s分配区间范围start=%s, end=%s", i, start, end));

            // 启动分块下载和合并任务
            downloadAndMerge(i, netUrl, localFilePath, start, end, countDownLatch);
        }

        // 等待所有线程完成下载任务
        countDownLatch.await();

        // 校验文件完整性
        validateCompleteness(localFilePath, netUrl);
    }


    /**
     * 文件下载、合并
     *
     * @param threadNum      线程标识
     * @param netUrl         网络文件地址
     * @param localFilePath  本地文件路径
     * @param start          范围请求开始位置
     * @param end            范围请求结束位置
     * @param countDownLatch 闭锁对象
     */
    private static void downloadAndMerge(int threadNum, String netUrl, String localFilePath, int start, int end, CountDownLatch countDownLatch) {
        fixedThreadPool.execute(() -> {
            try {
                HttpURLConnection httpURLConnection = getHttpUrlConnection(netUrl);
                httpURLConnection.setRequestProperty("Range", "bytes=" + start + "-" + end);

                // 获取远程文件流信息
                InputStream inputStream = httpURLConnection.getInputStream();

                RandomAccessFile randomAccessFile = new RandomAccessFile(localFilePath, "rw");

                // 文件写入开始位置指针移动到已经下载位置
                randomAccessFile.seek(start);

                byte[] buffer = new byte[1024 * 10];
                int len = -1;
                while ((len = inputStream.read(buffer)) != -1) {
                    randomAccessFile.write(buffer, 0, len);
                }

                // 关闭资源和连接
                randomAccessFile.close();
                inputStream.close();
                closeHttpUrlConnection(httpURLConnection);

                System.out.println(String.format("下载完成时间%s, 线程：%s, 下载完成: start=%s, end=%s",
                        System.currentTimeMillis(), threadNum, start, end));
            } catch (Exception e) {
                System.out.println(String.format("片段下载异常：线程：%s, start=%s, end=%s", threadNum, start, end));
                e.printStackTrace();
            } finally {
                // 确保闭锁数量减少，无论异常与否
                countDownLatch.countDown();
            }
        });
    }


    /**
     * 校验文件一致性，我们判断Etag和本地文件的md5是否一致
     * 注: Etag携带了双引号
     *
     * @param localFilePath 本地文件路径
     * @param netUrl        远程文件地址
     */
    private static void validateCompleteness(String localFilePath, String netUrl) throws Exception {
        File file = new File(localFilePath);
        InputStream data = null;
        HttpURLConnection httpURLConnection = null;
        try {
            data = new FileInputStream(file);

            // 计算本地文件的MD5值
            String md5 = DigestUtils.md5Hex(data);

            httpURLConnection = getHttpUrlConnection(netUrl);
            String etag = httpURLConnection.getHeaderField("Etag");

            // 去掉etag中的双引号（如果有），便于比较
            if (etag != null) {
                etag = etag.replace("\"", "");
            }

            if (etag != null && etag.equalsIgnoreCase(md5)) {
                System.out.println(String.format("本地文件和远程文件一致，md5 = %s, Etag = %s", md5.toUpperCase(), etag));
            } else {
                System.out.println(String.format("本地文件和远程文件不一致，md5 = %s, Etag = %s", md5.toUpperCase(), etag));
            }
        } finally {
            // 关闭输入流和断开连接
            if (data != null) {
                data.close();
            }
            if (httpURLConnection != null) {
                closeHttpUrlConnection(httpURLConnection);
            }
        }
    }


    public static void main(String[] args) throws Exception {
        groupDownload("http://***:8999/aaa/1231.xlsx", 22491, 3);
    }
}

