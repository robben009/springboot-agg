package arrayType;

/**
 * 给定一个由整数组成的非空数组所表示的非负整数，在该数的基础上加一。
 * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。你可以假设除了整数 0 之外，这个整数不会以零开头。
 * 输入: [1,2,3]
 * 输出: [1,2,4]
 * 解释: 输入数组表示数字 123。
 */
public class 加一 {

    public static int[] plusOne(int[] digits) {
        int addon = 0; // 表示进位

        // 倒序遍历数组（从低位到高位）
        for (int i = digits.length - 1; i >= 0; i--) {
            // 加上进位
            digits[i] = digits[i] + addon;
            addon = 0; // 重置进位为 0

            // 如果是最低位，直接加 1
            if (i == digits.length - 1) {
                digits[i]++;
            }

            // 检查当前位是否需要进位
            if (digits[i] == 10) {
                digits[i] = 0; // 当前位设置为 0
                addon = 1; // 设置进位为 1
            }
        }

        // 如果遍历结束仍有进位（高位需要额外的 1）
        if (addon == 1) {
            // 创建一个新数组，长度比原数组大 1
            int[] result = new int[digits.length + 1];
            result[0] = 1; // 最高位设置为 1
            // 复制原数组内容到新数组的后续位置
            System.arraycopy(digits, 0, result, 1, digits.length);
            return result;
        }

        // 如果没有进位，直接返回原数组
        return digits;
    }

    public static void main(String[] args) {

        // 测试用例 1：普通情况
        int[] digits1 = {1, 2, 3};
        int[] result1 = plusOne(digits1);
        System.out.println("结果: "); // 应输出 [1, 2, 4]
        printArray(result1);

        // 测试用例 2：全是 9 的情况
        int[] digits2 = {9, 9, 9};
        int[] result2 = plusOne(digits2);
        System.out.println("结果: "); // 应输出 [1, 0, 0, 0]
        printArray(result2);

        // 测试用例 3：最后一位达到进位
        int[] digits3 = {4, 9, 9};
        int[] result3 = plusOne(digits3);
        System.out.println("结果: "); // 应输出 [5, 0, 0]
        printArray(result3);

        // 测试用例 4：只有一位数组
        int[] digits4 = {0};
        int[] result4 = plusOne(digits4);
        System.out.println("结果: "); // 应输出 [1]
        printArray(result4);
    }

    // 打印数组方法
    private static void printArray(int[] array) {
        for (int num : array) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
