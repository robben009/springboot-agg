package sort;

import java.util.Arrays;

public class MergeSort {

    // 主方法，调用归并排序
    public void mergeSort(int[] nums) {
        if (nums.length < 2) {
            return; // 如果数组长度小于2，不需要排序
        }
        // 调用递归函数
        mergeSortRec(nums, 0, nums.length - 1);
    }

    // 归并排序的递归函数
    private void mergeSortRec(int[] nums, int left, int right) {
        if (left < right) {
            // 计算中间索引
            int mid = left + (right - left) / 2;

            // 递归对左右子数组进行归并排序
            mergeSortRec(nums, left, mid);
            mergeSortRec(nums, mid + 1, right);

            // 合并已排序的子数组
            merge(nums, left, mid, right);
        }
    }

    // 合并两个已排序的子数组
    private void merge(int[] nums, int left, int mid, int right) {
        // 临时数组
        int[] leftArray = new int[mid - left + 1];
        int[] rightArray = new int[right - mid];

        // 复制数据到临时数组
        for (int i = 0; i < leftArray.length; i++) {
            leftArray[i] = nums[left + i];
        }
        for (int j = 0; j < rightArray.length; j++) {
            rightArray[j] = nums[mid + 1 + j];
        }

        // 合并临时数组
        int i = 0, j = 0, k = left;
        while (i < leftArray.length && j < rightArray.length) {
            if (leftArray[i] <= rightArray[j]) {
                nums[k] = leftArray[i];
                i++;
            } else {
                nums[k] = rightArray[j];
                j++;
            }
            k++;
        }

        // 复制剩余元素
        while (i < leftArray.length) {
            nums[k] = leftArray[i];
            i++;
            k++;
        }
        while (j < rightArray.length) {
            nums[k] = rightArray[j];
            j++;
            k++;
        }
    }

    // 测试方法
    public static void main(String[] args) {
        MergeSort sorter = new MergeSort();
        int[] nums = {38, 27, 43, 3, 9, 82, 10};
        sorter.mergeSort(nums);
        System.out.println("排序后的数组: " + Arrays.toString(nums));
    }
}

