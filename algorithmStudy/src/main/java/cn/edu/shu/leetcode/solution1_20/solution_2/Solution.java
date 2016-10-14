package cn.edu.shu.leetcode.solution1_20.solution_2;

import java.math.BigInteger;

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        this.val = x;
    }
}

/**
 * You are given two linked lists representing two non-negative numbers.
 * The digits are stored in reverse order and each of their nodes contain a single digit.
 * Add the two numbers and return it as a linked list.
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 */
public class Solution {
    public ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        String l1Value = String.valueOf(l1.val);
        String l2Value = String.valueOf(l2.val);
        while (l1.next != null) {
            l1 = l1.next;
            l1Value = l1.val + l1Value;
        }
        while (l2.next != null) {
            l2 = l2.next;
            l2Value = l2.val + l2Value;
        }
        String resultValue = new BigInteger(l1Value).add(new BigInteger(l2Value)).toString();
        ListNode resultNode = new ListNode(0);
        ListNode rear = resultNode;
        for (int i = resultValue.length() - 1; i >= 0; i--) {
            ListNode temp = new ListNode(Integer.parseInt(String.valueOf(resultValue.charAt(i))));
            rear.next = temp;
            rear = temp;
        }
        if (null == resultNode.next) {
            return new ListNode(0);
        }
        return resultNode.next;
    }

    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        if(l1 == null && l2 == null) {
            return null;
        }

        ListNode head = new ListNode(0);
        ListNode point = head;
        int carry = 0;
        while(l1 != null && l2!=null){
            int sum = carry + l1.val + l2.val;
            point.next = new ListNode(sum % 10);
            carry = sum / 10;
            l1 = l1.next;
            l2 = l2.next;
            point = point.next;
        }

        while(l1 != null) {
            int sum =  carry + l1.val;
            point.next = new ListNode(sum % 10);
            carry = sum /10;
            l1 = l1.next;
            point = point.next;
        }

        while(l2 != null) {
            int sum =  carry + l2.val;
            point.next = new ListNode(sum % 10);
            carry = sum /10;
            l2 = l2.next;
            point = point.next;
        }

        if (carry != 0) {
            point.next = new ListNode(carry);
        }
        return head.next;
    }

    public static void main(String[] args) {
        int[] a = new int[]{2, 4, 3};
        int[] b = new int[]{5, 6, 4};
        ListNode l1 = new ListNode(0);
        ListNode rear = l1;
        for (int i = 0; i < a.length; i++) {
            ListNode temp = new ListNode(a[i]);
            rear.next = temp;
            rear = temp;
        }
        ListNode l2 = new ListNode(0);
        rear = l2;
        for (int i = 0; i < b.length; i++) {
            ListNode temp = new ListNode(b[i]);
            rear.next = temp;
            rear = temp;
        }
        new Solution().addTwoNumbers1(l1.next, l2.next);
    }
}