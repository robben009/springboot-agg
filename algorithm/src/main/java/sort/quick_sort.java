package sort;

import java.util.Arrays;

/**
 * 快速排序
 */
public class quick_sort {
    /* 元素交换 */
    static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    /* 哨兵划分 */
    static int partition(int[] nums, int left, int right) {
        // 以 nums[left] 为基准数
        int i = left, j = right;
        while (i < j) {
            while (i < j && nums[j] >= nums[left])
                j--;          // 从右向左找首个小于基准数的元素
            while (i < j && nums[i] <= nums[left])
                i++;          // 从左向右找首个大于基准数的元素
            swap(nums, i, j); // 交换这两个元素
        }
        swap(nums, i, left);  // 将基准数交换至两子数组的分界线
        return i;             // 返回基准数的索引
    }

    // 快速排序主方法
    public static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            // 分区操作，返回基准元素的索引
            int pivotIndex = partition(array, low, high);
            // 递归排序基准元素左边的子数组
            quickSort(array, low, pivotIndex - 1);
            // 递归排序基准元素右边的子数组
            quickSort(array, pivotIndex + 1, high);
        }
    }

    public static void main(String[] args) {
        int[] array = { 9, 7, 5, 11, 12, 2, 14, 3, 10, 6 };
        System.out.println("原数组: " + Arrays.toString(array));

        // 调用快速排序
        quickSort(array, 0, array.length - 1);

        System.out.println("排序后的数组: " + Arrays.toString(array));
    }


}
