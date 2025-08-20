package typeArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。注意：答案中不可以包含重复的三元组。
 * <p>
 * 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
 * 满足要求的三元组集合为：
 * [
 * [-1, 0, 1],
 * [-1, -1, 2]
 * ]
 */
public class 三数之和 {

    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);

        List<List<Integer>> res = new ArrayList();
        for (int i = 0; i < nums.length; i++) {
            int target = 0 - nums[i];
            int left = i + 1;
            int rigth = nums.length - 1;

            if (nums[i] > 0) {
                break;
            }

            //第一次循环和连续相同的数值需要跳过
            if (i == 0 || nums[i] != nums[i - 1]) {
                while (left < rigth) {
                    if (nums[left] + nums[rigth] == target) {
                        res.add(Arrays.asList(nums[i], nums[left], nums[rigth]));

                        while (left < rigth && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        while (left < rigth && nums[rigth] == nums[rigth - 1]) {
                            rigth--;
                        }

                        left++;
                        rigth--;
                    } else if (nums[left] + nums[rigth] < target) {
                        left++;
                    } else {
                        rigth--;
                    }
                }
            }
        }

        return res;
    }

    public static void main(String[] args) {
        // 测试用例 1：正常情况，有多个三元组
        int[] nums1 = {-1, 0, 1, 2, -1, -4};
        System.out.println("测试用例 1：输入：" + Arrays.toString(nums1));
        List<List<Integer>> result1 = threeSum(nums1);
        System.out.println("结果：" + result1);  // [[-1, -1, 2], [-1, 0, 1]]
        System.out.println();
    }


    /**
     * 采取固定一个数，同时用双指针来查找另外两个数的方式。所以初始化时，我们选择固定第一个元素（当然，这一轮走完了，这个蓝框框我们就要也往前移动），
     * 同时将下一个元素和末尾元素分别设上 left 和 right 指针。
     * 现在已经找到了三个数，当然是计算其三值是否满足三元组。但是这里因为我们已经排好了序，如果固定下来的数（上面蓝色框框）本身就大于 0，那三数之和必然无法等于 0。
     *
     * 然后自然用脚指头也能想到，我们需要移动指针。现在我们的排序就发挥出用处了，如果和大于0，那就说明 right 的值太大，需要左移。如果和小于0，那就说明 left 的值太小，需要右移
     *
     */

}
