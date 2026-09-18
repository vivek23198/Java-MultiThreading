package com.coding.LinkedList;

import java.util.List;

public class FastAndSlowPointer {

//    static class ListNode {
//        int data;
//        ListNode next;
//
//        ListNode() {
//            this.data = 0;
//            this.next = null;
//        }
//
//        ListNode(int val) {
//            this.data = val;
//            this.next = null;
//        }
//    }

    public Basics.ListNode findMiddle(Basics.ListNode head) {
        Basics.ListNode slowPtr = head;
        Basics.ListNode fastPtr = head;

        if(head == null) {
            return null;
        }

        while(fastPtr != null && fastPtr.next != null) {
            slowPtr = slowPtr.next;
            fastPtr = fastPtr.next.next;
        }

        return slowPtr;
    }

    public boolean hasCycle(Basics.ListNode head) {
        Basics.ListNode slowPtr = head;
        Basics.ListNode fastPtr = head;

        while(fastPtr != null && fastPtr.next != null) {
            slowPtr = slowPtr.next;
            fastPtr = fastPtr.next.next;

            if(slowPtr == fastPtr) return true;
        }

        return false;
    }

    public Basics.ListNode detectCycle(Basics.ListNode head) {
        Basics.ListNode slowPtr = head;
        Basics.ListNode fastPtr = head;

        while(fastPtr != null && fastPtr.next != null ) {
            slowPtr = slowPtr.next;
            fastPtr = fastPtr.next.next;

            if(slowPtr == fastPtr) {
                slowPtr  =head;

                while(slowPtr != fastPtr) {
                    slowPtr = slowPtr.next;
                    fastPtr = fastPtr.next;
                }
                return slowPtr;
            };
        }
        return null;
    }

    public boolean isPalindrome(Basics.ListNode head) {
        Basics.ListNode middleNode  = findMiddle(head);
        Basics.ListNode reversedList = reverseLinkedList(middleNode);

        Basics.ListNode leftPtr = head;
        Basics.ListNode rightPtr = reversedList;

        while(rightPtr != null) {
            if(leftPtr.data != rightPtr.data) {
                return false;
            }
            rightPtr  =rightPtr.next;
            leftPtr = leftPtr.next;
        }
        return true;
    }

    public Basics.ListNode reverseLinkedList(Basics.ListNode head) {
        Basics.ListNode prev  = null;
        Basics.ListNode curr = head;

        while(curr != null) {
            Basics.ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr  = next;
        }
        return prev;
    }

    public Basics.ListNode nthFromEnd(Basics.ListNode head, int n) {

        if(head == null || n <= 0) {
            return null;
        }

        Basics.ListNode slowPtr = head;
        Basics.ListNode fastPtr = head;

        for(int i=0; i<(n-1); i++) {
            if(fastPtr == null) {
                return null;
            }
            fastPtr = fastPtr.next;
        }

        while(fastPtr.next != null) {
            slowPtr = slowPtr.next;
            fastPtr = fastPtr.next;
        }

        return slowPtr;
    }

    public Basics.ListNode removeNthFromEnd(Basics.ListNode head, int n) {

        if(head == null || n <= 0) {
            return null;
        }

        Basics.ListNode dummyNode = head;
        Basics.ListNode slowPtr = dummyNode;
        Basics.ListNode fastPtr = dummyNode;

        for(int i=0; i<n; i++) {
            if(fastPtr == null) {
                return null;
            }
            fastPtr = fastPtr.next;
        }

        while(fastPtr.next != null) {
            slowPtr = slowPtr.next;
            fastPtr = fastPtr.next;
        }

        Basics.ListNode temp = slowPtr.next;
        slowPtr.next = temp.next;
        temp.next = null;
        return dummyNode.next;
    }

    public static void main(String[] args) {
        Basics ll = new Basics();
        FastAndSlowPointer fastAndSlowPointer = new FastAndSlowPointer();
        Basics.ListNode head = null;
        Basics.ListNode temp  =head;
        int[] data = {10, 20, 30, 40, 50, 60};

        for(int num: data) {
            head = ll.add(new Basics.ListNode(num), head);
        }

        int middleElement = fastAndSlowPointer.findMiddle(head).data;
        System.out.println("Middle Element is :: "+middleElement);

        System.out.println();
        temp = head;
        while(temp.next != null) {
            temp = temp.next;

        }

//        temp.next = head.next;
        boolean isCycleDetected = fastAndSlowPointer.hasCycle(head);
        System.out.println("Cycle Exists or not :: "+isCycleDetected);
//        System.out.println("Starting Node of Cyclic "+fastAndSlowPointer.detectCycle(head).data);

        ll.printLL(head);
        System.out.println();
        Basics.ListNode nthNode = fastAndSlowPointer.nthFromEnd(head, 2);
        System.out.println("2nd Node from end :: "+nthNode.data);

        Basics.ListNode nthNodeFromEnd = fastAndSlowPointer.removeNthFromEnd(head, 6);
        System.out.println("Nth node from end "+nthNodeFromEnd.data);
        System.out.println("After Removing nth Node");
        ll.printLL(head);

    }
}
