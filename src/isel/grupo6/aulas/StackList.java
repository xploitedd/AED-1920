package isel.grupo6.aulas;

public class StackList<E> {

    private static class Node<E> {
        E element;
        Node<E> next;

        Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }

    private Node<E> top;

    public boolean isEmpty() { return top == null; }

    public void push(E elem) { top = new Node<>(elem, top); }

    public E peek() {
        if (isEmpty()) return null;
        return top.element;
    }

    public E pop() {
        if (isEmpty()) return null;
        E elem = top.element;
        top = top.next;
        return elem;
    }

}
