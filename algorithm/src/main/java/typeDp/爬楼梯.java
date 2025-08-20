package typeDp;

/**
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？ 注意：给定 n 是一个正整数。
 *
 * 输入： 2   输出： 2  解释： 有两种方法可以爬到楼顶。
 * 1.  1 阶 + 1 阶
 * 2.  2 阶
 *
 * 输入： 3   输出： 3  解释： 有三种方法可以爬到楼顶。
 * 1.  1 阶 + 1 阶 + 1 阶
 * 2.  1 阶 + 2 阶
 * 3.  2 阶 + 1 阶
 */
public class 爬楼梯 {
    public static int climbStairs(int n) {
        // 如果只有 1 阶楼梯，直接返回 1
        if (n == 1) {
            return 1;
        }

        // 创建一个数组 dp 用来存储子问题的解
        int[] dp = new int[n + 1];
        dp[1] = 1; // 第一阶楼梯的方式
        dp[2] = 2; // 第二阶楼梯的方式

        // 动态规划计算每一阶楼梯的爬法
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        // 返回到达第 n 阶楼梯的方法总数
        return dp[n];
    }

    public static void main(String[] args) {
        // 测试用例
        System.out.println(climbStairs(5)); // 输出：8
    }

    /**
     * 状态转移方程
     * dp[n]=dp[n-1]+dp[n-2]
     */
}
