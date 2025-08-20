package typeLinkedList;

/**
 * 将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 *
 * 输入：1->2->4, 1->3->4
 * 输出：1->1->2->3->4->4
 */
public class 合并两个有序链表 {

    class ListNode {
        int val;
        ListNode next;

        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }


    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // 创建一个虚拟头节点
        ListNode prehead = new ListNode(-1);
        ListNode result = prehead;

        // 遍历两个链表，合并
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                prehead.next = l1;
                l1 = l1.next;
            } else {
                prehead.next = l2;
                l2 = l2.next;
            }
            prehead = prehead.next;
        }

        // 将剩余的链表节点接到后面
        if (l1 != null) {
            prehead.next = l1;
        }
        if (l2 != null) {
            prehead.next = l2;
        }

        // 返回合并后的链表
        return result.next;
    }


    /**
     * 首先我们维护一个 prehead 的哨兵节点。我们其实只需要调整它的 next 指针。
     * 让它总是指向l1或者l2中较小的一个，直到l1或者l2任一指向null。
     * 这样到了最后，如果l1还是l2中任意一方还有余下元素没有用到，那余下的这些元素一定大于prehead已经合并完的链表（因为是有序链表）。
     * 我们只需要将这些元素全部追加到prehead合并完的链表后，最终就得到了我们需要的链表
     */
}
