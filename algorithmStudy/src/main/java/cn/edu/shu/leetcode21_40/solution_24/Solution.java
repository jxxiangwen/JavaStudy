package cn.edu.shu.leetcode21_40.solution_24;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

class ListNode {
    private int val;
    ListNode next;
    ListNode(int x) {
        this.val = x;
    }
}
public class Solution {
    public ListNode swapPairs(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }
        ListNode listNode = new ListNode(0);
        listNode.next = head;
        ListNode pivot = listNode;
        while(pivot.next != null && pivot.next.next != null){
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