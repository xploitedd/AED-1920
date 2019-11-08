package isel.grupo6.s2;

public class StackArray<E> {

    private E[] elements;
    private int size;

    @SuppressWarnings("unchecked")
    public StackArray() {
        elements = (E[]) new Object[10];
    }

    public boolean isEmpty() { return size == 0; }

    private boolean isFull() { return size == elements.length; }

    @SuppressWarnings("unchecked")
    private void grow() {
        E[] elements = (E[]) new Object[size << 1];
        System.arraycopy(this.elements, 0, elements, 0, size);
        this.elements = elements;
    }

    public void push(E elem) {
        if (isFull()) grow();
        elements[size++] = elem;
    }

    public E peek() { return elements[size - 1]; }

    public E pop() {
        if (isEmpty()) return null;
        E elem = elements[size - 1];
        elements[--size] = null;
        return elem;
    }

    public int getSize() { return size; }

}
