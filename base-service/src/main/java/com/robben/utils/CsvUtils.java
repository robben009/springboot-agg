package com.robben.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Description： TODO
 * Author: robben
 * Date: 2020/8/5 14:23
 */
public class CsvUtils {

    /**
     * CSV文件生成方法
     * @param head 文件头
     * @return
     */
    public static void createCSVFile(List<Object> head, List<List<Object>> dataList, HttpServletResponse response) {

        File csvFile = null;
        BufferedWriter csvWtriter = null;
        try {
//            csvFile = new File(outPutPath + File.separator + filename + ".csv");
//            File parent = csvFile.getParentFile();
//            if (parent != null && !parent.exists()) {
//                parent.mkdirs();
//            }
//            csvFile.createNewFile();

            // GB2312使正确读取分隔符","
            csvWtriter = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "GB2312"), 1024);
            // 写入文件头部
            writeRow(head, csvWtriter);

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
//        return csvFile;
    }

    /**
     * 写一行数据方法
     * @param row
     * @param csvWriter
     * @throws IOException
     */
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
