package isel.grupo6.s3;

public class Nodes {

    static Node<Integer> getOneNode() { return new Node<>(10); }

    static Node<Integer> getTreeLeft() {
        Node<Integer> root = new Node<>(20);
        root.left = new Node<>(10);
        root.left.left = new Node<>(5);
        root.left.left.left = new Node<>(2);
        return root;
    }

    static Node<Integer> getTreeRight() {
        Node<Integer> root = new Node<>(10);
        root.right = new Node<>(20);
        root.right.right = new Node<>(25);
        root.right.right.right = new Node<>(30);
        return root;
    }

    static Node<Integer> getBalancedTree() {
        Node<Integer> root = new Node<>(25);
        root.left = new Node<>(10);
        root.right = new Node<>(30);
        root.left.left = new Node<>(5);
        root.left.right = new Node<>(11);
        root.right.left = new Node<>(27);
        root.right.right = new Node<>(35);
        return root;
    }

    static Node<Integer> getUnbalancedTree() {
        Node<Integer> root = new Node<>(25);
        root.left = new Node<>(10);
        root.right = new Node<>(30);
        root.left.left = new Node<>(5);
        root.left.left.left = new Node<>(2);
        root.right.left = new Node<>(27);
        root.right.right = new Node<>(35);
        return root;
    }

}
