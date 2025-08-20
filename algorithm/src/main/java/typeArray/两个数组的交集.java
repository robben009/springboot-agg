package typeArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 给定两个数组，编写一个函数来计算它们的交集。
 */
public class 两个数组的交集 {

    public static int[] intersect(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>();

        // 遍历 nums1，初始化 map
        for (int num : nums1) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        List<Integer> intersection = new ArrayList<>();

        // 遍历 nums2
        for (int num : nums2) {
            // 如果元素存在于 map 中且次数大于 0
            if (map.getOrDefault(num, 0) > 0) {
                intersection.add(num); // 将其加入交集
                map.put(num, map.get(num) - 1); //次数减 1
            }
        }

        // 将 List 转成数组
        int[] result = new int[intersection.size()];
        for (int i = 0; i < intersection.size(); i++) {
            result[i] = intersection.get(i);
        }

        return result;
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 2, 1};
        int[] nums2 = {2, 2};
        int[] result = intersect(nums1, nums2);

        System.out.print("Intersection: ");
        for (int num : result) {
            System.out.print(num + " ");
        }
        // 输出: Intersection: 2 2
    }

    /**
     * 使用 HashMap：
     *
     * 创建一个 HashMap，用于存储 nums1 中每个数字及其出现的次数。
     * 初始化计数：
     *
     * 遍历 nums1，将每个元素的计数存入 map 中。使用 getOrDefault 方法，如果元素不存在，就默认数量为 0。
     * 查找交集：
     *
     * 遍历 nums2，检查元素是否在 map 中且其计数大于 0。
     * 如果是，则将该元素添加到 intersection 列表中，并在 map 中将其计数减一。
     * 转换列表为数组：
     *
     * 将 List<Integer> 转换为 int[] 数组，准备返回。
     * 主函数测试：
     *
     * 在 main 方法中测试 intersect 函数，并输出结果。
     */

}
