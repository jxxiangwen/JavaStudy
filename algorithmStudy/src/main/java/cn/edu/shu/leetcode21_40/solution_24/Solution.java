package cn.edu.shu.leetcode21_40.solution_24;

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
 * Given a linked list, swap every two adjacent nodes and return its head.
 * <p>
 * For example,
 * Given 1->2->3->4, you should return the list as 2->1->4->3.
 * <p>
 * Your algorithm should use only constant space. You may not modify the values in the list, only nodes itself can be changed.
 */
public class Solution {
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode listNode = new ListNode(0);
        listNode.next = head;
        ListNode pivot = listNode;
        while (pivot.next != null && pivot.next.next != null) {
            ListNode temp = pivot.next;
            pivot.next = temp.next;
            pivot = pivot.next;
            temp.next = pivot.next;
            pivot.next = temp;
            pivot = pivot.next;
        }
        return listNode.next;
    }

    public static void main(String[] args) {
        ListNode one = new ListNode(1);
        ListNode two = new ListNode(2);
        one.next = two;
        new Solution().swapPairs(one);
    }
}