package algorithmsandme;

import org.junit.Test;

/**
 * Given a sorted array construct a Balanced Binary Search Tree.
 */
public class BalancedTree {

    private BalancedTree bt;

    public static void print(Node node, int level) {
        if (node == null) return;
        String delimiter = "";
        for (int i = 0; i < level; i++) delimiter += "  ";
        System.out.println(delimiter + node.data);
        print(node.left, level + 1);
        print(node.right, level + 1);
    }

    public Node constructTree(int[] a, int start, int end) {
        if (start > end) return null;
        int mid = (start + end) / 2;

        Node root = new Node(a[mid]);
        root.left = constructTree(a, start, mid - 1);
        root.right = constructTree(a, mid + 1, end);

        return root;
    }

    @Test
    public void testAccuracy() {
        Node node = constructTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 0, 9);
        print(node, 0);
    }

    public static class Node {
        int data;
        Node left;
        Node right;

        public Node(int data) {
            this.data = data;
        }
    }
}
