package arrayType;

import java.util.List;


public class 原地删除 {

    /**
     * 给定一个数组 nums 和一个值 val，你需要原地移除所有数值等于 val 的元素，返回移除后数组的新长度。
     * 不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
     * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
     */
    public int removeElement(List<Integer> nums, int val) {
        // 循环遍历数组的每个元素
        for (int i = 0; i < nums.size(); ) {
            // 如果当前元素等于待移除的值
            if (nums.get(i) == val) {
                // 从列表中移除该元素
                nums.remove(i);
            } else {
                // 否则，继续检查下一个元素（i 增加）
                i++;
            }
        }
        // 返回修改后的数组长度
        return nums.size();
    }


    /**
     * 给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素只出现一次。
     * @param nums
     * @return
     */
    public int removeDuplicates(List<Integer> nums) {
        // 遍历数组，检查当前元素和下一个元素是否相同
        for (int i = 0; i + 1 < nums.size();) {
            // 如果当前元素和下一个元素相等
            if (nums.get(i).equals(nums.get(i + 1))) {
                // 删除下一个相同元素
                nums.remove(i + 1);
            } else {
                // 否则，只移动指针到下一位置
                i++;
            }
        }
        // 返回调整后的数组的大小
        return nums.size();
    }


}
