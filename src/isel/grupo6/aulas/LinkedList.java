package isel.grupo6.aulas;

import java.util.Comparator;

public class LinkedList<E> {

    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        list.add("aed");
        list.add("com");
        list.add("si1");
        LinkedList<String> list2 = new LinkedList<>();
        list2.add("egp");
        list2.add("psc");
        list.mergeWith(list2, String::compareTo);
        System.out.println("Hello");
    }

    private static class Node<E> {
        E element;
        Node<E> next;
        Node<E> previous;

        Node(E element, Node<E> next, Node<E> previous) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }

        Node(E element) {
            this.element = element;
            next = this;
            previous = this;
        }
    }

    private Node<E> top;

    public LinkedList() {
        top = new Node<>(null);
    }

    public boolean isEmpty() { return top.element == null; }

    public void add(E element) {
        Node<E> node = new Node<>(element, top, top.previous);
        top.previous.next = node;
        top.previous = node;
    }

    public boolean contains(E element) {
        return search(element) != null;
    }

    public boolean remove(E element) {
        if (element == null) return false;
        Node<E> node = search(element);
        if (node == null) return false;
        node.previous.next = node.next;
        node.next.previous = node.previous;
        return true;
    }

    private Node<E> search(E element) {
        if (element == null) return null;
        for (Node<E> cur = top.next; cur != null; cur = cur.next) {
            if (cur.element != null && cur.element.equals(element))
                return cur;
        }

        return null;
    }

    private void mergeWith(LinkedList<E> list, Comparator<E> comparator) {
        Node<E> a = top.next, b = list.top.next;
        while (b != null) {
            if (comparator.compare(a.element, b.element) < 0) {
                if (a.next == null)
                    break;

                a = a.next;
            } else {
                top.next = b;
                top.next.previous = a.previous;
                top.next.next = a;
                a.previous = top.next;
                b = b.next;
            }
        }

        while (b != null) {
            a.next = b;
            b.previous = a;
            b = b.next;
            a = b;
        }
    }

    public void clear() { top = null; }

}
