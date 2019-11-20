package isel.grupo6.aulas;

import java.util.Comparator;

public class BinarySearchTree<E> {

    private static class Node<E> {
        E elem;
        Node<E> left;
        Node<E> right;
    }

    private Node<E> head;
    private Comparator<E> comparator;

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public int height() { return height(head); }

    private int height(Node<E> node) {
        if (node == null) return -1;
        int l = height(node.left);
        int r = height(node.right);
        return 1 + Math.max(l, r);
    }

    public boolean isComplete() {
        Node<E> cur = head;
        int height = height(cur);
        for (int i = 0; i <= height; ++i) {
            if (cur.left == null || cur.right == null)
                return false;
        }

        return true;
    }

    private Node<E> search(E elem) {
        Node<E> cur = head;
        while (cur != null) {
            int c = comparator.compare(cur.elem, elem);
            if (c == 0) return cur;
            cur = c < 0 ? cur.right : cur.left;
        }

        return null;
    }

}
