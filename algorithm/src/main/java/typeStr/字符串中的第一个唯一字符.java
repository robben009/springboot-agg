package typeStr;

/**
 * 给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1
 *
 * s = "leetcode"
 * 返回 0.
 * s = "loveleetcode",
 * 返回 2.
 */
public class 字符串中的第一个唯一字符 {

    public static int firstUniqChar(String s) {
        // 定义一个数组，用来记录每个字母首次出现的索引
        int[] arr = new int[26]; // 假设输入字符串只包含小写字母 a-z
        for (int i = 0; i < 26; i++) {
            arr[i] = -1; // 初始化为 -1，表示字母还未出现
        }

        // 遍历字符串，记录每个字母的索引
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (arr[c - 'a'] == -1) {
                // 如果该字母还未记录，存储当前索引
                arr[c - 'a'] = i;
            } else {
                // 如果字母重复出现，将其置为 -2，表示该字符不唯一
                arr[c - 'a'] = -2;
            }
        }

        // 遍历字符串，寻找第一个唯一字符
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i); // 当前字符
            if (arr[c - 'a'] >= 0) {
                // 如果该字符的索引不为 -1 或 -2，说明这是第一个唯一字符
                return i;
            }
        }

        // 如果没有找到唯一字符，返回 -1
        return -1;
    }

    public static void main(String[] args) {
        // 测试用例
        String s1 = "leetcode";
        System.out.println("第一个不重复字符的索引: " + firstUniqChar(s1)); // 输出: 0

    }

}
