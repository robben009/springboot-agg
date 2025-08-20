package typeLinkedList;

/**
 * 判断链表中是否存在环
 */
public class 环形链表 {
    static class ListNode {
        int val;
        ListNode next;

        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public boolean hasCycle(ListNode head) {
        // 定义快慢指针
        ListNode slow = head, fast = head;

        // 遍历链表，快指针走两步，慢指针走一步
        while (fast != null && fast.next != null) {
            slow = slow.next;              // 慢指针移动一步
            fast = fast.next.next;         // 快指针移动两步

            // 如果快慢指针相遇，说明链表有环
            if (slow == fast) {
                return true;
            }
        }

        // 如果快指针到达链表末尾，说明没有环
        return false;
    }


    public static int detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return -1; // 空链表或只有一个节点的链表必定无环
        }

        // 快慢指针初始化
        ListNode slow = head, fast = head;

        // 快慢指针判断链表是否有环
        while (fast != null && fast.next != null) {
            slow = slow.next;              // 慢指针走一步
            fast = fast.next.next;         // 快指针走两步

            if (slow == fast) {            // 快慢指针相遇，说明链表有环
                // 找到环的起始节点
                return getCycleStartIndex(head, slow);
            }
        }

        return -1; // 如果快指针到达链表末尾，没有环
    }


    private static int getCycleStartIndex(ListNode head, ListNode meetingNode) {
        ListNode pointer1 = head;
        ListNode pointer2 = meetingNode;

        int index = 0; // 用于记录环起始位置的索引

        // 两指针相遇的节点就是环的起点
        while (pointer1 != pointer2) {
            pointer1 = pointer1.next;
            pointer2 = pointer2.next;
            index++;
        }

        return index; // 返回环的起始索引
    }


    public static void main(String[] args) {


        // 测试用例 1：有环的链表
        // 链表结构：1 -> 2 -> 3 -> 4 -> 5 -> 2 (环起始节点为索引 1)
        ListNode head = new ListNode(1);
        ListNode second = new ListNode(2);
        ListNode third = new ListNode(3);
        ListNode fourth = new ListNode(4);
        ListNode fifth = new ListNode(5);

        head.next = second;
        second.next = third;
        third.next = fourth;
        fourth.next = fifth;
        fifth.next = second; // 构造环，5 -> 2

        int cycleStartIndex = detectCycle(head);
        System.out.println(cycleStartIndex); // 输出 1

        // 测试用例 2：无环的链表
        // 链表结构：1 -> 2 -> 3 -> 4 -> 5 (无环)
        head = new ListNode(1);
        second = new ListNode(2);
        third = new ListNode(3);
        fourth = new ListNode(4);
        fifth = new ListNode(5);

        head.next = second;
        second.next = third;
        third.next = fourth;
        fourth.next = fifth;

        cycleStartIndex = detectCycle(head);
        System.out.println(cycleStartIndex); // 输出 -1
    }






    /**
     * 通过使用具有 不同速度 的快、慢两个指针遍历链表，空间复杂度可以被降低至 O(1)。慢指针每次移动一步，而快指针每次移动两步。
     */

}
