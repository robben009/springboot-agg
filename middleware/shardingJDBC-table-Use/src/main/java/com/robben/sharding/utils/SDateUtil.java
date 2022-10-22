package com.robben.sharding.utils;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/11/16 16:58
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class SDateUtil {


    private static final SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

    private static final SimpleDateFormat monthFormat = new SimpleDateFormat("MM");

    private static final SimpleDateFormat yearJoinMonthFormat = new SimpleDateFormat("yyyyMM");


    public static String getYearByMillisecond(long millisecond) {

        return yearFormat.format(new Date(millisecond));
    }


    public static String getMonthByMillisecond(long millisecond) {

        return monthFormat.format(new Date(millisecond));
    }


    public static String getYearJoinMonthByMillisecond(long millisecond) {

        return yearJoinMonthFormat.format(new Date(millisecond));
    }


    public static List<String> getMonthBetween(String minDate, String maxDate) {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");// 格式化为年月
        try {
            Calendar min = Calendar.getInstance();
            Calendar max = Calendar.getInstance();

            min.setTime(sdf.parse(minDate));
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

            max.setTime(sdf.parse(maxDate));
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

            Calendar curr = min;
            while (curr.before(max)) {
                result.add(sdf.format(curr.getTime()));
                curr.add(Calendar.MONTH, 1);
            }

            // 实现排序方法
            Collections.sort(result, new Comparator<Object>() {
                @Override
                public int compare(Object o1, Object o2) {
                    String str1 = (String) o1;
                    String str2 = (String) o2;
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
                    Date date1 = null;
                    Date date2 = null;
                    try {
                        date1 = format.parse(str1);
                        date2 = format.parse(str2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (date2.compareTo(date1) > 0) {
                        return -1;
                    }
                    return 1;
                }
            });
        }catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }



}
