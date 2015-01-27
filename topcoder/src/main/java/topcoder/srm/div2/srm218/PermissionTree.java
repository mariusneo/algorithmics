package topcoder.srm.div2.srm218;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Problem Statement
 * <p/>
 * <p/>
 * You are building a web site for your clients to access, and want to create a welcome screen that serves as a starting point where they can view various pieces of data.
 * <p/>
 * All of your data is stored hierarchically in folders, much like a file system. You are given a String[] folders, where each element includes the id of the parent folder, a space, and a comma-delimited list of users who have permission to view that folder. The root level folder, 0, is its own parent, and all folders are descendants of the root node. Additionally, you are given a String[], users, which contains a list of the users who will be accessing the web site.
 * <p/>
 * For each user, you are to determine which folder should serve as their "home folder". A user's home folder is defined as the deepest folder which contains all of the folders the user can access.
 * <p/>
 * Suppose the data is very simple, such that folders looks like this:
 * <p/>
 * folders = { "0 Administrator",
 * "0 Joe,Phil",
 * "0 Joe" }
 * <p/>
 * and your users are Administrator, Joe, and Phil. Clearly, the Administrator's home folder is 0, since he can access the root node. Similarly, Phil's home folder is 1, since he can only access folder 1. But Joe can access folders 1 and 2, so his home folder must be folder 0, which contains folders 1 and 2.
 * <p/>
 * The return value should be a int[], indicating the home folder for each corresponding element of users. Users who have access to no folders are assigned a home folder of -1.
 * <p/>
 * Definition
 * <p/>
 * Class:	PermissionTree
 * Method:	findHome
 * Parameters:	String[], String[]
 * Returns:	int[]
 * Method signature:	int[] findHome(String[] folders, String[] users)
 * (be sure your method is public)
 * <p/>
 * <p/>
 * Notes
 * -	User names are case-sensitive
 * <p/>
 * Constraints
 * -	folders will have between 1 and 50 elements, inclusive.
 * -	users will have between 1 and 50 elements, inclusive.
 * -	Each element of users will have between 1 and 50 alphabetic ('A'-'Z' or 'a'-'z') characters.
 * -	Each element of folders will be of the form "<parent> <user list>" (quotes added for clarity).
 * -	Each element of folders will contain between 3 and 50 characters, inclusive.
 * -	<parent> will be an integer index of an element of folders, between 0 and n - 1, inclusive, where n is the number of elements in folders. Leading zeroes are permitted.
 * -	<user list> will be a comma-delimited list of user names, with no spaces, and containing only the characters 'A'-'Z', 'a'-'z', or ','.
 * -	<user list> cannot be empty, but may contain repeats.
 * -	The elements of folders will define a valid tree.
 * <p/>
 * Examples
 * 0)
 * <p/>
 * <p/>
 * {"0 Admin", "0 Joe,Phil", "0 Joe"}
 * <p/>
 * {"Admin", "Joe", "Phil"}
 * <p/>
 * Returns: { 0,  0,  1 }
 * <p/>
 * The example from the problem statement.
 * 1)
 * <p/>
 * <p/>
 * {"0 Admin"}
 * <p/>
 * {"Peter", "Paul", "Mary"}
 * <p/>
 * Returns: { -1,  -1,  -1 }
 * <p/>
 * There is not much to access, and only the admin can see it.
 * 2)
 * <p/>
 * <p/>
 * {"0 Admin", "2 John", "0 Peter,John", "0 Tim", "1 John"}
 * <p/>
 * {"John"}
 * <p/>
 * Returns: { 2 }
 * <p/>
 * Notice that, apart from the root node always being (0), other folders are not necessarily defined in order of depth. (Here, folder 2 is the parent of folder 1.)
 * 3)
 * <p/>
 * <p/>
 * {"0 Admin",
 * "0 Jeff", "1 Mark,Tim", "1 Tim,Jeff",
 * "0 Dan", "4 Ed", "4 Tom", "4 Kyle,Ed",
 * "0 Ben", "8 Rich", "8 Sam", "8 Tim"}
 * <p/>
 * {"Jeff", "Ed", "Tim", "Steve"}
 * <p/>
 * Returns: { 1,  4,  0,  -1 }
 * <p/>
 * 4)
 * <p/>
 * <p/>
 * {"0 Admin", "0 Bob,Joe,Bob", "0 Joe"}
 * <p/>
 * {"Joe", "Bob"}
 * <p/>
 * Returns: { 0,  1 }
 * <p/>
 * Notice here that a username can be repeated for a given folder.
 * <p/>
 * This problem statement is the exclusive and proprietary property of TopCoder, Inc. Any unauthorized use or reproduction of this information without the prior written consent of TopCoder, Inc. is strictly prohibited. (c)2010, TopCoder, Inc. All rights reserved.
 * <p/>
 * TAG : TREE
 */
