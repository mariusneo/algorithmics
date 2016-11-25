package algorithmsandme;

import org.junit.Test;

/**
 * Given two linked lists representing large numbers. Add these two numbers and result should
 * be stored in third linked list.
 * <p>
 * L1 = 1->2->3
 * L2 = 3->4
 * Output: L3 = 1->5->7
 */
public class AddTwoNumbers {

    public Node add(Node n1, Node n2) {
        if (n1 == null) return n2;
        if (n2 == null) return n1;

        Node rn1 = reverseList(n1);
        Node rn2 = reverseList(n2);

        int digitSum = rn1.digit + rn2.digit;
        Node head = new Node(digitSum % 10);
        int reminder = calculateReminder(digitSum);
        Node previous = head;
        rn1 = rn1.next;
        rn2 = rn2.next;
        while (rn1 != null && rn2 != null) {
            digitSum = rn1.digit + rn2.digit + reminder;
            reminder = calculateReminder(digitSum);
            Node current = new Node(digitSum % 10);
            previous.next = current;
            previous = current;
            rn1 = rn1.next;
            rn2 = rn2.next;
        }

        Node rest = null;
        if (rn1 == null) rest = rn2;
        if (rn2 == null) rest = rn1;
        while (rest != null) {
            digitSum = rest.digit + reminder;
            reminder = calculateReminder(digitSum);
            Node current = new Node(digitSum % 10);
            previous.next = current;
            previous = current;
            rest = rest.next;
        }

        return reverseList(head);
    }

    private int calculateReminder(int digitSum) {
        return digitSum >= 10 ? 1 : 0;
    }

    @Test
    public void testAccuracy() {
        Node n1 = createList("34");
        Node n2 = createList("1234567");

        Node sum = add(n1, n2);
        printList(sum);
    }

    private Node createList(String number) {
        if (number == null || number.trim().isEmpty()) return null;
        Node result = new Node(Character.getNumericValue(number.charAt(0)));
        Node previous = result;
        for (int i = 1; i < number.length(); i++) {
            Node current = new Node(Character.getNumericValue(number.charAt(i)));
            previous.next = current;
            previous = current;
        }

        return result;
    }

    private Node reverseList(Node list) {
        Pair<Node, Node> result = reverseListInternal(list);
        if (result == null) return null;
        Node head = result.x;
        return head;
    }

    private Pair<Node, Node> reverseListInternal(Node list) {
        if (list == null) return null;
        if (list.next == null) {
            Node node = new Node(list.digit);
            return new Pair<>(node, node);
        }

        Pair<Node, Node> result = reverseListInternal(list.next);
        Node head = result.x;
        Node tail = result.y;
        tail.next = new Node(list.digit);
        return new Pair<>(head, tail.next);
    }


    private void printList(Node list) {
        if (list == null) return;
        Node current = list;
        do {
            System.out.printf("%1d ", current.digit);
            current = current.next;
        } while (current != null);
    }

    private static class Node {
        int digit;
        Node next;

        public Node(int digit) {
            this.digit = digit;
        }
    }

    private static class Pair<X, Y> {
        private X x;
        private Y y;

        public Pair(X x, Y y) {
            this.x = x;
            this.y = y;
        }
    }
}
