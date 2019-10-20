package isel.grupo6.s1.isbn2;

public class ISBNArrayList {

    private String[] elements;
    private int readPos, writePos;

    @SuppressWarnings("unchecked")
    public ISBNArrayList(int maxSize) { elements = new String[maxSize]; }

    public boolean isEmpty() { return writePos == readPos; }

    private boolean isFull() { return writePos == elements.length; }

    public void add(String elem) {
        if (isFull()) return;
        elements[writePos++] = elem;
    }

    public String next() {
        if (isEmpty()) return null;
        String elem = elements[readPos];
        elements[readPos++] = null;
        return elem;
    }

    public void sort(ISBNComparator comparator) { quickSort(elements, 0, writePos - 1, comparator); }

    public void reset() {
        readPos = 0;
        writePos = 0;
    }

    private static void quickSort(String[] a, int l, int r, ISBNComparator comparator) {
        if (l < r) {
            int i = partition(a, l, r, comparator);
            quickSort(a, l, i - 1, comparator);
            quickSort(a, i + 1, r, comparator);
        }
    }

    private static int partition(String[] a, int l, int r, ISBNComparator comparator) {
        int i = l - 1, j = r;
        for ( ; ; ) {
            while (i < r && comparator.compare(a[++i], a[r]) < 0);
            while (j > l && comparator.compare(a[--j], a[r]) > 0);
            if (i >= j) break;
            swap(a, i, j);
        }

        swap(a, i, r);
        return i;
    }

    private static void swap(String[] a, int idxA, int idxB) {
        String temp = a[idxA];
        a[idxA] = a[idxB];
        a[idxB] = temp;
    }

}
