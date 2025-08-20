package typeArray;

/**
 * 编写一个函数来查找字符串数组中的最长公共前缀。如果不存在公共前缀，则返回””
 */
public class 最长公共前缀 {

    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return ""; // 如果数组为空，返回空串
        }

        String prefix = strs[0]; // 初始化公共前缀为第一个字符串

        for (int i = 1; i < strs.length; i++) { // 遍历字符串数组
            while (strs[i].indexOf(prefix) != 0) { // 当前字符串不以 prefix 开头时
                prefix = prefix.substring(0, prefix.length() - 1); // 减少公共前缀的长度
                if (prefix.isEmpty()) { // 如果公共前缀已空，返回空串
                    return "";
                }
            }
        }
        return prefix; // 返回找到的公共前缀
    }

    public static void main(String[] args) {
        String[] strings = {"flower", "flow", "flight"};
        System.out.println("Longest Common Prefix: " + longestCommonPrefix(strings)); // 输出 "fl"
    }

    /**
     * 输入检查：
     *
     * 首先检查输入的字符串数组是否为 null 或者长度为 0。如果是，则直接返回空字符串。这样可以保证后续操作不出错。
     * 初始化前缀：
     *
     * 将第一个字符串作为初始的公共前缀 prefix。我们将逐步缩短这个前缀，直到找到一个有效的公共前缀。
     * 遍历字符串数组：
     *
     * 从第二个字符串开始，遍历整个字符串数组。
     * 在每一次循环中，使用 indexOf 方法检查当前的字符串是否以 prefix 开头。如果不是，说明 prefix 不是公共前缀，需要缩短。
     * 缩短前缀：
     *
     * 如果当前字符串不以 prefix 开头，就通过 substring 方法缩短 prefix，去掉最后一个字符。
     * 在缩短后，检查 prefix 是否为空字符串。如果为空，说明没有公共前缀，直接返回空字符串。
     * 返回结果：
     *
     * 如果遍历完成后仍然有有效的前缀，则返回这个前缀，作为字符串数组的最长公共前缀。
     * 示例解析
     * 对于输入字符串数组 {"flower", "flow", "flight"}：
     *
     * 初始化 prefix 为 "flower".
     * 与 "flow" 比较，发现 "flow" 不以 "flower" 开头，因此缩短 prefix 变为 "flow".
     * 继续与 "flight" 比较，发现 "flight" 不以 "flow" 开头，缩短成 "fl"。
     * 最后，遍历结束，返回结果就是 "fl"。
     * @param strs
     * @return
     */
}
