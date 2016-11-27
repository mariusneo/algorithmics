package algorithmsandme;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Replace all the elements in the array with its next highest element to its right.
 * <p>
 * [1, 4, 3, 2, 5, 6, 1]  --> [4, 5, 5, 5, 6, 1]
 */
public class ReplaceElementsInArray {


    public void replaceElements(int[] a) {
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        int currentMax = stack.peek();

        int i = 1;
        while (i < a.length) {
            if (a[i] > currentMax) {
                while (!stack.isEmpty()) {
                    Integer index = stack.pop();
                    a[index] = a[i];
                }
                currentMax = a[i];
            }
            stack.push(i++);
        }
    }

    private void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }


    public void print(int[] a) {
        System.out.println(Arrays.stream(a).boxed().map(Object::toString).collect(Collectors.joining(" ")));
    }

    @Test
    public void testAccuracy() {
        int[] a = {1, 4, 3, 2, 5, 6, 1};
        print(a);
        replaceElements(a);
        print(a);
    }

    private class Stack<V> {
        Node<V> head;

        public Stack() {
        }

        public V push(V value) {
            if (head == null) {
                head = new Node(value);
            } else {
                Node node = new Node(value);
                node.next = head;
                head = node;
            }
            return value;
        }

        public V pop() {
            if (head == null) throw new IllegalStateException("Stack is empty");
            V value = head.value;
            head = head.next;

            return value;
        }

        public V peek() {
            if (head == null) throw new IllegalStateException("Stack is empty");
            V value = head.value;
            return value;
        }

        public boolean isEmpty() {
            return head == null;
        }
    }

    private class Node<V> {
        Node next;
        V value;

        public Node(V value) {
            this.value = value;
        }
    }
}
