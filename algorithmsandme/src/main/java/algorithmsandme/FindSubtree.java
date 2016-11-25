package algorithmsandme;

import org.junit.Test;

/**
 * Find if the given tree is the subtree of the a given tree.
 */
public class FindSubtree {

    public boolean isSubtree(Node tree, Node subtree) {
        return isSubtreeInternal(tree, subtree)
                || (tree.left != null && isSubtree(tree.left, subtree))
                || (tree.right != null && isSubtree(tree.right, subtree));
    }

    public boolean isSubtreeInternal(Node tree, Node subtree) {
        if (tree == null && subtree == null) return true;
        if (tree == null) return false;
        if (subtree == null) return false;
        if (tree.data != subtree.data) return false;

        return isSubtreeInternal(tree.left, subtree.left) && isSubtreeInternal(tree.right, subtree.right);
    }


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


    @Test
    public void testAccuracy() {
        Node subtree = new Node(20);
        insertBST(subtree, 15);
        insertBST(subtree, 27);
        insertBST(subtree, 10);
        insertBST(subtree, 13);
        //insertBST(subtree, 30);

        print(subtree, 0);

        System.out.println();

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

        System.out.println(isSubtree(tree, subtree));
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
