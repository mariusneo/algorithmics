package algorithmsandme;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Stack;

/**
 * Print the level-order traversal from bottom to up in a given binary tree.
 */
public class PrintBinaryTreeBottomUp {


    public void insertBST(Node node, int value) {
        if (node.data == value) return;
        if (node.data < value) {
            if (node.right == null) {
                node.right = new Node(value);
            } else {
                insertBST(node.right, value);
            }
        } else {
            if (node.left == null) {
                node.left = new Node(value);
            } else {
                insertBST(node.left, value);
            }
        }
    }


    public void print(Node node, int level) {
        if (node == null) return;
        String delimiter = "";
        for (int i = 0; i < level; i++) delimiter += "  ";
        System.out.println(delimiter + node.data);
        print(node.left, level + 1);
        print(node.right, level + 1);
    }


    public void printBFS(Node node) {
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            Node current = queue.pop();
            System.out.printf("%4d", current.data);
            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }

        System.out.println();

    }

    public void printBottomUp(Node node) {
        LinkedList<Node> queue = new LinkedList<>();
        Stack<Node> stack = new Stack<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            Node current = queue.pop();
            stack.add(current);
            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            System.out.printf("%4d", current.data);
        }
        System.out.println();

    }


    @Test
    public void testAccuracy() {

        Node tree = new Node(30);
        insertBST(tree, 20);
        insertBST(tree, 45);
        insertBST(tree, 15);
        insertBST(tree, 27);
        insertBST(tree, 37);
        insertBST(tree, 50);
        insertBST(tree, 10);
        insertBST(tree, 13);

        print(tree, 0);

        System.out.println();
        System.out.println();
        printBFS(tree);
        System.out.println();
        System.out.println();
        printBottomUp(tree);
    }

    private static class Node {
        int data;
        Node left;
        Node right;

        public Node(int data) {
            this.data = data;
        }

        public Node(int data, Node left, Node right) {
            this(data);
            this.left = left;
            this.right = right;
        }
    }
}
