package isel.grupo6.aulas;

public class StackList<E> {

    public static class Node<E> {
        E element;
        Node<E> next;

        Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }

    public Node<E> top;
    private int size = 0;

    public boolean isEmpty() { return top == null; }

    public void push(E elem) {
        top = new Node<>(elem, top);
        size++;
    }

    public E peek() {
        if (isEmpty()) return null;
        return top.element;
    }

    public E pop() {
        if (isEmpty()) return null;
        E elem = top.element;
        top = top.next;
        --size;
        return elem;
    }

    public int getSize() { return size; }

}
