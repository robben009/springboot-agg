package typeDp;

/**
 * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 *
 * 输入: [-2,1,-3,4,-1,2,1,-5,4],
 * 输出: 6
 * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
 */
public class 最大子序和 {

    public int maxSubArray(int[] nums) {
        // 如果数组长度小于 1，直接返回 0
        if (nums.length < 1) {
            return 0;
        }

        // 定义动态规划数组 dp，用于存储最大子数组和
        int[] dp = new int[nums.length];
        dp[0] = nums[0]; // 初始化第一个元素的dp值
        int result = nums[0]; // 初始化结果为第一个元素值

        // 动态规划：迭代计算最大子数组和
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]); // 当前元素是否加入之前的子数组
            result = Math.max(dp[i], result); // 更新最终结果值
        }

        return result; // 返回全局最大子数组和
    }

}
