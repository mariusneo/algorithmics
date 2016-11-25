package algorithmsandme;

import org.junit.Test;

/**
 * Find if binary tree has given sum path.
 * <p>
 * <p>
 * <p>
 * <pre>
 *                     10
 *                   /     \
 *                  6       15
 *                 /  \    /  \
 *                4   7   13   17
 *
 * </pre>
 * <p>
 * <p>
 * The paths in the tree shown are:
 * <ul>
 * <li>10, 6, 4</li>
 * <li>10, 6, 7</li>
 * <li>10, 15, 13</li>
 * <li>10, 15, 17</li>
 * </ul>
 */
public class FindBinaryTreeSumPath {


    public static void print(BalancedTree.Node node, int level) {
        if (node == null) return;
        String delimiter = "";
        for (int i = 0; i < level; i++) delimiter += "  ";
        System.out.println(delimiter + node.data);
        print(node.left, level + 1);
        print(node.right, level + 1);
    }

    public boolean hasSum(Node node, int sum) {
        if (node == null) return false;
        if (node.left == null || node.right == null) {
            return sum == node.data;
        }

        return hasSum(node.left, sum - node.data) || hasSum(node.right, sum - node.data);
    }

    @Test
    public void testAccuracy() {
        Node root = new Node(10,
                new Node(6, new Node(4), new Node(7)),
                new Node(15, new Node(13), new Node(17)));
        System.out.println(hasSum(root, 20));
        System.out.println(hasSum(root, 38));
        System.out.println(hasSum(root, 39));
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
