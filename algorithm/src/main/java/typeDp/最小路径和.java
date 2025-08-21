package typeDp;

/**
 * 给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
 * 说明：每次只能向下或者向右移动一步。
 *
 * 输入:
 * [
 *   [1,3,1],
 *   [1,5,1],
 *   [4,2,1]
 * ]
 * 输出: 7
 * 解释: 因为路径 1→3→1→1→1 的总和最小。
 */
public class 最小路径和 {

    public static int minPathSum(int[][] grid) {
        int rows = grid.length;       // 网格的行数
        if (rows < 1) {
            return 0;                 // 如果网格为空，直接返回 0
        }

        int cols = grid[0].length;    // 网格的列数
        int[][] dp = new int[rows][cols];  // 定义 dp 数组，保存最小路径和

        // 初始化起点
        dp[0][0] = grid[0][0];

        // 填充第一行（只能从左边到达当前点）
        for (int j = 1; j < cols; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }

        // 填充第一列（只能从上边到达当前点）
        for (int i = 1; i < rows; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }

        // 填充剩余的 dp 表
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }

        // 返回右下角的最小路径和
        return dp[rows - 1][cols - 1];
    }

    // 测试主程序
    public static void main(String[] args) {
        // 示例测试用例
        int[][] grid = {
                {1, 3, 1},
                {1, 5, 1},
                {4, 2, 1}
        };

        System.out.println("最小路径和为: " + minPathSum(grid)); // 输出: 7
    }


}
