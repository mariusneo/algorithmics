package algorithmsandme;

import org.junit.Test;

import static java.lang.Math.abs;
import static java.lang.Math.max;

/**
 * Find if given binary tree is height balanced.
 * <p>
 * First of all, understand what is height balanced tree. Balance tree is the tree where
 * none of its sub trees have height difference more than 1.
 * So, if tree rooted at node let’s r, height of left subtree l and height of
 * right subtree r, should not differ more than 1.
 */
public class FindIfBinaryTreeIsBalanced {


    public boolean isBalanced(Node node) {
        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);
        return abs(leftHeight - rightHeight) <= 1 && isBalanced(node.left) && isBalanced(node.right);
    }

    private int getHeight(Node node) {
        if (node == null) return 0;
        return max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    @Test
    public void testAccuracy() {
        Node root = new Node(10,
                new Node(6, new Node(4), new Node(7)),
                new Node(15, new Node(13),
                        new Node(17, new Node(20, new Node(10), new Node(11)), null)));
        System.out.println(isBalanced(root));
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
