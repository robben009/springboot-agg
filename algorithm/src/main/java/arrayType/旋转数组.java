package arrayType;

/**
 * 给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。
 * 示例
 * 输入: [1,2,3,4,5,6,7] 和 k = 3
 * 输出: [5,6,7,1,2,3,4]
 */
public class 旋转数组 {

    public void rotate(int[] nums, int k) {
        int n = nums.length;
        if (n == 0) return;        // 防止 k % 0
        k = k % n;
        if (k == 0) return;
        reverse(nums, 0, n - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, n - 1);
    }

    private void reverse(int[] arr, int left, int right) {
        while (left < right) {
            int tmp = arr[left];
            arr[left++] = arr[right];
            arr[right--] = tmp;
        }
    }

    /**
     * 我们只需要将所有元素反转，然后反转前 k 个元素(实现移动效果)，再反转后面n-k个元素(还原顺序)，就能得到想要的结果。
     *
     */
}
