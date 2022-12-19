package com.robben.utils;

import com.carrotsearch.sizeof.RamUsageEstimator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 压缩String的工具类
 */
@Slf4j
public class CompressUtil {
    /**
     * 使用gzip压缩字符串
     * @param str 要压缩的字符串
     * @return 压缩后的字符串
     */
    public static String compress(String str) {
        if (str == null || str.length() <= 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(out)) {
            gzip.write(str.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error("字符串压缩失败str:{}，错误信息:{}", str, e.getMessage());
            throw new RuntimeException("字符串压缩失败");
        }
        return Base64.encodeBase64String(out.toByteArray());
    }

    /**
     * 使用gzip解压缩
     * @param compressedStr 压缩字符串
     * @return 解压后的字符串
     */
    public static String uncompress(String compressedStr) {
        if (compressedStr == null || compressedStr.length() <= 0) {
            return compressedStr;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in;
        GZIPInputStream gzip = null;
        byte[] compressed;
        String decompressed;
        try {
            compressed = Base64.decodeBase64(compressedStr);
            in = new ByteArrayInputStream(compressed);
            gzip = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int offset;
            while ((offset = gzip.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString(StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            log.error("字符串解压失败compressedStr:{}，错误信息:{}", compressedStr, e.getMessage());
            throw new RuntimeException("字符串解压失败");
        } finally {
            if (gzip != null) {
                try {
                    gzip.close();
                } catch (IOException ignored) {
                }
            }
            try {
                out.close();
            } catch (IOException ignored) {
            }
        }
        return decompressed;
    }

    public static void main(String[] args) {
        String str = "123";
        System.out.println(RamUsageEstimator.humanSizeOf(str));

        System.out.println(RamUsageEstimator.humanSizeOf(CompressUtil.compress(str)));

    }


}
