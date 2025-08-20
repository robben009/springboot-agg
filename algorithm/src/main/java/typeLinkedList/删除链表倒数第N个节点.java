package typeLinkedList;

/**
 * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
 *
 */
public class 删除链表倒数第N个节点 {
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        // 添加一个虚拟头节点，方便处理边界情况
        ListNode result = new ListNode(0);
        result.next = head;

        // 定义 pre 和 cur 指针
        ListNode pre = null;
        ListNode cur = result;

        int i = 1;

        // 遍历原链表
        while (head != null) {
            // 在倒数第 n 个节点开始，移动 pre 和 cur
            if (i >= n) {
                pre = cur;
                cur = cur.next;
            }

            // 移动 head 指针
            head = head.next;
            i++;
        }

        // 删除目标节点
        pre.next = pre.next.next;

        // 返回链表头节点
        return result.next;
    }


    static class ListNode {
        int val;
        ListNode next;

        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }


    public static void main(String[] args) {
        // 构造链表 1 -> 2 -> 3 -> 4 -> 5
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        int n = 2;
        // 移除倒数第 n 个节点
        ListNode result = removeNthFromEnd(head, n);

        // 打印结果（期望结果为 1 -> 2 -> 3 -> 5）
        while (result != null) {
            System.out.print(result.val);
            if (result.next != null) {
                System.out.print(" -> ");
            }
            result = result.next;
        }
    }



    /**
     * 首先我们思考，让我们删除倒数第N个元素，那我们只要找到倒数第N个元素就可以了，那怎么找呢？
     * 我们只需要设置两个指针变量，中间间隔N-1元素。当后面的指针遍历完所有元素指向nil时，前面的指针就指向了我们要删除的元素。
     */
}
