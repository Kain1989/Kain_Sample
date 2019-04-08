package com.kain.algorithm.test;

/**
 * Created on 3/28/2017.
 */
public class Recursion {

    static class Node {

        Object value;

        Node next;

        Node(Object value) {
            this.value = value;
        }

        public void print() {
            System.out.print(value);
            Node next = this.next;
            while (next != null) {
                System.out.print("," + next.value);
                next = next.next;
            }
        }
    }

    public static void main(String args[]) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        node1.next = node2;
        node2.next = node3;
        reverse(node1).print();
    }



    public static Node reverse(Node input) {
        if (input.next == null || input == null) {
            return input;
        }
        Node head = reverse(input.next);
        input.next.next = input;
        input.next = null;
        return head;

    }

}
