package algorithmsandme;

import org.junit.Before;
import org.junit.Test;

/**
 * Given two sorted linked lists, merge two linked lists in one list in sorted order without using extra space.
 */
public class MergeLists {


    private MergeLists ml;

    public <T extends Comparable<T>> Node<T> mergeListsRecursive(Node<T> node1, Node<T> node2) {
        if (node1 == null) return node2;
        if (node2 == null) return node1;

        if (node1.data.compareTo(node2.data) < 0) {
            node1.next = mergeListsRecursive(node1.next, node2);
            return node1;
        } else {
            node2.next = mergeListsRecursive(node1, node2.next);
            return node2;
        }
    }

    public <T extends Comparable<T>> Node<T> mergeListsIterative(Node<T> node1, Node<T> node2) {
        if (node1 == null) return node2;
        if (node2 == null) return node1;

        Node<T> result;
        Node<T> current1 = node1;
        Node<T> current2 = node2;

        if (node1.data.compareTo(node2.data) < 0) {
            result = node1;
            current1 = current1.next;
        } else {
            result = node2;
            current2 = current2.next;
        }

        Node<T> current = result;

        while (current1 != null && current2 != null) {
            if (current1.data.compareTo(current2.data) < 0) {
                current.next = current1;
                current1 = current1.next;
            } else {
                current.next = current2;
                current2 = current2.next;
            }
            current = current.next;
        }

        if (current1 == null) {
            current.next = current2;
        } else {
            current.next = current1;
        }
        return result;
    }

    public <T extends Comparable<T>> Node<T> mergeListsIterative1(Node<T> node1, Node<T> node2) {
        if (node1 == null) return node2;
        if (node2 == null) return node1;

        Node<T> head;

        if (node1.data.compareTo(node2.data) < 0) {
            head = node1;
        } else {
            head = node2;
            node2 = node1;
            node1 = head;
        }

        while (node1.next != null && node2 != null) {
            if (node1.next.data.compareTo(node2.data) > 0) {
                Node<T> tmp = node1.next;
                node1.next = node2;
                node2 = tmp;
            }
            node1 = node1.next;
        }


        if (node1.next == null) {
            node1.next = node2;
        }

        return head;
    }

    @Before
    public void setup() {
        ml = new MergeLists();
    }

    @Test
    public void testMerge() {
        Node<Integer> mergeRecursive = mergeListsRecursive(createList(new Integer[]{1, 2, 5, 6}), createList(new
                Integer[]{3, 4, 7}));
        printList(mergeRecursive);
        Node<Integer> mergeIterative = mergeListsIterative(createList(new Integer[]{1, 2, 5, 6}), createList(new
                Integer[]{3, 4, 7}));
        printList(mergeIterative);
        Node<Integer> mergeIterative1 = mergeListsIterative1(createList(new Integer[]{1, 2, 5, 6}), createList(new
                Integer[]{3, 4, 7}));
        printList(mergeIterative1);
    }

    private <T> Node<T> createList(T[] data) {
        Node<T> node = new Node<T>(data[0]);
        Node<T> current = node;
        for (int i = 1; i < data.length; i++) {
            current.next = new Node<T>(data[i]);
            current = current.next;
        }
        return node;
    }


    private <T> void printList(Node<T> node) {
        Node<T> current = node;

        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    private static class Node<T> {
        T data;
        Node<T> next;

        public Node(T data, Node<T> next) {
            this(data);
            this.next = next;
        }

        public Node(T data) {
            this.data = data;
        }
    }
}
