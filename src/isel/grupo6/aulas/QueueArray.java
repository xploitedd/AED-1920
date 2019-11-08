package isel.grupo6.aulas;

public class QueueArray<E> implements IQueue<E> {

    private E[] elements;
    private int write, read;

    @SuppressWarnings("unchecked")
    public QueueArray() { elements = (E[]) new Object[10]; }

    @Override
    public boolean isEmpty() { return write == read; }

    @Override
    public boolean isFull() { return elements.length == write - read + 1; }

    @Override
    public boolean offer(E elem) {
        if (isFull()) return false;
        elements[write++] = elem;
        if (write == elements.length) write = 0;
        return true;
    }

    @Override
    public E poll() {
        if (isEmpty()) return null;
        E elem = elements[read];
        elements[read++] = null;
        if (read == elements.length) read = 0;
        return elem;
    }

    @Override
    public E peek() {
        return elements[read];
    }

}
