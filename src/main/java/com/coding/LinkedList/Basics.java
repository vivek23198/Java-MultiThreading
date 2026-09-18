package com.coding.LinkedList;

public class Basics {

    static class ListNode {
        int data;
        ListNode next;

        ListNode() {
            this.data = 0;
            this.next = null;
        }

        ListNode(int val) {
            this.data = val;
            this.next = null;
        }
    }

    public  ListNode add(ListNode elem, ListNode head) {
        if(head == null) {
            return elem;
        }

        ListNode temp = head;
        while(temp.next != null) {
            temp = temp.next;
        }
        temp.next = elem;
        return head;
    }

    public ListNode insertAtBeginning(ListNode head, int val) {
        ListNode node = new ListNode(val);
        if(head == null) {
            return node;
        }

        ListNode temp = head;
        node.next = head;
        head = node;
        temp = head;

        return head;
    }

    public ListNode insertAtEnd(ListNode head, int val){
        ListNode newNode = new ListNode(val);

        if(head == null) {
            return newNode;
        }

        ListNode temp = head;
        while(temp.next != null){
            temp = temp.next;
        }
        temp.next = newNode;
        return head;
    }

    public ListNode insertAtPosition(ListNode head, int val, int pos) {
        ListNode temp = head;
        ListNode newNode = new ListNode(val);
        if(pos == 0) {
            head = insertAtBeginning(head, val);
            return head;
        }

        if(head == null) {
            return head;
        }

        for(int i=0; i< pos-1; i++) {
            if(temp.next == null) {
                System.out.println("Position is higher than the total number of node");
                return head;
            }
            temp = temp.next;
        }
        newNode.next = temp.next;
        temp.next = newNode;
        return head;
    }

    public ListNode deleteAtPosition(ListNode head, int pos) {
        ListNode temp = head;
        if(head == null) {
            System.out.println("No Node are available for a delete operation");
            return head;
        }

        if(pos == 0) {
            head = head.next;
            temp.next = null;
            return head;
        }

        for(int i=0; i< pos-1; i++) {
            if(temp.next == null) {
                System.out.println("Position is higher than the total number of node");
                return head;
            }
            temp = temp.next;
        }
        ListNode deletedNode = temp.next;
        temp.next = deletedNode.next;
        deletedNode.next = null;
        return head;
    }

    public int getLength(ListNode head) {
        int count = 0;

        ListNode temp = head;
        while(temp != null) {
            count++;
            temp = temp.next;
        }

        return count;
    }

    public void printLL(ListNode head) {
        ListNode temp = head;
        while(temp != null) {
            System.out.print(temp.data+" -> ");
            temp = temp.next;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Basics ll = new Basics();
        ListNode head  = null;
        int[] data = {10, 20, 30, 40, 50};
        for(int num: data) {
            head = ll.add(new ListNode(num), head);
        }
        ll.printLL(head);

        head = ll.insertAtBeginning(head, 5);
        System.out.println("Inserting data at the start ");
        System.out.println("First Element :: "+head.data);
        ll.printLL(head);

        head = ll.insertAtEnd(head, 40);
        System.out.println();
        System.out.println("After inserting data at the end ");
        ll.printLL(head);

        head = ll.insertAtPosition(head, 25, 2);
        System.out.println();
        System.out.println("After inserting at position 2");
        ll.printLL(head);

        head = ll.deleteAtPosition(head, 7);
        System.out.println();
        System.out.println("After Deleting an element at position 2");
        ll.printLL(head);

        System.out.println();
        System.out.println("Total Node in LinkedList");
        System.out.println(ll.getLength(head));
    }
}
