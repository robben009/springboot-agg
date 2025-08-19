package arrayType;


import java.util.Arrays;

/**
 * 将一个给定字符串根据给定的行数，以从上往下、从左到右进行 Z 字形排列。比如输入字符串为 “LEETCODEISHIRING” 行数为 3 时，排列如下：
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 */
public class Z字形变换 {

    public static String convert(String s, int numRows) {
        if (numRows == 1) {
            return s;
        }

        String[] arr = new String[numRows];

        Arrays.fill(arr, "");

        char[] chars = s.toCharArray();

        /**
         * 周期 Z字形的元素分布是周期性重复，一个周期包含 numRows 个“从上往下的字符”和 numRows-2 个“从下往上的字符
         *   0       6
         *   1    5
         *   2  4
         *   3
         */

        int period = numRows * 2 - 2;
        for (int i = 0; i < chars.length; i++) {
            int mod = i % period;
            if (mod < numRows) {
                arr[mod] = arr[mod] + chars[i];
            } else {
                arr[period - mod] = arr[period - mod] + chars[i];
            }
        }

        StringBuilder res = new StringBuilder();
        for (String ch : arr) {
            res.append(ch);
        }
        return res.toString();
    }

    public static void main(String[] args) {
        String a = "LEETCODEISHIRING";
        System.out.println(convert(a,3));
    }

}
