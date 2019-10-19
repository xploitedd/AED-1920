package isel.grupo6.s1.isbn2;

public class StringArrayList {

    private String[] elements;
    private int readPos, writePos;
    private int size = 100000;

    @SuppressWarnings("unchecked")
    public StringArrayList() { elements = new String[size]; }

    public boolean isEmpty() { return writePos == readPos; }

    private boolean isFull() { return writePos == elements.length; }

    @SuppressWarnings("unchecked")
    private void grow() {
        String[] elements = new String[size << 1];
        System.arraycopy(this.elements, 0, elements, 0, writePos);
        this.elements = elements;
    }

    public void add(String elem) {
        if (isFull()) grow();
        elements[writePos++] = elem;
    }

    public String next() {
        if (isEmpty()) return null;
        String elem = elements[readPos];
        elements[readPos++] = null;
        return elem;
    }

    public void sort() {
    }

    public void reset() {
        readPos = 0;
        writePos = 0;
    }

    private static String[] countingSort(String[] a, int min) {
        return null;
    }
}
