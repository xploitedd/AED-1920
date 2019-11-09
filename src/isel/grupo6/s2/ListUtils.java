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

    public static <E> void internalReverse(Node<Node<E>> list) {
        if (list == null) return;
        for (Node<Node<E>> parent = list; parent != null; parent = parent.next) {
            Node<E> head = parent.value;
            for (Node<E> child = head; child != null; child = child.previous) {
                Node<E> aux = child.next;
                child.next = child.previous;
                child.previous = aux;
                if (aux != null) head = aux;
            }

            parent.value = head;
        }
    }

    public static <E> E mostOccurrent(Node<E>[] lists, Comparator<E> cmp) {
        if (lists == null || lists.length == 0) return null;
        int len = lists.length;

        E mostOccurred = null;
        int maxOccurrences = 0;
        for ( ; ; ) {
            Node<E> currentList = lists[0];
            for (int i = 1; i < len; i++) {
                if (lists[i] != null) {
                    if (currentList == null) currentList = lists[i];
                    // find the minimum (the starting point)
                    if (cmp.compare(lists[i].value, currentList.value) < 0)
                        currentList = lists[i];
                }
            }

            if (currentList == null)
                break;

            int occurrences = 0;
            for (int i = 0; i < len; i++) {
                if (lists[i] == null) continue;
                Node<E> curr = lists[i];
                for (; curr != null; curr = curr.next) {
                    int diff = cmp.compare(curr.value, currentList.value);
                    if (diff == 0)
                        ++occurrences;
                    else
                        break;
                }

                if (curr != null) curr.previous = null;
                lists[i] = curr;
            }

            if (occurrences > maxOccurrences) {
                maxOccurrences = occurrences;
                mostOccurred = currentList.value;
            }
        }

        return mostOccurred;
    }

}
