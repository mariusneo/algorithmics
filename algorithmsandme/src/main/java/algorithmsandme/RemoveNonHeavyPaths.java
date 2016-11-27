package algorithmsandme;

import org.junit.Test;

/**
 * Given a binary tree and a value k. A path is called heavy path if the sum of the elements in the path
 * (path from root to leaf) > k remove all the paths from the tree which are not heavy
 * i.e., tree should contain only heavy paths.
 */
public class RemoveNonHeavyPaths {


    public static void print(TreeNode treeNode, int level) {
        if (treeNode == null) return;
        String delimiter = "";
        for (int i = 0; i < level; i++) delimiter += "  ";
        System.out.println(delimiter + treeNode.data);
        print(treeNode.left, level + 1);
        print(treeNode.right, level + 1);
    }

    public boolean removeNonHeavyPaths(TreeNode treeNode, int k, Stack<TreeNode> stack) {
        boolean isLeaf = treeNode.left == null && treeNode.right == null;
        if (isLeaf) {
            if (k > treeNode.data) {
                // non heavy path
                TreeNode parent = stack.peek();
                // remove the node from its parent
                if (parent.left == treeNode) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
                return true;
            }
            return false;
        }

        stack.push(treeNode);
        boolean allLeafsRemoved = true;
        if (treeNode.left != null) {
            allLeafsRemoved = removeNonHeavyPaths(treeNode.left, k - treeNode.data, stack);
        }
        if (treeNode.right != null) {
            allLeafsRemoved &= removeNonHeavyPaths(treeNode.right, k - treeNode.data, stack);
        }
        stack.pop();
        if (allLeafsRemoved) {
            if (!stack.isEmpty()) {
                TreeNode parent = stack.peek();
                // remove the node from its parent
                if (parent.left == treeNode) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
                return true;
            }
        }
        return false;
    }

    public TreeNode constructTree(int[] a, int start, int end) {
        if (start > end) return null;
        int mid = (start + end) / 2;

        TreeNode root = new TreeNode(a[mid]);
        root.left = constructTree(a, start, mid - 1);
        root.right = constructTree(a, mid + 1, end);

        return root;
    }


    @Test
    public void testAccuracy() {
        TreeNode treeNode = constructTree(new int[]{1, 2, 3, 4, 5, 6, 2, 1, 5, 6}, 0, 9);
        print(treeNode, 0);

        System.out.println();
        System.out.println();
        System.out.println();

        removeNonHeavyPaths(treeNode, 15, new Stack<>());
        print(treeNode, 0);

    }


    private class TreeNode {
        int data;
        TreeNode left;
        TreeNode right;

        public TreeNode(int data) {
            this.data = data;
        }

        public TreeNode(int data, TreeNode left, TreeNode right) {
            this(data);
            this.left = left;
            this.right = right;
        }
    }

    private class Stack<V> {
        StackNode<V> head;

        public Stack() {
        }

        public V push(V value) {
            if (head == null) {
                head = new StackNode(value);
            } else {
                StackNode stackNode = new StackNode(value);
                stackNode.next = head;
                head = stackNode;
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


    private class StackNode<V> {
        StackNode next;
        V value;

        public StackNode(V value) {
            this.value = value;
        }
    }

}
