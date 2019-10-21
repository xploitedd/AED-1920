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

    public void sort(ISBNComparator comparator) { quickSort(elements, readPos, writePos - 1, comparator); }

    public void reset() {
        readPos = 0;
        writePos = 0;
    }

    private static void quickSort(String[] a, int l, int r, ISBNComparator comparator) {
        if (l < r) {
            if (r - l + 1 <= 10) {
                insertionSort(a, l, r, comparator);
            } else {
                int i = partition(a, l, r, comparator);
                if (r == i) {
                    boolean sorted = true;
                    for (int j = l + 1; j < r; j++) {
                        if (comparator.compare(a[j - 1], a[j]) > 0) {
                            sorted = false;
                            break;
                        }
                    }

                    if (sorted)
                        return;
                }

                quickSort(a, l, i - 1, comparator);
                quickSort(a, i + 1, r, comparator);
            }
        }
    }

    private static int partition(String[] a, int l, int r, ISBNComparator comparator) {
        int i = l - 1, j = r + 1;
        for ( ; ; ) {
            do { ++i; } while (comparator.compare(a[i], a[r]) < 0);
            do { --j; } while (comparator.compare(a[j], a[r]) > 0);
            if (i >= j) return j;
            ISBNUtil.swap(a, i, j);
        }
        //ISBNUtil.swap(a, i, r);
    }

    private static void insertionSort(String[] a, int l, int r, ISBNComparator comparator) {
        String temp;
        for (int i = l + 1; i <= r; ++i) {
            temp = a[i];
            int j = i;
            for (; j > l && comparator.compare(a[j - 1], temp) > 0; --j)
                a[j] = a[j - 1];

            a[j] = temp;
        }
    }

}
