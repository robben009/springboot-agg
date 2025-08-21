package typeDp;

/**
 * 给定一个无序的整数数组，找到其中最长上升子序列的长度。
 *
 * 输入: [10,9,2,5,3,7,101,18]
 * 输出: 4
 * 解释: 最长的上升子序列是 [2,3,7,101]，它的长度是 4。
 */
public class 最长上升子序列 {

    public static int lengthOfLIS(int[] nums) {
        // 如果数组为空，返回 0
        if (nums.length < 1) {
            return 0;
        }

        int n = nums.length;
        int[] dp = new int[n]; // 定义 dp 数组，保存到达每个位置时的最长递增子序列长度
        int result = 1; // 初始化结果为 1（最小的递增子序列长度）

        // 动态规划求解
        for (int i = 0; i < n; i++) {
            dp[i] = 1; // 每个元素单独时，长度至少为 1

            for (int j = 0; j < i; j++) {
                // 如果 nums[j] < nums[i]，则可以扩展递增子序列
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[j] + 1, dp[i]);
                }
            }

            // 更新全局最长递增子序列长度
            result = Math.max(result, dp[i]);
        }

        return result; // 返回最长递增子序列长度
    }

    public static void main(String[] args) {
        // 测试用例
        int[] nums1 = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println("输入: {10, 9, 2, 5, 3, 7, 101, 18}");
        System.out.println("输出: " + lengthOfLIS(nums1)); // 输出: 4 (子序列为 [2, 3, 7, 101])

        int[] nums2 = {0, 1, 0, 3, 2, 3};
        System.out.println("输入: {0, 1, 0, 3, 2, 3}");
        System.out.println("输出: " + lengthOfLIS(nums2)); // 输出: 4 (子序列为 [0, 1, 2, 3])

        int[] nums3 = {7, 7, 7, 7};
        System.out.println("输入: {7, 7, 7, 7}");
        System.out.println("输出: " + lengthOfLIS(nums3)); // 输出: 1 (所有元素相同)
    }

    /**
     * dp[i] = max(dp[j]+1，dp[k]+1，dp[p]+1，…..)
     * 只要满足：
     * nums[i] > nums[j]
     * nums[i] > nums[k]
     * nums[i] > nums[p]
     */

}
