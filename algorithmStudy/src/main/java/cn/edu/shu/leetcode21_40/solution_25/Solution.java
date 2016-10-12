package cn.edu.shu.leetcode21_40.solution_25;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 */

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        this.val = x;
    }
}

/**
 * Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.
 * If the number of nodes is not a multiple of k then left-out nodes in the end should remain as it is.
 * You may not alter the values in the nodes, only nodes itself may be changed.
 * Only constant memory is allowed.
 * <p>
 * For example,
 * Given this linked list: 1->2->3->4->5
 * For k = 2, you should return: 2->1->4->3->5
 * For k = 3, you should return: 3->2->1->4->5
 */
public class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        if (k < 2) {
            return head;
        }
        ListNode listNode = new ListNode(0);
        ListNode rear = listNode;
        ListNode dp = head;
        int length = 0;
        while (dp != null) {
            length++;
            dp = dp.next;
        }
        if (length < k) {
            return head;
        }
        dp = head;
        ListNode temp = null;
        for (int i = 0; i < length - k + 1; i += k) {
            int size = 0;
            while (size < k){
                temp = dp;
                dp = dp.next;
                temp.next = rear.next;
                rear.next = temp;
                size++;
            }
            while (size > 0){
                rear = rear.next;
                size --;
            }
        }
        rear.next = dp;
        return listNode.next;
    }

    public static void main(String[] args) {
        ListNode one = new ListNode(1);
        ListNode two = new ListNode(2);
//        ListNode three = new ListNode(3);
//        ListNode four = new ListNode(4);
//        ListNode five = new ListNode(5);
        one.next = two;
//        two.next = three;
//        three.next = four;
//        four.next = five;
        new Solution().reverseKGroup(one, 2);
    }
}