package sort;

import java.util.Arrays;

public class HeapSort {
    /* 堆的长度为 n ，从节点 i 开始，从顶至底堆化 */
    static void siftDown(int[] nums, int n, int i) {
        while (true) {
            // 判断节点 i, l, r 中值最大的节点，记为 largest
            int l = 2 * i + 1;
            int r = 2 * i + 2;
            int largest = i;
            if (l < n && nums[l] > nums[largest])
                largest = l;
            if (r < n && nums[r] > nums[largest])
                largest = r;
            // 若节点 i 最大或索引 l, r 越界，则无须继续堆化，跳出
            if (largest == i)
                break;
            // 交换两节点
            int temp = nums[i];
            nums[i] = nums[largest];
            nums[largest] = temp;
            // 循环向下堆化
            i = largest;
        }
    }

    /* 堆排序 */
    static void heapSort(int[] nums) {
        // 建堆操作：堆化除叶节点以外的其他所有节点
        for (int i = nums.length / 2 - 1; i >= 0; i--) {
            siftDown(nums, nums.length, i);
        }
        // 从堆中提取最大元素，循环 n-1 轮
        for (int i = nums.length - 1; i > 0; i--) {
            // 交换根节点与最右叶节点（交换首元素与尾元素）
            int tmp = nums[0];
            nums[0] = nums[i];
            nums[i] = tmp;
            // 以根节点为起点，从顶至底进行堆化
            siftDown(nums, i, 0);
        }
    }

    public static void main(String[] args) {
        int[] array = { 9, 7, 5, 11, 12, 2, 14, 3, 10, 6 };
        System.out.println("原数组: " + Arrays.toString(array));

        // 调用快速排序
        heapSort(array);

        System.out.println("排序后的数组: " + Arrays.toString(array));
    }
}
