package sort;

/**
 * 初始状态下，数组的第 1 个元素已完成排序。
 * 选取数组的第 2 个元素作为 base ，将其插入到正确位置后，数组的前 2 个元素已排序。
 * 选取第 3 个元素作为 base ，将其插入到正确位置后，数组的前 3 个元素已排序。
 * 以此类推，在最后一轮中，选取最后一个元素作为 base ，将其插入到正确位置后，所有元素均已排序。
 *
 * 直接插入排序(Straight Insertion Sort)的基本思想是: 把n个待排序的元素看成为一个有序表和一个无序表。开始时有序表中只包含1个元素，
 * 无序表中包含有n-1个元素，排序过程中每次从无序表中取出第一个元素，将它插入到有序表中的适当位置，使之成为新的有序表，重复n-1次可完成排序过程。
 */
public class InsertionSort {

    // 插入排序方法
    public static void insertionSort(int[] array) {
        // 从数组的第二个元素开始遍历
        for (int i = 1; i < array.length; i++) {
            int key = array[i]; // 当前要插入的元素
            int j = i - 1;

            // 找到 key 在已排序部分的适当位置
            // 将大于 key 的元素移动到右边
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j]; // 移动元素
                j--; // 移动到下一个元素
            }

            // 将 key 放到找到的位置
            array[j + 1] = key; // 插入元素
        }
    }

    // 测试插入排序
    public static void main(String[] args) {
        int[] array = {5, 2, 4, 6, 1, 3}; // 待排序数组
        insertionSort(array); // 调用插入排序方法

        // 打印排序后的数组
        System.out.print("排序后的数组：");
        for (int num : array) {
            System.out.print(num + " ");
        }
    }
}