public class PermissionTree {

    public int[] findHome(String[] folders, String[] users) {
        Map<Integer, TreeNode> value2NodeMap = new HashMap<>();
        Map<Integer, List<TreeNode>> parentNodeValue2TreeNode = new HashMap<>();
        for (int nodeValue = 0; nodeValue < folders.length; nodeValue++) {
            StringTokenizer st = new StringTokenizer(folders[nodeValue], " ,");
            int parentNodeValue = Integer.parseInt(st.nextToken());
            Set<String> nodeUsers = new HashSet<>();
            while (st.hasMoreTokens()) {
                nodeUsers.add(st.nextToken());
            }

            TreeNode treeNode = new TreeNode(nodeValue);
            value2NodeMap.put(treeNode.value, treeNode);
            treeNode.users.addAll(nodeUsers);


            if (nodeValue != 0) {
                List<TreeNode> childNodes = null;
                if (parentNodeValue2TreeNode.containsKey(parentNodeValue)) {
                    childNodes = parentNodeValue2TreeNode.get(parentNodeValue);
                } else {
                    childNodes = new ArrayList<>();
                    parentNodeValue2TreeNode.put(parentNodeValue, childNodes);
                }
                childNodes.add(treeNode);
            }
        }

        TreeNode rootNode = value2NodeMap.get(0);
        rootNode.level = 0;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(rootNode);
        while (!stack.empty()) {
            TreeNode treeNode = stack.pop();
            if (parentNodeValue2TreeNode.containsKey(treeNode.value)) {
                treeNode.children.addAll(parentNodeValue2TreeNode.get(treeNode.value));
            }

            for (TreeNode childNode : treeNode.children) {
                childNode.level = treeNode.level + 1;
                stack.push(childNode);
            }
        }


        int[] homeDirs = new int[users.length];

        for (int i = 0; i < users.length; i++) {
            String user = users[i];
            TreeNode deepestTreeNode = getContainingNode(rootNode, user);
            homeDirs[i] = deepestTreeNode == null ? -1 : deepestTreeNode.value;
        }


        return homeDirs;
    }


    private TreeNode getContainingNode(TreeNode parentNode, String user) {
        if (parentNode.users.contains(user)) {
            return parentNode;
        }

        TreeNode deepestTreeNode = null;
        int childrenAvailableCount = 0;
        for (TreeNode childNode : parentNode.children) {
            TreeNode deepestChildNode = getContainingNode(childNode, user);
            if (deepestChildNode != null) {
                childrenAvailableCount++;
                if (deepestTreeNode == null || deepestChildNode.level < deepestTreeNode.level) {
                    deepestTreeNode = deepestChildNode;
                }

            }
        }

        if (childrenAvailableCount > 1) return parentNode;

        return deepestTreeNode;
    }

    @Test
    public void test0() {
        PermissionTree pt = new PermissionTree();
        int[] homeDirs = pt.findHome(new String[]{"0 Admin", "0 Joe,Phil", "0 Joe"}, new String[]{"Admin", "Joe", "Phil"});
        Assert.assertArrayEquals(new int[]{0, 0, 1}, homeDirs);
    }


    @Test
    public void test3() {
        PermissionTree pt = new PermissionTree();
        int[] homeDirs = pt.findHome(new String[]{"0 Admin",
                "0 Jeff", "1 Mark,Tim", "1 Tim,Jeff",
                "0 Dan", "4 Ed", "4 Tom", "4 Kyle,Ed",
                "0 Ben", "8 Rich", "8 Sam", "8 Tim"}, new String[]{"Jeff", "Ed", "Tim", "Steve"});

        Assert.assertArrayEquals(new int[]{1, 4, 0, -1}, homeDirs);
    }

    @Test
    public void test1() {
        PermissionTree pt = new PermissionTree();
        int[] homeDirs = pt.findHome(new String[]{"0 Admin"},

                new String[]{"Peter", "Paul", "Mary"});

        Assert.assertArrayEquals(new int[]{-1, -1, -1}, homeDirs);
    }

    @Test
    public void test2() {
        PermissionTree pt = new PermissionTree();
        int[] homeDirs = pt.findHome(new String[]{"0 Admin", "2 John", "0 Peter,John", "0 Tim", "1 John"},
                new String[]{"John"});
        Assert.assertArrayEquals(new int[]{2}, homeDirs);
    }

    private class TreeNode {
        private int value;
        private int level;
        private Set<String> users = new HashSet<>();
        private List<TreeNode> children = new ArrayList<>();
        private TreeNode parentNode;

        public TreeNode(int value) {
            this.value = value;
        }

        private boolean isEmpty() {
            return children.isEmpty();
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "value=" + value +
                    ", level=" + level +
                    ", parentNode=" + parentNode +
                    '}';
        }
    }
}
