package com.coding.LinkedList;

public class MergeTwoListIntoSingleLL {

    public static Node mergeArrays(int[] arr1, int[] arr2) {

        int i = 0, j = 0;

        Node dummy = new Node(0);
        Node tail = dummy;

        while (i < arr1.length && j < arr2.length) {

            if (arr1[i] <= arr2[j]) {
                tail.next = new Node(arr1[i++]);
            } else {
                tail.next = new Node(arr2[j++]);
            }

            tail = tail.next;
        }

        while (i < arr1.length) {
            tail.next = new Node(arr1[i++]);
            tail = tail.next;
        }

        while (j < arr2.length) {
            tail.next = new Node(arr2[j++]);
            tail = tail.next;
        }

        return dummy.next;
    }

    public static void printLL(Node head) {
        Node temp = head;

        while (temp != null) {
            System.out.print(temp.data + " -> ");
            temp = temp.next;
        }

    }

    public static void main(String[] args) {

        int[] arr1 = {1, 3, 5, 7};
        int[] arr2 = {2, 4, 6, 8};

        Node head = MergeTwoListIntoSingleLL.mergeArrays(arr1, arr2);

        MergeTwoListIntoSingleLL.printLL(head);

    }
}
