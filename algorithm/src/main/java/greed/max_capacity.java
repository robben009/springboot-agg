package greed;

/**
 * 输入一个数组
 *  ，其中的每个元素代表一个垂直隔板的高度。数组中的任意两个隔板，以及它们之间的空间可以组成一个容器。
 * 容器的容量等于高度和宽度的乘积（面积），其中高度由较短的隔板决定，宽度是两个隔板的数组索引之差。
 * 请在数组中选择两个隔板，使得组成的容器的容量最大，返回最大容量。
 */
public class max_capacity {

    /* 最大容量：贪心 */
    int maxCapacity(int[] ht) {
        // 初始化 i, j，使其分列数组两端
        int i = 0, j = ht.length - 1;
        // 初始最大容量为 0
        int res = 0;
        // 循环贪心选择，直至两板相遇
        while (i < j) {
            // 更新最大容量
            int cap = Math.min(ht[i], ht[j]) * (j - i);
            res = Math.max(res, cap);
            // 向内移动短板
            if (ht[i] < ht[j]) {
                i++;
            } else {
                j--;
            }
        }
        return res;
    }

}
