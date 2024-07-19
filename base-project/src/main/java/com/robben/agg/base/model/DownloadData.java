package com.robben.model;

import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Date;

public class DownloadData {
    @ExcelProperty("字符串标题")
    private String string;
    @ExcelProperty("日期标题")
    private Date date;
    @ExcelProperty("数字标题")
    private Double doubleData;

}
