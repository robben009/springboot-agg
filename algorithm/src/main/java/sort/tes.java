package sort;

import java.util.Arrays;

public class tes {
    public static void main(String[] args) {
        int[] array = {9, 7, 5, 11, 12, 2, 14, 3, 10, 6};
        System.out.println("原数组: " + Arrays.toString(array));

        // 排序
        sort2(array);

        System.out.println("排序后的数组: " + Arrays.toString(array));
    }

    private static void sort2(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < nums.length; j++) {
                if(nums[minIndex] > nums[j]){
                    minIndex = j;
                }
            }

            int temp = nums[minIndex];
            nums[minIndex] = nums[i];
            nums[i] = temp;
        }
    }


}
