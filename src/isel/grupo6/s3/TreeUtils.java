package isel.grupo6.s3;

import java.util.LinkedList;
import java.util.Stack;

public class TreeUtils {

    /**
     * Count how many elements multiple of k there are in a binary search tree
     * @param root tree to search multiples
     * @param k multiples of
     * @return how many multiples of k there are
     */
    public static int countMultiples(Node<Integer> root, Integer k) {
        if (root == null) return 0;
        int count = 0;
        // if multiple increment count
        if (root.value % k == 0) ++count;
        // if the value is less or equal than k then we only
        // need to check the right side because there are no
        // multiples on the left
        if (root.value <= k) {
            count += countMultiples(root.right, k);
        } else {
            count += countMultiples(root.left, k);
            count += countMultiples(root.right, k);
        }

        return count;
    }

    /**
     * Find the k-smallest element in a binary search tree
     * @param root head of the tree
     * @param k element to get
     * @return k-smallest element or null if doesn't exist one
     */
    public static Integer kSmallest(Node<Integer> root, int k) {
        if (root == null || k < 1) return null;
        Stack<Node<Integer>> stack = new Stack<>();
        for ( ; ; ) {
            while (root != null) {
                // add all elements from the left leg of the current tree
                stack.push(root);
                root = root.left;
            }

            // if the k-th element does not exist
            if (stack.isEmpty()) return null;
            // remove the last stack entry and check if it's the k-th element
            root = stack.pop();
            if (--k == 0) return root.value;
            // transverse the right sub-tree of the last stack entry
            root = root.right;
         }
    }

    /**
     * Count how many elements there are in a tree
     * @param root root of the tree
     * @param <E> element type
     * @return element count
     */
    private static <E> int countTree(Node<E> root) {
        if (root == null) return 0;
        int count = 1;
        count += countTree(root.left);
        count += countTree(root.right);
        return count;
    }

    /**
     * Check if a tree is balanced
     * A tree is balanced if the heights of both sides differ, at the most, by 1
     * @param root root of the tree
     * @param <E> element type
     * @return true if balanced, false otherwise
     */
    public static <E> boolean isBalanced(Node<E> root) {
        if (root == null) return false;
        if (root.left == null && root.right == null) return true;

        // get the height of the left and right sub-trees
        int lh = height(root.left);
        int rh = height(root.right);
        // if the difference between the left height and the right height
        // is less or equal than 1 then check if the other sub-trees are
        // also balanced
        if (Math.abs(lh - rh) < 2)
            return isBalanced(root.left) && isBalanced(root.right);

        return false;
    }

    /**
     * Get the height of a tree starting in node
     * @param node starting point of the tree
     * @param <E> element type
     * @return height of the tree
     */
    private static <E> int height(Node<E> node) {
        if (node == null) return -1;
        int l = height(node.left);
        int r = height(node.right);
        return 1 + Math.max(l, r);
    }

}
