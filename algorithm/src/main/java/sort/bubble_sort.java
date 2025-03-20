package sort;

import java.util.Arrays;

public class bubble_sort {

    static void sort(int[] nums){
        for (int i = nums.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if(nums[j] > nums[i]){
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] array = { 9, 7, 5, 11, 12, 2, 14, 3, 10, 6 };
        System.out.println("原数组: " + Arrays.toString(array));

        // 调用快速排序
        sort(array);

        System.out.println("排序后的数组: " + Arrays.toString(array));
    }

}
