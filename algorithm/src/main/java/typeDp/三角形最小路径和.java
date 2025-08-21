package typeDp;

import java.util.List;

/**
 * 给定一个三角形，找出自顶向下的最小路径和。每一步只能移动到下一行中相邻的结点上。
 *
 * [
 *      [2],
 *     [3,4],
 *    [6,5,7],
 *   [4,1,8,3]
 * ]
 *
 * 则自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）
 */
public class 三角形最小路径和 {


    public static int minimumTotal(List<List<Integer>> triangle) {
        // 如果三角形的层数小于 1，返回 0
        if (triangle.size() < 1) {
            return 0;
        }

        // 如果三角形只有一层，直接返回顶点值
        if (triangle.size() == 1) {
            return triangle.get(0).get(0);
        }

        // 创建 DP 数组，用来保存每个位置的最小路径和
        int[][] dp = new int[triangle.size()][triangle.size()];

        // 初始化顶点的 DP 值
        dp[0][0] = triangle.get(0).get(0);

        // 初始化第二层的 DP 值
        dp[1][0] = triangle.get(1).get(0) + dp[0][0];
        dp[1][1] = triangle.get(1).get(1) + dp[0][0];

        // 自顶向下动态计算 DP 值
        for (int i = 2; i < triangle.size(); i++) {
            for (int j = 0; j < triangle.get(i).size(); j++) {
                // 如果是第一个元素，只能从上一层的第一个元素到达
                if (j == 0) {
                    dp[i][j] = dp[i - 1][0] + triangle.get(i).get(0);
                }
                // 如果是最后一个元素，只能从上一层的最后一个元素到达
                else if (j == triangle.get(i).size() - 1) {
                    dp[i][j] = dp[i - 1][j - 1] + triangle.get(i).get(j);
                }
                // 中间元素，取来自上一层两个元素的较小值
                else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1], dp[i - 1][j]) + triangle.get(i).get(j);
                }
            }
        }

        // 找出最后一层的最小值
        int result = Integer.MAX_VALUE;
        for (int k : dp[triangle.size() - 1]) {
            result = Math.min(result, k);
        }

        return result; // 返回结果
    }

    public static void main(String[] args) {
        // 示例测试用例
        List<List<Integer>> triangle = List.of(
                List.of(2),
                List.of(3, 4),
                List.of(6, 5, 7),
                List.of(4, 1, 8, 3)
        );

        System.out.println(minimumTotal(triangle)); // 输出：11
    }

}
