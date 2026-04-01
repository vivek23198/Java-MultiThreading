package com.coding.LinkedList;

public class ReverseLL {

    public static Node reverseLL(Node head) {
        Node prev = null;
        Node curr = head;

        while(curr != null) {
            Node next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6 ,7};
        Node head = null;
        Node temp = head;

        for(int num: nums) {
            head = Node.insertNode(head, num);
        }

        while(temp != null) {
            System.out.print(temp.data+" -> ");
            temp = temp.next;
        }

        head = reverseLL(head);
        Node temp1 = head;
        while(temp1 != null) {
            System.out.print(temp1.data+" -> ");
            temp1 = temp1.next;
        }
    }
}
