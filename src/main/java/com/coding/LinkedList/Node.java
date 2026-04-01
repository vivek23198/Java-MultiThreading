package com.coding.LinkedList;

public class Node {
     int data;
     Node next;

    public Node() {

    }

    public Node(int data) {
        this.data = data;
        this.next = null;
    }

    public static Node insertNode(Node head, int value) {
        Node temp = head;
        Node newNode = new Node(value);
        if(head == null) {
            return newNode;
        }
        while(temp.next != null) {
            temp = temp.next;
        }

        temp.next = newNode;
        return head;
    }
}
