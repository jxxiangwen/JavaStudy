package cn.edu.shu.leetcode.solution1_20.solution_19;

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
        val = x;
    }
}

/**
 * Given a linked list, remove the nth node from the end of list and return its head.
 * For example,
 * Given linked list: 1->2->3->4->5, and n = 2.
 * After removing the second node from the end, the linked list becomes 1->2->3->5.
 * Note:
 * Given n will always be valid.
 * Try to do this in one pass.
 */
public class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode first = new ListNode(0);
        first.next = head;
        ListNode second = first;
        ListNode result = first;
        while(n > 0){
            second = second.next;
            n--;
        }
        while (second.next != null){
            first = first.next;
            second = second.next;
        }
//        if(first == head){
//            head.next = head.next.next;
//            return head;
//        }
        first.next = first.next.next;
        return result.next;
    }

    public ListNode removeNthFromEnd1(ListNode head, int n) {
        ListNode first = head;
        ListNode second = head;
        int length = 0;
        while(second != null){
            length++;
            second = second.next;
        }
        if(length < n){
            return head;
        }
        int diff = length - n - 1;
        if(diff < 0){
            return head.next;
        }
        while( diff > 0){
            first = first.next;
            diff--;
        }
        first.next = first.next.next;
        return head;
    }

    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
//        listNode.next = new ListNode(2);
        listNode = new Solution().removeNthFromEnd(listNode, 1);
    }
}