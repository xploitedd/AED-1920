package isel.grupo6.s2;

import java.util.Comparator;

public class ListUtils {

    public static <E> Node<E> intersection(Node<E> list1, Node<E> list2, Comparator<E> cmp) {
        Node<E> list3 = null;
        Node<E> l1i = list1.previous;
        Node<E> l2i = list2.previous;
        E last = null;
        while (l1i.value != null && l2i.value != null) {
            E first = l1i.value, second = l2i.value;
            boolean bypass = false;
            if (first.equals(last)) {
                l1i.previous.next = l1i.next;
                l1i.next.previous = l1i.previous;
                l1i = l1i.previous;
                bypass = true;
            }

            if (second.equals(last)) {
                l2i.previous.next = l2i.next;
                l2i.next.previous = l2i.previous;
                l2i = l2i.previous;
                bypass = true;
            }

            if (bypass) continue;

            int diff = cmp.compare(first, second);
            if (diff == 0) {
                // remove element from the first list
                Node<E> aux = l1i.previous;
                aux.next = l1i.next;
                l1i.next.previous = aux;

                // remove element from the second list
                l2i.previous.next = l2i.next;
                l2i.next.previous = l2i.previous;
                l2i = l2i.previous;

                // add element to the return list
                l1i.next = list3;
                l1i.previous = null;
                if (list3 != null) list3.previous = l1i;
                list3 = l1i;

                last = l1i.value;
                l1i = aux;
            } else if (diff < 0) {
                l2i = l2i.previous;
            } else {
                l1i = l1i.previous;
            }
        }

        return list3;
    }

}
