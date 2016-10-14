package cn.edu.shu.leetcode.solution21_40.solution_23;

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
    int val;
    ListNode next;

    ListNode(int x) {
        this.val = x;
    }
}

public class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }
        if (lists.length == 1) {
            return lists[0];
        }
        int length = lists.length;
        int mid = (length + 1) / 2;
        ListNode[] first = new ListNode[mid];
        ListNode[] second = new ListNode[length - mid];
        for(int i = 0; i < lists.length; i++){
            if(i < mid){
                first[i] = lists[i];
            }else {
                second[i - mid] = lists[i];
            }
        }
        ListNode l1 = mergeKLists(first);
        ListNode l2 = mergeKLists(second);
        return mergeTwoLists(l1, l2);
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode lastNode = dummy;

        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                lastNode.next = l1;
                l1 = l1.next;
            } else {
                lastNode.next = l2;
                l2 = l2.next;
            }
            lastNode = lastNode.next;
        }

        if (l1 != null) {
            lastNode.next = l1;
        } else {
            lastNode.next = l2;
        }

        return dummy.next;
    }

    public ListNode getSmallestNode(ListNode[] rears) {
        ListNode result = null;
        int index = -1;
        for (int i = 0; i < rears.length; i++) {
            if (rears[i] != null) {
                if (result == null) {
                    result = rears[i];
                    index = i;
                } else {
                    if (rears[i].val < result.val) {
                        result = rears[i];
                        index = i;
                    }
                }
            }
        }
        if (index != -1) {
            rears[index] = rears[index].next;
        }
        return result;
    }

    public static void main(String[] args) {
        ListNode one = new ListNode(1);
        ListNode two = new ListNode(2);
        ListNode[] listNodes = new ListNode[2];
        listNodes[0] = null;
        listNodes[1] = one;
        System.out.println(new Solution().mergeKLists(listNodes));
        listNodes[0] = one;
        listNodes[1] = two;
        System.out.println(new Solution().mergeKLists(listNodes));
    }
}