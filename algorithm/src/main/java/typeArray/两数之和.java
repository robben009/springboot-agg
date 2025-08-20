package typeArray;

import java.util.HashMap;

/**
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 *
 * 给定 nums = [2, 7, 11, 15], target = 9
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 */
public class 两数之和 {

    public int[] twoSum(int[] nums, int target) {
        int[] result = new int[2]; // 用于存储结果索引
        HashMap<Integer, Integer> map = new HashMap<>(); // 存储数值和对应索引

        // 遍历数组，查找每个数的补数
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i]; // 补数 = 目标值 - 当前数值

            // 检查补数是否已在 HashMap 中
            if (map.containsKey(complement)) {
                result[0] = map.get(complement); // 取出补数的索引
                result[1] = i; // 当前索引
                return result; // 返回结果
            }

            // 如果补数不存在，将当前值和索引存入 HashMap
            map.put(nums[i], i);
        }

        // 如果未找到结果，返回一个空数组 (该情况在本题目假定中不会出现)
        return new int[0];
    }

}
