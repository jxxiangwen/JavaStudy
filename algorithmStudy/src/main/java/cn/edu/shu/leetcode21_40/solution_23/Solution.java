package cn.edu.shu.leetcode21_40.solution_23;

/**
 * Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
 * <p>
 * <p>
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
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
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode listNode = new ListNode(0);
        return listNode;
    }
}